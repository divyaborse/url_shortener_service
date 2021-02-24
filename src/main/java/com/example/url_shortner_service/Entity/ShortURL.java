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
public class ShortURL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;


    @Column(nullable = false)
    private String domain= "localhost:8081";
    private String protocol = "http";
    private  boolean expired = false;
    @Column(insertable = false,updatable = false)
    private LocalDateTime createdAt;
    @OneToOne(cascade = CascadeType.ALL)
    private LongURL longURL;

}
