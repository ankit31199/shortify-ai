package com.example.url_shortener.controller;


import com.example.url_shortener.dto.AIResponse;
import com.example.url_shortener.service.AIAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AIAnalyticsService aiService;

    // 🔥 AI Insights Endpoint
    @GetMapping("/ai-insights")
    public AIResponse getAIInsights() {
        System.out.println("🔥 AI ENDPOINT HIT");
        return aiService.generateInsights();
    }
}