package com.kcb.books_api.controller;

import com.kcb.books_api.dto.BookDto;
import com.kcb.books_api.entity.Book;
import com.kcb.books_api.repo.BookRepo;
import com.kcb.books_api.sevice.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Books api", description = "Operations related to Books and their associated Tasks")
public class BooksController {

    private final BookService bookService;

    private final BookRepo bookRepo;

    @PostMapping
    @Operation(summary = "Create a new project", description = "Creates a book with a name ,title author description")
    @ApiResponse(responseCode = "200", description = "Book created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public ResponseEntity<Book> create(@Valid @RequestBody BookDto request) {
        log.debug("Request to create a book : {}", request);
        return ResponseEntity.ok(bookService.create(request));
    }

    @GetMapping
    @Operation(summary = "List all books", description = "Retrieves a full list of books along with their details")
    @ApiResponse(responseCode = "200", description = "Successful retrieval")
    public List<Book> getAll() {
        return bookRepo.findAll();
    }

    @GetMapping("/{bookId}")
    @Operation(summary = "Get book by ID", description = "Retrieves detailed information for a specific book")
    @ApiResponse(responseCode = "200", description = "Book found")
    @ApiResponse(responseCode = "404", description = "Book not found")
    public Book getById(@PathVariable Long bookId) {
        return bookService.getById(bookId);
    }

    @PutMapping("/{bookId}")
    @Operation(summary = "Update book details", description = "Updates an existing book's title, description, status, or due date")
    @ApiResponse(responseCode = "200", description = "Book updated successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @ApiResponse(responseCode = "400", description = "Invalid update data")
    public Book update(@PathVariable Long bookId,
                       @Valid @RequestBody BookDto request) {
        return bookService.update(bookId, request);
    }

    @DeleteMapping("/{bookId}")
    public void delete(@PathVariable Long bookId) {
        bookService.delete(bookId);
    }

}
