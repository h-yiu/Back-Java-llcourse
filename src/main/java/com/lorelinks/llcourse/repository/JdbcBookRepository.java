package com.lorelinks.llcourse.repository;

import com.lorelinks.llcourse.entity.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcBookRepository implements BookRepository{
    private final JdbcTemplate jdbc;

    public JdbcBookRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Book queryByIsbn(String isbn)  {

        /*
        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/llcoursedb","riceuser","ricepw");
        String sqlFunc = "SELECT * FROM query_book_func('" + isbn + "')";
        PreparedStatement pstmt = conn.prepareStatement(sqlFunc);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            System.out.println(rs.getString("title"));
        }
        rs.close();
        pstmt.close();
        conn.close();
        */

        String sqlQueryFunc = "SELECT * FROM query_book_func(?)";
        List<Map<String, Object>> res = jdbc.queryForList(sqlQueryFunc, isbn);
//        System.out.println(res);

        if (!res.isEmpty()) {
            Book book = new Book();
            book.setIsbn(isbn);
            book.setAuthor((String) res.get(0).get("author"));
            book.setTitle((String) res.get(0).get("title"));
            book.setYearPublish((String) res.get(0).get("year_of_pub"));
            book.setPublisher((String) res.get(0).get("publisher"));
            book.setImageUrlSmall((String) res.get(0).get("image_url_s"));
            book.setImageUrlMedium((String) res.get(0).get("image_url_m"));
            book.setImageUrlLarge((String) res.get(0).get("image_url_l"));
            return book;
        } else {
            return null;
        }
    }
    @Override
    public List<Book> queryRandom20Books() {
        String sql = "SELECT * FROM query_20_books_func()";
        List<Map<String, Object>> res = jdbc.queryForList(sql);
//        System.out.println(res);
        List<Book> books = new ArrayList<>();
        for (Map<String, Object> data : res) {
            Book book = new Book();
            book.setIsbn((String) data.get("isbn"));
            book.setAuthor((String) data.get("author"));
            book.setTitle((String) data.get("title"));
            book.setYearPublish((String) data.get("year_of_pub"));
            book.setPublisher((String) data.get("publisher"));
            book.setImageUrlSmall((String) data.get("image_url_s"));
            book.setImageUrlMedium((String) data.get("image_url_m"));
            book.setImageUrlLarge((String) data.get("image_url_l"));
            books.add(book);
        }
        return books;
    }

    @Override
    public List<Book> queryBooks(int pageNum, int size) {
        String sql = "SELECT * FROM query_books_func(?, ?)";
        int offset = (pageNum - 1) * size;
        List<Map<String, Object>> res = jdbc.queryForList(sql, size, offset);
        List<Book> books = new ArrayList<>();
        for (Map<String, Object> data : res) {
            Book book = new Book();
            book.setIsbn((String) data.get("isbn"));
            book.setAuthor((String) data.get("author"));
            book.setTitle((String) data.get("title"));
            book.setYearPublish((String) data.get("year_of_pub"));
            book.setPublisher((String) data.get("publisher"));
            book.setImageUrlSmall((String) data.get("image_url_s"));
            book.setImageUrlMedium((String) data.get("image_url_m"));
            book.setImageUrlLarge((String) data.get("image_url_l"));
            books.add(book);
        }
        return books;
    }

}
