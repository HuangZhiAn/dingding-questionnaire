package com.fsl.questionnaire.config;

/**
 * 项目中的常量定义类
 */
public class Constant {
    /**
     * 企业corpid, 需要修改成开发者所在企业
     */
    public static final String CORP_ID = "ding7a7983d0f947125235c2f4657eb6378f";
    /**
     * 应用的AppKey，登录开发者后台，点击应用管理，进入应用详情可见
     */
    public static final String APPKEY = "dingp6kj34urulcczui5";
    /**
     * 应用的AppSecret，登录开发者后台，点击应用管理，进入应用详情可见
     */
    public static final String APPSECRET = "NGRb0B6OcD7UYaM4fxVwZ6ZtNo8QqBu4T_LykRqh8_CurjaC27PAHz7B9deHHv55";

    /**
     * 数据加密密钥。用于回调数据的加密，长度固定为43个字符，从a-z, A-Z, 0-9共62个字符中选取,您可以随机生成
     */
    public static final String ENCODING_AES_KEY = "1234567890123456789012345678901234567890abc";

    /**
     * 加解密需要用到的token，企业可以随机填写。如 "12345"
     */
    public static final String TOKEN = "12345";

    /**
     * 应用的agentdId，登录开发者后台可查看
     */
    public static final Long AGENTID = 258370291L;

    /**
     * 审批模板唯一标识，可以在审批管理后台找到      审批接入 PROC-CFYJVUBV-QSB3DLF3142DDCO07WKM3-46PYIMSJ-3
     */
    public static final String PROCESS_CODE = "PROC-CFYJPO3V-OX6369024NTHAD9UDRA62-MBX5PFSJ-5";

    /**
     * 回调host    http://abcde.vaiwan.com
     */
    public static final String CALLBACK_URL_HOST = "http://fsl2018.vaiwan.com";

    /**
     * 安全巡查 processCode：  PROC-CFYJPO3V-OX6369024NTHAD9UDRA62-MBX5PFSJ-5
     */
    public static final String SAFETY_PATROL_PROCESSCODE="PROC-CFYJPO3V-OX6369024NTHAD9UDRA62-MBX5PFSJ-5";

    public static final String IT_OPERATION_PROCESSCODE="PROC-CFYJEMBV-U2E3SB62YE6830B48PTK3-OYZY5VSJ-92";

    public static final String CESHI="PROC-7KYJMCPV-5EB40DO90X8ZL5J8CZBD2-ZJJB92UJ-32";

    public static final String IMAGE_DIRECTORY = "D:/fsl/安全巡检/quick/eapp-corp-project/src/main/resources/image/";
}
