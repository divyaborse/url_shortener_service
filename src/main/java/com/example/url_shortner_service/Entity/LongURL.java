package com.example.url_shortner_service.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LongURL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    @Column(nullable = false)
    private String url;
    @Column(insertable = false,updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    @OneToOne(cascade = CascadeType.ALL)
    private ShortURL shortURL;

}
