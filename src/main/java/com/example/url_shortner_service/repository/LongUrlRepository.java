package com.example.url_shortner_service.repository;

import com.example.url_shortner_service.Entity.LongURL;
import org.springframework.data.repository.CrudRepository;

public interface LongUrlRepository extends CrudRepository<LongURL,Long> {
}
