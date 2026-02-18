package com.kcb.books_api.service;

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

import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookRepo repo;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void test_delete() {
        var id = 1L;
        doNothing().when(repo).deleteById(anyLong());
        bookService.delete(id);
        verify(repo, times(1)).deleteById(id);
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
