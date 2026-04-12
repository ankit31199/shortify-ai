package com.example.url_shortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AIResponse {

    private long totalClicks;
    private String topUrl;
    private long clickCount;
    private String insight;
}
