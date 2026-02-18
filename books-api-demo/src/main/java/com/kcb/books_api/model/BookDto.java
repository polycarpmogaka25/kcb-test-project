package com.kcb.books_api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @Email
    private String email;
    private String phoneNumber;
}
