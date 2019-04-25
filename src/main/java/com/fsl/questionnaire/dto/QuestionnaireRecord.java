package com.fsl.questionnaire.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/*T_QUESTIONNAIRE_RECORD*/
@Data
public class QuestionnaireRecord implements Serializable,Cloneable{
    /** 雪花ID */
    private Long id ;
    /** 钉钉用户id */
    private String userId ;
    /** 钉钉用户名 */
    private String userName ;
    /** 待办record，用于取消待办 */
    private String requestRecord ;
    /** 待办状态，Y正在待办，N已取消 */
    private String operationStatus ;
    /** 问题1 */
    private String qustion1 ;
    /** 答案1 */
    private String answer1 ;
    /** 问题2 */
    private String qustion2 ;
    /** 答案2 */
    private String answer2 ;
    /** 问题3 */
    private String qustion3 ;
    /** 答案3 */
    private String answer3 ;
    /** 问题4 */
    private String qustion4 ;
    /** 答案4 */
    private String answer4 ;
    /** 问题5 */
    private String qustion5 ;
    /** 答案5 */
    private String answer5 ;
    /** 提交时间 */
    private Date operationTime ;
    /** 创建人账户编码 */
    private String createdByUser ;
    /** 所属组织编码 */
    private String createdUnitCode ;
    /** 所属部门编码 */
    private String createdDeptCode ;
    /** 主岗部门编码 */
    private String createdOrg ;
    /** 创建时间 */
    private Date creationDate ;
    /** 修改人账户编码 */
    private String lastUpdatedByUser ;
    /** 修改时间 */
    private Date lastUpdateDate ;
    /** 是否删除 */
    private String isDelete ;
    /** 版本号 */
    private Long objectVersionNumber ;
    /** 创建人账户Id */
    private Long createdBy ;
    /** 修改人账户Id */
    private Long lastUpdatedBy ;
    /** 最后登陆人账户Id */
    private Long lastUpdateLogin ;
    /** 请求id */
    private Long requestId ;
    /** 程序id */
    private Long programId ;
    /** 发送消息次数 */
    private String attribute1 ;
    /**  */
    private String attribute2 ;
    /**  */
    private String attribute3 ;
    /**  */
    private String   attribute4 ;
    /**  */
    private String attribute5 ;
    /**  */
    private String attribute6 ;
    /**  */
    private String attribute7 ;
    /**  */
    private String attribute8 ;
    /**  */
    private String attribute9 ;
    /**  */
    private String attribute10 ;
    /**  */
    private String attribute11 ;
    /**  */
    private String attribute12 ;
    /**  */
    private String attribute13 ;
    /**  */
    private String attribute14 ;
    /**  */
    private String attribute15 ;
}
