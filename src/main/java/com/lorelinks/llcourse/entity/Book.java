package com.lorelinks.llcourse.entity;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private String isbn;
    private String title;
    private String author;
    private String yearPublish;
    private String publisher;
    private String imageUrlSmall;
    private String imageUrlMedium;
    private String imageUrlLarge;

}
