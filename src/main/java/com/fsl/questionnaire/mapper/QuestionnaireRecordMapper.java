package com.fsl.questionnaire.mapper;

import java.util.List;

import com.fsl.questionnaire.dto.QuestionnaireRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionnaireRecordMapper {

    int insertQuestionnaireRecord(QuestionnaireRecord questionnaireRecord);

    QuestionnaireRecord selectQuestionnaireRecordById(QuestionnaireRecord questionnaireRecord);

    int updateQuestionnaireRecord(QuestionnaireRecord questionnaireRecord);

    List<QuestionnaireRecord> selectUncompleteRecord();

    int increaseSendMessageCount(Long id);

    int userExists(String userId);
}
