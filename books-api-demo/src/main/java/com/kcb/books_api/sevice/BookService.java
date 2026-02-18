package com.kcb.books_api.sevice;

import com.kcb.books_api.dto.BookDto;
import com.kcb.books_api.entity.Book;
import jakarta.validation.Valid;

public interface BookService {
    Book create(@Valid BookDto request);

    Book getById(Long projectId);

    Book update(Long bookId, @Valid BookDto request);

    void delete(Long bookId);
}
