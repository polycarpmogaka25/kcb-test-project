package com.kcb.books_api.sevice;

import com.kcb.books_api.dto.BookDto;
import com.kcb.books_api.entity.Book;
import com.kcb.books_api.exception.ResourceNotFoundException;
import com.kcb.books_api.repo.BookRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;

    @Override
    public Book create(BookDto request) {
        log.debug("Request to create a book : {}", request);
        return bookRepo.save(Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .build());
    }

    @Override
    public Book getById(Long bookId) {
        return bookRepo.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    @Override
    public Book update(Long bookId, BookDto request) {
        log.debug("Request to update a book : {}", request);
        var book = bookRepo.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setEmail(request.getEmail());
        book.setPhoneNumber(request.getPhoneNumber());
        return bookRepo.save(book);
    }

    @Override
    public void delete(Long bookId) {
        bookRepo.deleteById(bookId);
    }
}
