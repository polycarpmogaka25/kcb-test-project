package com.kcb.books_api.dto;

import com.kcb.masking.annotation.Mask;
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
    @Mask
    private String email;
    @Mask
    private String phoneNumber;
}
