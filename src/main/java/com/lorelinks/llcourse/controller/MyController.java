package com.lorelinks.llcourse.controller;

import com.lorelinks.llcourse.entity.Answers;
import com.lorelinks.llcourse.entity.Book;
import com.lorelinks.llcourse.entity.Car;
import com.lorelinks.llcourse.entity.Question;
import com.lorelinks.llcourse.repository.JdbcBookRepository;
import com.lorelinks.llcourse.repository.JdbcQuestionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")
public class MyController {

    private final JdbcBookRepository jdbcBookRepository;
    private final JdbcQuestionRepository jdbcQuestionRepository;
    public MyController(JdbcBookRepository jdbcBookRepository, JdbcQuestionRepository jdbcQuestionRepository) {
        this.jdbcBookRepository = jdbcBookRepository;
        this.jdbcQuestionRepository = jdbcQuestionRepository;
    }

    @GetMapping("/cars")
    public Set<Car> cars(
            @RequestHeader("name")
            String name) {
        System.out.println("authorize successfully: " + name);
        return Set.of(
                new Car("vw", "black"),
                new Car("bmw", "white")
        );
    }
    @GetMapping("/books/{isbn}")
    public Book queryBookByIsbn(@PathVariable String isbn) {
        return jdbcBookRepository.queryByIsbn(isbn);
    }
    @GetMapping("/randombooks")
    public List<Book> queryRandomBooks() {
        return jdbcBookRepository.queryRandom20Books();
    }
    @GetMapping("/books")
    public List<Book> getPaginatedBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return jdbcBookRepository.queryBooks(page, size);
    }
    @GetMapping("/questions")
    public List<Question> getQuestions(@RequestHeader("quiz-name") String quizName,
                                       @RequestHeader("user") String userId) {
        return jdbcQuestionRepository.findByQuizName(quizName);
    }
    @PostMapping("/answers")
    public Map<String, String> receiveAnswers(@RequestBody Answers answers) {
        System.out.println("Received answers: " + answers);
        jdbcQuestionRepository.saveAnswers(answers);
        Map<String, String> result = new HashMap<>();
        result.put("result", "Answers received successfully");
        return result;
    }
    @GetMapping("/score")
    public Map<String, String> getScore(@RequestHeader("student-name") String studentName,
                                        @RequestHeader("quiz-name") String quizName) {
        Map<String, String> res = new HashMap<>();
        res.put("result", jdbcQuestionRepository.getQuizScore(studentName, quizName));
        return res;
    }

}