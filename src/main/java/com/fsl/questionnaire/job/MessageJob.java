package com.fsl.questionnaire.job;

import java.time.LocalDateTime;
import java.util.List;

import com.fsl.questionnaire.dto.QuestionnaireRecord;
import com.fsl.questionnaire.mapper.QuestionnaireRecordMapper;
import com.fsl.questionnaire.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class MessageJob {

    private final QuestionnaireRecordMapper questionnaireRecordMapper;

    @Value("${send.message.url}")
    private String url;

    @Autowired
    public MessageJob(QuestionnaireRecordMapper questionnaireRecordMapper) {
        this.questionnaireRecordMapper = questionnaireRecordMapper;
    }

    //3.添加定时任务
    //@Scheduled(cron = "0 * * ? * 1-5")
    @Scheduled(cron = "0 0 9 ? * 1-5")
    private void configureTasks() {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        sendMessageScheduled();
    }

    public void sendMessageScheduled(){
        List<QuestionnaireRecord> questionnaireRecords = questionnaireRecordMapper.selectUncompleteRecord();
        for (QuestionnaireRecord r:questionnaireRecords) {
            String title = "钉钉办公协同工具使用问卷调查";
            MessageUtil.sendMessage(r.getUserId(),url+r.getId(), title,LocalDateTime.now().toString());
            questionnaireRecordMapper.increaseSendMessageCount(r.getId());
        }

    }
}
