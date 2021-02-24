package com.example.url_shortner_service.service;

import com.example.url_shortner_service.Entity.LongURL;
import com.example.url_shortner_service.Entity.ShortURL;
import com.example.url_shortner_service.repository.LongUrlRepository;
import com.example.url_shortner_service.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
@Service

public class URLShorterServiceImpl  implements  URLShorterService{
    @Autowired
    private ShortUrlRepository shortUrlRepository;
    @Autowired
    private LongUrlRepository longUrlRepository;
    @Override
    public String shorten(String longUrlString) {
       ShortURL shortURL = ShortURL.builder().
               domain("localhost:8081").protocol("http").expired(false).createdAt(LocalDateTime.now()).
               build();
       LongURL longURL = LongURL.builder().url(longUrlString).
               createdAt(LocalDateTime.now()).shortURL(shortURL).build();
       shortURL.setLongURL(longURL);

       longURL = longUrlRepository.save(longURL);
      /* String path = getsShortenPath(longURL.getId());
       shortURL = longURL.getShortURL();

       shortUrlRepository.save(shortURL);
       return getsShortenPath(longURL.getShortURL().getId());*/
        //build tiny url
        URI uri = URI.create(shortURL.getProtocol()+"://"+shortURL.getDomain()+"/"+getsShortenPath(longURL.getShortURL().getId()));
        return uri.toString();
    }

    private String getsShortenPath(Long id) {

        String path = Base64.getEncoder().encodeToString(String.valueOf(id).getBytes());
        return  path;
    }

    @Override
    public String get(String shortUrlRequest) throws Exception {
        Long id = 0l;
        try {
            id = decodePath(shortUrlRequest);
        }catch (Exception e){
            throw new Exception("invalid shortUrl");
        }
        ShortURL shortURL = shortUrlRepository.findByIdAndExpired(id,false)
                .orElseThrow(()->  new Exception("shortURL doesn't exist or expired"));
        return shortURL.getLongURL().getUrl();
    }

    @Override
    public void expire() {
        Iterable<ShortURL> shortURLList = shortUrlRepository.findAll();
        shortURLList.forEach(
                shortURL -> {
                    if(!shortURL.isExpired()){
                        if(shortURL.getCreatedAt().isBefore(LocalDateTime.now().minusSeconds(10))){
                            shortURL.setExpired(true);
                            shortUrlRepository.save(shortURL);
                        }
                    }
                });
    }

    private Long decodePath(String shortUrl) {
        byte[] bytes = Base64.getDecoder().decode(shortUrl);
        String str = new String( bytes, StandardCharsets.UTF_8);
        return Long.parseLong(str);
    }
}

