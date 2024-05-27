package com.hu6r1s.zzuli.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@ToString
@NoArgsConstructor
@Document(collection = "shorten_urls")
public class ShortenUrl {

    @Transient
    public static final String SEQUENCE_NAME = "shorturl_sequence";

    @Id
    private Long id;

    private String originalUrl;

    private LocalDateTime createdAt = LocalDateTime.now();

    public ShortenUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
