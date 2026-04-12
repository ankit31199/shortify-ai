package com.example.url_shortener.service;

import com.example.url_shortener.dto.AIResponse;
import com.example.url_shortener.entity.Url;
import com.example.url_shortener.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AIAnalyticsService {

    @Value("${llm.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final UrlRepository repository;

    public String callLLM(String prompt) {
        System.out.println("🚀 Inside callLLM()");

        String url = "https://openrouter.ai/api/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        String body = """
    {
      "model": "meta-llama/llama-3-8b-instruct",
      "messages": [
        {"role": "user", "content": "%s"}
      ]
    }
    """.formatted(prompt);

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
        );

//        return response.getBody();
        String responseBody = response.getBody();

// Extract only content field manually (quick fix)
        int start = responseBody.indexOf("\"content\":\"") + 11;
        int end = responseBody.indexOf("\"", start);

        String cleaned = responseBody.substring(start, end);

// Fix escaped new lines
        cleaned = cleaned.replace("\\n", "\n");

        return cleaned;
    }

    public AIResponse generateInsights() {

        List<Url> urls = repository.findAll();

        if (urls.isEmpty()) {
            return new AIResponse(0, "N/A", 0, "No data available");
        }

        long totalClicks = 0;
        Url topUrl = urls.get(0);

        StringBuilder data = new StringBuilder(); // ✅ CREATE HERE

        for (Url url : urls) {

            totalClicks += url.getClickCount();

            if (url.getClickCount() > topUrl.getClickCount()) {
                topUrl = url;
            }

            // ✅ BUILD DATA STRING
            data.append(url.getShortCode())
                    .append(": ")
                    .append(url.getClickCount())
                    .append(" clicks\n");
        }

        // 🔥 AI PROMPT
        String prompt = "Analyze this URL click data and give 2 insights:\n" + data;

        System.out.println("🔥 CALLING LLM...");
        System.out.println("PROMPT:\n" + prompt);
        String aiInsight = callLLM(prompt);


        return new AIResponse(
                totalClicks,
                "http://localhost:8080/" + topUrl.getShortCode(),
                topUrl.getClickCount(),
                aiInsight
        );
    }

//    public AIResponse generateInsights() {
//
//        List<Url> urls = repository.findAll();
//
//        if (urls.isEmpty()) {
//            return new AIResponse(0, "N/A", 0, "No data available");
//        }
//
//        long totalClicks = 0;
//        Url topUrl = urls.get(0);
//
//        for (Url url : urls) {
//
//            totalClicks += url.getClickCount();
//
//            if (url.getClickCount() > topUrl.getClickCount()) {
//                topUrl = url;
//            }
//        }
//
//        return new AIResponse(totalClicks,
//                "http://localhost:8080/" + topUrl.getShortCode(),
//                topUrl.getClickCount(),
//                "📈 Engagement is growing");
//    }
}