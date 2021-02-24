package com.example.url_shortner_service.resource;

import com.example.url_shortner_service.models.URLShortenRequest;
import com.example.url_shortner_service.service.URLShorterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
public class controller {
    @Autowired
    private URLShorterService urlShorterService;
    @PostMapping("/longurl/")
    ResponseEntity shortenUrl(@RequestBody URLShortenRequest urlShortenRequest){
        return ResponseEntity.ok(urlShorterService.shorten(urlShortenRequest.getUrl()));
    }

    @GetMapping("{shorturl}")
    ResponseEntity getLongURL(@PathVariable("shorturl") String shorturl){
        try {
            return ResponseEntity.ok(urlShorterService.get(shorturl));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
    @PutMapping("/shorturl/expiration")
    ResponseEntity expire(){
        urlShorterService.expire();
        return ResponseEntity.ok().build();
    }

}
