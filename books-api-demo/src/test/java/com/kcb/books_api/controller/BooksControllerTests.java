package com.kcb.books_api.controller;

import com.kcb.books_api.entity.Book;
import com.kcb.books_api.model.BookDto;
import com.kcb.books_api.repo.BookRepo;
import com.kcb.books_api.sevice.BookService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BooksControllerTests {
    @InjectMocks
    private BooksController controller;

    @Mock
    private BookService bookService;

    @Mock
    private BookRepo bookRepo;


    private WebTestClient webTestClient;

    @AfterAll
    static void teardown() {
        Mockito.reset();
    }

    @BeforeEach
    void setup() {
        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void test_create_book() {
        when(bookService.create(any(BookDto.class))).thenReturn(getBook());

        var baseUrl = "/books";

        webTestClient.post().uri(baseUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(getRequest())
                .exchange().expectStatus().isOk();

        verify(bookService).create(any(BookDto.class));
    }

    @Test
    void test_get_book_by_id() {
        when(bookService.getById(
                any(Long.class))).thenReturn(getBook());

        var baseUrl = "/books/1";

        webTestClient.get().uri(baseUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk();

        verify(bookService).getById(any(Long.class));
    }

    @Test
    void test_get_all_books() {
        when(bookRepo.findAll()).thenReturn(List.of(getBook()));

        var baseUrl = "/books";

        webTestClient.get().uri(baseUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk();

        verify(bookRepo).findAll();
    }

    private BookDto getRequest() {

        return BookDto.builder()
                .author("test")
                .title("test")
                .email("test@test.com")
                .phoneNumber("072000000")
                .build();
    }

    private Book getBook() {
        return Book.builder()
                .author("Test author")
                .title("Test title")
                .build();
    }
}
