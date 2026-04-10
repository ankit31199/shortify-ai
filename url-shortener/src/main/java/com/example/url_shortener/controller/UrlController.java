package  com.example.url_shortener.controller;



import com.example.url_shortener.dto.UrlRequest;
import com.example.url_shortener.dto.UrlResponse;
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

    // 🔹 1. Create Short URL
    @PostMapping("api/url/shorten")
    public ResponseEntity<UrlResponse> shorten(@RequestBody UrlRequest request) {

        String shortCode = service.shortenUrl(request.getOriginalUrl());

        return ResponseEntity.ok(
                new UrlResponse("http://localhost:8080/" + shortCode)
        );
    }

    // 🔹 2. Redirect to Original URL
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {

        String originalUrl = service.getOriginalUrl(shortCode);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}