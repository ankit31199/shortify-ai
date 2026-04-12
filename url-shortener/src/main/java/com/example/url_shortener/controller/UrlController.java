package  com.example.url_shortener.controller;



import com.example.url_shortener.dto.UrlRequest;
import com.example.url_shortener.dto.UrlResponse;
import com.example.url_shortener.ratelimit.RateLimiterSevice;
import com.example.url_shortener.service.UrlService;



import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController

@RequiredArgsConstructor
public class UrlController {

    private final UrlService service;
    private final RateLimiterSevice rateLimiterSevice;

    // 🔹 1. Create Short URL
    @PostMapping("api/url/shorten")
    public ResponseEntity<?> shorten(@RequestBody UrlRequest request) {

        if(!rateLimiterSevice.isAllowed("user"))
            return ResponseEntity.status(429).body("Too Many Requests");
        String shortCode = service.shortenUrl(request.getOriginalUrl());

        return ResponseEntity.ok(
                new UrlResponse("http://localhost:8080/r" + shortCode)
        );
    }

    // 🔹 2. Redirect to Original URL
    @GetMapping("/r/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {

        String originalUrl = service.getOriginalUrl(shortCode);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}