package com.avacorp.blog.payload;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDto {
    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 chars")
    private  String title;

    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10")
    private  String description;

    @NotEmpty
    private  String content;


    private Set<CommentDto> comments;
}
