package com.fsl.questionnaire.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiProcessinstanceGetRequest;
import com.dingtalk.api.request.OapiWorkrecordAddRequest;
import com.dingtalk.api.request.OapiWorkrecordUpdateRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.dingtalk.api.response.OapiWorkrecordAddResponse;
import com.dingtalk.api.response.OapiWorkrecordUpdateResponse;
import com.fsl.questionnaire.config.Constant;
import com.fsl.questionnaire.config.URLConstant;
import com.fsl.questionnaire.dto.QuestionnaireRecord;
import com.fsl.questionnaire.mapper.QuestionnaireRecordMapper;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息工具类
 */
public class MessageUtil {
    private static final Logger bizLogger = LoggerFactory.getLogger(MessageUtil.class);

    public static void sendMessageToOriginator(String processInstanceId) throws RuntimeException {
        try {
            DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_PROCESSINSTANCE_GET);
            OapiProcessinstanceGetRequest request = new OapiProcessinstanceGetRequest();
            request.setProcessInstanceId(processInstanceId);
            OapiProcessinstanceGetResponse response = client.execute(request, AccessTokenUtil.getToken());
            String recieverUserId = response.getProcessInstance().getOriginatorUserid();

            client = new DefaultDingTalkClient(URLConstant.MESSAGE_ASYNCSEND);

            OapiMessageCorpconversationAsyncsendV2Request messageRequest = new OapiMessageCorpconversationAsyncsendV2Request();
            messageRequest.setUseridList(recieverUserId);
            messageRequest.setAgentId(Constant.AGENTID);
            messageRequest.setToAllUser(false);

            OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
            msg.setMsgtype("text");
            msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
            msg.getText().setContent("出差申请通过了，快去订机票吧");
            messageRequest.setMsg(msg);

            OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(messageRequest, AccessTokenUtil.getToken());
        } catch (ApiException e) {
            bizLogger.error("send message failed", e);
            throw new RuntimeException();
        }
    }

    /**
     * 发送待办
     *
     * @param userId  用户ID
     * @param url     待办URL
     * @param title   标题
     * @param content 内容
     * @return 返回待办recordId, 用于取消待办
     */
    public static String addWorkRecord(String userId, String url, String title, String content) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/workrecord/add");
        OapiWorkrecordAddRequest req = new OapiWorkrecordAddRequest();
        req.setUserid(userId);
        Date date = new Date();
        req.setCreateTime(date.getTime());
        req.setTitle(title);
        req.setUrl(url);
        List<OapiWorkrecordAddRequest.FormItemVo> list2 = new ArrayList<OapiWorkrecordAddRequest.FormItemVo>();
        OapiWorkrecordAddRequest.FormItemVo obj3 = new OapiWorkrecordAddRequest.FormItemVo();
        list2.add(obj3);
        obj3.setTitle(title);
        obj3.setContent(content);
        req.setFormItemList(list2);
        OapiWorkrecordAddResponse rsp = null;
        try {
            rsp = client.execute(req, AccessTokenUtil.getToken());
            System.out.println(rsp.getBody());
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return rsp==null?null:rsp.getRecordId();
    }

    /**
     * 取消待办
     *
     * @param userid   用户ID
     * @param recordId 发送待办时返回的待办recordId
     */
    public static void updateWorkRecord(String userid, String recordId) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/workrecord/update");
        OapiWorkrecordUpdateRequest req = new OapiWorkrecordUpdateRequest();
        req.setUserid(userid);
        req.setRecordId(recordId);
        try {
            OapiWorkrecordUpdateResponse rsp = client.execute(req, AccessTokenUtil.getToken());
            System.out.println(rsp.getBody());
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     * 给一个或多个用户发送连接消息
     *
     * @param useridList 用户ID字符串，用逗号","隔开
     * @param url        消息连接
     * @param title      标题
     * @param text       内容
     */
    public static void sendMessage(String useridList, String url, String title, String text) {
        DingTalkClient client = new DefaultDingTalkClient(URLConstant.MESSAGE_ASYNCSEND);
        OapiMessageCorpconversationAsyncsendV2Request messageRequest = new OapiMessageCorpconversationAsyncsendV2Request();
        messageRequest.setUseridList(useridList);
        messageRequest.setAgentId(Constant.AGENTID);
        messageRequest.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("link");
        OapiMessageCorpconversationAsyncsendV2Request.Link link = new OapiMessageCorpconversationAsyncsendV2Request.Link();
        link.setMessageUrl(url);
        link.setTitle(title);
        link.setPicUrl("@lALOACZwe2Rk");
        link.setText(text);
        msg.setLink(link);
        messageRequest.setMsg(msg);
        try {
            OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(messageRequest, AccessTokenUtil.getToken());
            //System.out.println(rsp.getBody());
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     * 推送问卷待办和消息给用户
     *
     * @param userId   用户ID
     * @param username 用户名
     */
    public static void sendMessageToUser(String userId, String username,String urlPrefix,QuestionnaireRecordMapper questionnaireRecordMapper) {
        QuestionnaireRecord questionnaireRecord = new QuestionnaireRecord();
        Long id = SnowIdGenUtil.nextId(4L, 16L);
        questionnaireRecord.setId(id);
        questionnaireRecord.setUserId(userId);
        questionnaireRecord.setUserName(username);
        questionnaireRecord.setOperationStatus("Y");
        questionnaireRecord.setAttribute1("1");
        String url = urlPrefix + id;
        //避免重复向一个人推送问卷
        int i1 = questionnaireRecordMapper.userExists(questionnaireRecord.getUserId());
        if (i1 == 0) {
            //发待办
            String title = "钉钉办公协同工具使用问卷调查";
            String record = MessageUtil.addWorkRecord(userId, url
                    , title, "");
            questionnaireRecord.setRequestRecord(record);
            int i = questionnaireRecordMapper.insertQuestionnaireRecord(questionnaireRecord);
            //确保成功插入数据库再发消息推送
            if (i > 0) {
                MessageUtil.sendMessage(userId, url, title, title);
            } else {
                //否则取消待办
                MessageUtil.updateWorkRecord(questionnaireRecord.getUserId(), questionnaireRecord.getRequestRecord());
            }
        }
    }

}
