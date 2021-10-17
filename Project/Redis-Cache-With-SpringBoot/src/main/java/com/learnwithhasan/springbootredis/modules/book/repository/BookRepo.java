package com.learnwithhasan.springbootredis.modules.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learnwithhasan.springbootredis.modules.book.model.Book;

public interface BookRepo extends JpaRepository<Book, Long> {

}
