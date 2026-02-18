package com.kcb.books_api.repo;

import com.kcb.books_api.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book, Long> {
}
