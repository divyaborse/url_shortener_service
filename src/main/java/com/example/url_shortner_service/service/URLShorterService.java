package com.example.url_shortner_service.service;

public interface URLShorterService {
    String shorten(String longUrl);
    String get(String shortUrl) throws Exception;
    void expire();

}
