package com.fsl.questionnaire;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiWorkrecordGetbyuseridRequest;
import com.dingtalk.api.response.OapiWorkrecordGetbyuseridResponse;
import com.fsl.questionnaire.dto.QuestionnaireRecord;
import com.fsl.questionnaire.dto.User;
import com.fsl.questionnaire.mapper.QuestionnaireRecordMapper;
import com.fsl.questionnaire.util.AccessTokenUtil;
import com.fsl.questionnaire.util.MemberListUtil;
import com.fsl.questionnaire.util.MessageUtil;
import com.taobao.api.ApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private QuestionnaireRecordMapper questionnaireRecordMapper;

    private int coun = 0;

    @Value("${send.message.url}")
    private String urlPrefix;

    @Test
    public void contextLoads() {
        //取消待办
        //MessageUtil.updateWorkRecord("123061175039842902","recordb72f60a6996bfcc36ef9c556f252cc5e");
    }

    public static void main(String[] args){
        Map<User, Object> objectObjectHashMap = new HashMap<>(3);
        System.out.println(objectObjectHashMap);

        new HashMap<>();
        new Hashtable<>();
    }

    @Test
    public void sendMessageToUser() {
        //MessageUtil.sendMessageToUser("061343005926696347","林锦洪",urlPrefix,questionnaireRecordMapper);
        MessageUtil.sendMessageToUser("123061175039842902", "黄志安",urlPrefix,questionnaireRecordMapper);
        //MessageUtil.sendMessageToUser("025714173126259030", "柴部",urlPrefix,questionnaireRecordMapper);
        //sendMessageToUser("014575","卢科");
        //MessageUtil.sendMessage("123061175039842902","http://ddq.fslgz.com:8020/tt.html?pId=", "钉钉办公协同工具使用问卷调查",LocalDateTime.now().toString()+"\nIE无法提交问题已修复");
    }

    /**
     * 查询未完成问卷测试
     */
    @Test
    public void selectUncompleteRecordTest() {
        List<QuestionnaireRecord> questionnaireRecords = questionnaireRecordMapper.selectUncompleteRecord();
        System.out.println(questionnaireRecords);
    }

    /**
     * 发送消息次数加一测试
     */
    @Test
    public void increaseSendMessageCountTest() {
        int i = questionnaireRecordMapper.increaseSendMessageCount(569192791818649600L);
        assert i > 0;
    }

    /**
     * 推送消息待办给某部门下的所有用户
     *
     * @param departmentId 部门ID
     */
    public void sendMessageToDepartment(String departmentId) {
        List<Long> department = null;
        List<User> departmentUserIdWithName = null;
        try {
            department = MemberListUtil.getDepartment(departmentId);
            departmentUserIdWithName = MemberListUtil.getDepartmentUserIdWithName(department);
            coun += departmentUserIdWithName.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (departmentUserIdWithName != null) {
            for (User user : departmentUserIdWithName) {
                MessageUtil.sendMessageToUser(user.getUserid(), user.getName(),urlPrefix,questionnaireRecordMapper);
            }
        }
    }

    /**
     * 给指定的一组部门发问卷推送
     *
     * @param departmentList 部门ID数组
     */
    public void sendMessageToDepartmentList(String[] departmentList) {
        for (String s : departmentList) {
            sendMessageToDepartment(s);
        }
    }

    @Test
    public void sendMessageToDepartmentListTest() {
        String dept = "86149250,98512269,90205294,86149256,98639202,98598216,103888893,98493301,92310274,87186163,96905232,87184119,98820171,98820172,98961349,88543261,98644224,98637394,98472228,98477315,94119055,98929363,98295103,98373335,97792052,98373336,98664274,98629261,98519208,98519216,98664276,101362945,111642158,98357303,98357309,98357299,107837628,98059132,98178139,97797362,98480749,98656216,98590550,104007661,104108135,104084196,103799000,104165006,103851000,103929812";
        //String dept = "112237951";
        String[] departmentList = dept.split(",");
        sendMessageToDepartmentList(departmentList);
    }

    /**
     * 获取用户的所有待办
     */
    @Test
    public void updateWorkRecord() {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/workrecord/getbyuserid");
        OapiWorkrecordGetbyuseridRequest req = new OapiWorkrecordGetbyuseridRequest();
        req.setUserid("123061175039842902");
        req.setOffset(0L);
        req.setLimit(50L);
        req.setStatus(0L);
        OapiWorkrecordGetbyuseridResponse rsp = null;
        try {
            rsp = client.execute(req, AccessTokenUtil.getToken());
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
    }

    /**
     * 问卷已存在测试
     */
    @Test
    public void userExistsTest() {
        int i = questionnaireRecordMapper.userExists("");
        System.out.println(i);
    }

    /**
     * 获取部门下所有人员测试
     */
    @Test
    public void departmentUserIdWithName() {
        try {
            List<Long> department = MemberListUtil.getDepartment("112237951");
            System.out.println("部门："+department);
            List<User> departmentUserIdWithName = MemberListUtil.getDepartmentUserIdWithName(department);
            System.out.println(departmentUserIdWithName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Value("${send.message.url}")
    private String url;

    private String title="钉钉办公协同工具使用问卷调查";

    @Test
    public void sendMessageScheduled(){
        List<QuestionnaireRecord> questionnaireRecords = questionnaireRecordMapper.selectUncompleteRecord();

        //System.out.println(questionnaireRecords.size() + " aaaadfasdfasdfasdfas");

        //MessageUtil.sendMessage("014575",url+questionnaireRecords.get(0).getId(),title,LocalDateTime.now().toString());

        for (QuestionnaireRecord r:questionnaireRecords) {

            MessageUtil.sendMessage(r.getUserId(),url+r.getId(),title,LocalDateTime.now().toString());
            questionnaireRecordMapper.increaseSendMessageCount(r.getId());
        }

    }
}
