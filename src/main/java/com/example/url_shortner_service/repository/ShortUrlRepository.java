package com.example.url_shortner_service.repository;

import com.example.url_shortner_service.Entity.ShortURL;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ShortUrlRepository extends CrudRepository<ShortURL,Long> {


    //get shorturl  if the url is not expired
    Optional<ShortURL>  findByIdAndExpired(Long id,Boolean expired);
}
