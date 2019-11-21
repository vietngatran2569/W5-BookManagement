package com.codegym.service;

import com.codegym.model.Book;
import com.codegym.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService  {
    Page<Book> findAll(Pageable pageable);
    Book findById(Long id);

    void save(Book book);

    void remove(Long id);
    Page<Book> findAllByNameContaining(String name, Pageable pageable);
    Iterable<Book> findAllByCategory(Category category);

    Page<Book> findAllByCategory(Pageable pageable, Category category);
    Page<Book> findAllByOrderByPriceAsc(Pageable pageable);
    Page<Book> findAllByOrderByPriceDesc(Pageable pageable);
}
