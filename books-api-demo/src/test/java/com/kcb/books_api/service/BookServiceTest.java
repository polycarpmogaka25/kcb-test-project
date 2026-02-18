package com.kcb.books_api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kcb.books_api.dto.BookDto;
import com.kcb.books_api.entity.Book;
import com.kcb.books_api.exception.ResourceNotFoundException;
import com.kcb.books_api.repo.BookRepo;
import com.kcb.books_api.sevice.BookServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BookServiceTest {
    @Mock
    private BookRepo repo;

    @InjectMocks
    private BookServiceImpl bookService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void test_delete() {
        var id = 1L;
        doNothing().when(repo).deleteById(anyLong());
        bookService.delete(id);
        verify(repo, times(1)).deleteById(id);
    }

    @Test
    void test_create_book() {
        var rawEmail = "senior.dev@kcbgroup.com";
        var rawPhone = "0711223344";

        var request = BookDto.builder()
                .title("Spring Boot Mastery")
                .author("John Doe")
                .email(rawEmail)
                .phoneNumber(rawPhone)
                .build();

        when(repo.save(any(Book.class))).thenAnswer(i -> i.getArguments()[0]);
        var result = bookService.create(request);
        Assertions.assertNotNull(result);

    }

    @Test
    void test_update_book() throws JsonProcessingException {
        String rawEmail = "test@example.com";
        String rawPhone = "0711223344";

        var request = BookDto.builder()
                .title("Spring Boot Mastery")
                .author("John Doe")
                .email(rawEmail)
                .phoneNumber(rawPhone)
                .build();


        var json = mapper.writeValueAsString(request);

        when(repo.findById(1L)).thenReturn(Optional.of(getBook()));

        var result = bookService.update(1L, request);

        //assertTrue(json.contains("07*****"));

        Assertions.assertNull(result);


    }

    @Test
    void shouldReturnBookWhenIdExists() {


        when(repo.findById(anyLong())).thenReturn(Optional.of(getBook()));

        var result = bookService.getById(1L);

        Assertions.assertNotNull(result);
        verify(repo).findById(anyLong());
    }


    @Test
    void test_get_book_by_id_throws_exception_when_not_found() {
        when(repo.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> bookService.getById(1L));

        verify(repo, times(1)).findById(1L);
    }


    private Book getBook() {
        return Book.builder()
                .author("Test author")
                .title("Test title")
                .build();
    }
}
