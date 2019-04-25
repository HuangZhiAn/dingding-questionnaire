package com.fsl.questionnaire.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.fsl.questionnaire.config.Constant;
import com.fsl.questionnaire.config.URLConstant;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取access_token工具类
 */
public class AccessTokenUtil {
    private static final Logger bizLogger = LoggerFactory.getLogger(AccessTokenUtil.class);

    /**
     * 问卷调查TOKEN
     */
    public static String getToken() throws RuntimeException {
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_GET_TOKKEN);
            OapiGettokenRequest request = new OapiGettokenRequest();

            request.setAppkey(Constant.APPKEY);
            request.setAppsecret(Constant.APPSECRET);
            request.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(request);
            return response.getAccessToken();
        } catch (ApiException e) {
            bizLogger.error("getAccessToken failed", e);
            throw new RuntimeException();
        }
    }

    /**
     * 安全随手拍TOKEN
     */
    public static String getTokenAQ() throws RuntimeException {
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_GET_TOKKEN);
            OapiGettokenRequest request = new OapiGettokenRequest();

            request.setAppkey("dinguuqnilfwp2n2dtuh");
            request.setAppsecret("ZLtAaM1uVE1bSTfBda81bzKOMLgXCyZw9GYgjnCx3nHc-IOfxNVp5IxLew2QKyl4");
            request.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(request);
            return response.getAccessToken();
        } catch (ApiException e) {
            bizLogger.error("getAccessToken failed", e);
            throw new RuntimeException();
        }
    }
    /**
     * 资产管理应用TOKEN
     */
    public static String getTokenZC() throws RuntimeException {
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_GET_TOKKEN);
            OapiGettokenRequest request = new OapiGettokenRequest();

            request.setAppkey("ding1pfxwbhtnmtqtcfg");
            request.setAppsecret("xmq8udUbRifGkof3IOaf_6ySxzIWEJ6refhljWLstOBtuTUspQ-WKtIo5CpFcGhs");
            request.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(request);
            return response.getAccessToken();
        } catch (ApiException e) {
            bizLogger.error("getAccessToken failed", e);
            throw new RuntimeException();
        }

    }
}
