package com.avacorp.blog.payload;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDto {
    private Long id;

    @NotEmpty
    @Size(min = 3, message = "Name must not be empty")
    private String name;

    @NotEmpty
    @Email(message = "Email must be valid")
    private String email;

    @NotEmpty
    @Size(min = 5, message = "Body must have a valid response")
    private String body;

}
