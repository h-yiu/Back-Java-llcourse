package com.lorelinks.llcourse.repository;

import com.lorelinks.llcourse.entity.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookRepository {
    Book queryByIsbn(String isbn) throws ClassNotFoundException, SQLException;

    List<Book> queryRandom20Books();

    List<Book> queryBooks(int pageNum, int size);
}
