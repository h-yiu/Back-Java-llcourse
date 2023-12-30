package com.lorelinks.llcourse.repository;

import com.lorelinks.llcourse.entity.Answers;
import com.lorelinks.llcourse.entity.Question;

import java.util.List;

public interface QuestionRepository {
    List<Question> findByQuizName(String quizName);
    void saveAnswers(Answers answers);
    Object findQuestionIdByNum(int question_number, String quizID);

    String getQuizScore(String studentName, String quizID);

}
