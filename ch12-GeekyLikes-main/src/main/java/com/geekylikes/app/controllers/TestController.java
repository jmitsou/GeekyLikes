package com.geekylikes.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${geekylikes.app.newsApiKey}")
    private String apiKey;

    @GetMapping("/all")
    public String allAccess() {
        return "public content";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User content";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public String modAccess() {
        return "Mod content";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "admin content";
    }

    @GetMapping("/news/{g}")
    public ResponseEntity<?> getNewsArticles(@PathVariable String q){
        String url = "https://newsapi.org/v2/everything?sortBy=popularity&apiKey=" + apiKey + "&q=" + q;

        class NewsResponse{
            private String status;
            private Integer totalResults;
            private List<Article> articles;


        }

        NewsResponse response = restTemplate.getForObject(url, NewsResponse.class);

        return ResponseEntity.ok(response)
    }
}
