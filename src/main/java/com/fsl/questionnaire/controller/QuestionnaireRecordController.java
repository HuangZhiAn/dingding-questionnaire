package com.fsl.questionnaire.controller;

import java.util.List;
import java.util.Map;

import com.fsl.questionnaire.dto.QuestionnaireRecord;
import com.fsl.questionnaire.dto.User;
import com.fsl.questionnaire.mapper.QuestionnaireRecordMapper;
import com.fsl.questionnaire.util.MemberListUtil;
import com.fsl.questionnaire.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Transactional
public class QuestionnaireRecordController {

    private final QuestionnaireRecordMapper questionnaireRecordMapper;

    @Value("${send.message.url}")
    private String urlPrefix;

    @Autowired
    public QuestionnaireRecordController(QuestionnaireRecordMapper questionnaireRecordMapper) {
        this.questionnaireRecordMapper = questionnaireRecordMapper;
    }

    @PostMapping(value = "/saveData")
    @ResponseBody
    public String saveData(@RequestBody Map map) {
        System.out.println(map);
        String pId;
        pId = (String) map.get("pId");
        QuestionnaireRecord questionnaireRecord = new QuestionnaireRecord();
        if (pId == null || "".equals(pId)) {
            return "问卷ID为空";
        }
        questionnaireRecord.setId(Long.parseLong(pId));

        QuestionnaireRecord questionnaireRecordOld = questionnaireRecordMapper.selectQuestionnaireRecordById(questionnaireRecord);
        if (questionnaireRecordOld == null || questionnaireRecordOld.getId() == null) {
            return "问卷不存在";
        }

        if (questionnaireRecordOld.getOperationStatus().equals("N")) {
            return "此IP已经提交过问卷";
        }
        questionnaireRecord.setOperationStatus("N");
        questionnaireRecord.setId(questionnaireRecordOld.getId());

        Map qa = (Map) map.get("qa");
        for (Object o : qa.keySet()) {
            String question = (String) o;
            if (question.startsWith("1")) {
                questionnaireRecord.setQustion1(question);
                questionnaireRecord.setAnswer1((String) qa.get(question));
            }
            if (question.startsWith("2")) {
                questionnaireRecord.setQustion2(question);
                questionnaireRecord.setAnswer2((String) qa.get(question));
            }
            if (question.startsWith("3")) {
                questionnaireRecord.setQustion3(question);
                questionnaireRecord.setAnswer3((String) qa.get(question));
            }
            if (question.startsWith("4")) {
                questionnaireRecord.setQustion4(question);
                questionnaireRecord.setAnswer4((String) qa.get(question));
            }
            if (question.startsWith("5")) {
                questionnaireRecord.setQustion5(question);
                questionnaireRecord.setAnswer5((String) qa.get(question));
            }
        }
        int i = questionnaireRecordMapper.updateQuestionnaireRecord(questionnaireRecord);
        if (i <= 0) {
            return "F";
        }
        //取消待办
        new Thread(() -> MessageUtil.updateWorkRecord(questionnaireRecordOld.getUserId(), questionnaireRecordOld.getRequestRecord())).start();

        return "S";
    }

    @PostMapping(value = "/sendMessage/userIdList")
    @ResponseBody
    public String sendMessageToUsers(@RequestBody User[] userIdList) {
        //根据部门发问卷待办
        int count = 0;
        //根据用户发问卷待办
        if (userIdList != null) {
            for (User u : userIdList) {
                if (u.getUserid() != null && !u.getUserid().equals("")) {
                    MessageUtil.sendMessageToUser(u.getUserid(), u.getName(),urlPrefix,questionnaireRecordMapper);
                    count++;
                }
            }
        }
        return count + "";
    }

    @PostMapping(value = "/sendMessage/departmentIds")
    @ResponseBody
    public String sendMessageToDepartment(@RequestBody String[] departmentIds) {
        //根据部门发问卷待办
        int count = 0;
        for (String departmentId : departmentIds) {
            if (departmentId != null && !departmentId.equals("")) {
                List<Long> department = null;
                List<User> departmentUserIdWithName = null;
                try {
                    department = MemberListUtil.getDepartment(departmentId);
                    departmentUserIdWithName = MemberListUtil.getDepartmentUserIdWithName(department);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (departmentUserIdWithName != null) {
                    for (User user : departmentUserIdWithName) {
                        MessageUtil.sendMessageToUser(user.getUserid(), user.getName(),urlPrefix,questionnaireRecordMapper);
                        count++;
                    }
                }
            }
        }
        return count + "";
    }
}
