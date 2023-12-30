package com.lorelinks.llcourse.repository;

import com.lorelinks.llcourse.entity.Answers;
import com.lorelinks.llcourse.entity.Question;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcQuestionRepository implements QuestionRepository {
    private final JdbcTemplate jdbc;
    public JdbcQuestionRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    @Override
    public List<Question> findByQuizName(String quizName) {
        String sql = "SELECT * FROM query_quiz_questions (?)";
        List<Map<String, Object>> res = jdbc.queryForList(sql, quizName);
        List<Question> questions = new ArrayList<>();

        for (Map<String, Object> data: res) {
            System.out.println(data);
            Question question = new Question();
            question.setQuizID(data.get("quiz_id"));
            question.setQuestionNumber((Integer) data.get("question_number"));
            question.setQuestionDescription((String) data.get("question_description"));
            question.setOptionA((String) data.get("option_a"));
            question.setOptionB((String) data.get("option_b"));
            question.setOptionC((String) data.get("option_c"));
            question.setOptionD((String) data.get("option_d"));
            question.setCorrectAnswer((String) data.get("correct_ans"));
            questions.add(question);
        }
        return questions;
    }

    @Override
    public void saveAnswers(Answers answers) {

        List<String> sql = List.of(
                String.format("SELECT save_answers_func('%s', '%s', '%s', '%s')",
                        answers.getName(),
                        answers.getQuiz_id(),
                        findQuestionIdByNum(1, answers.getQuiz_id()),
                        answers.getQuestion_1()),

                String.format("SELECT save_answers_func('%s', '%s', '%s', '%s')",
                        answers.getName(),
                        answers.getQuiz_id(),
                        findQuestionIdByNum(2, answers.getQuiz_id()),
                        answers.getQuestion_2()),

                String.format("SELECT save_answers_func('%s', '%s', '%s', '%s')",
                        answers.getName(),
                        answers.getQuiz_id(),
                        findQuestionIdByNum(3, answers.getQuiz_id()),
                        answers.getQuestion_3()),

                String.format("SELECT save_answers_func('%s', '%s', '%s', '%s')",
                        answers.getName(),
                        answers.getQuiz_id(),
                        findQuestionIdByNum(4, answers.getQuiz_id()),
                        answers.getQuestion_4()),

                String.format("SELECT save_answers_func('%s', '%s', '%s', '%s')",
                        answers.getName(),
                        answers.getQuiz_id(),
                        findQuestionIdByNum(5, answers.getQuiz_id()),
                        answers.getQuestion_5()),

                String.format("SELECT save_answers_func('%s', '%s', '%s', '%s')",
                        answers.getName(),
                        answers.getQuiz_id(),
                        findQuestionIdByNum(6, answers.getQuiz_id()),
                        answers.getQuestion_6()),

                String.format("SELECT save_answers_func('%s', '%s', '%s', '%s')",
                        answers.getName(),
                        answers.getQuiz_id(),
                        findQuestionIdByNum(7, answers.getQuiz_id()),
                        answers.getQuestion_7()),

                String.format("SELECT save_answers_func('%s', '%s', '%s', '%s')",
                        answers.getName(),
                        answers.getQuiz_id(),
                        findQuestionIdByNum(8, answers.getQuiz_id()),
                        answers.getQuestion_8()),

                String.format("SELECT save_answers_func('%s', '%s', '%s', '%s')",
                        answers.getName(),
                        answers.getQuiz_id(),
                        findQuestionIdByNum(9, answers.getQuiz_id()),
                        answers.getQuestion_9()),

                String.format("SELECT save_answers_func('%s', '%s', '%s', '%s')",
                        answers.getName(),
                        answers.getQuiz_id(),
                        findQuestionIdByNum(10, answers.getQuiz_id()),
                        answers.getQuestion_10())
                );

        sql.forEach(jdbc::execute);

    }

    @Override
    public Object findQuestionIdByNum(int question_number, String quizID) {
        String sql = "SELECT * FROM query_qid_by_qnumber_func(?, ?)";

        List<Map<String, Object>> res = jdbc.queryForList(sql, question_number, quizID);
        if (res.isEmpty()) return 0;
        return res.get(0).get("question_id");
    }

    @Override
    public String getQuizScore(String studentName, String quizID) {
        String sql = "SELECT * FROM cal_score_func(?, ?)";
        List<Map<String, Object>> result = jdbc.queryForList(sql, studentName, quizID);
        if (result.isEmpty()) return "no score";
        return (String) result.get(0).get("score");
    }
}
