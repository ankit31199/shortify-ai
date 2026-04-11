package com.example.url_shortener.kafka;

import com.example.url_shortener.entity.Url;
import com.example.url_shortener.repository.UrlRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UrlClickConsumer {

    private final UrlRepository urlRepository;
    @KafkaListener(topics = "url-clicks", groupId="url-group")
    public void consume(String shortCode)
    {
        System.out.println("📥 Received event from Kafka: " + shortCode);
        Url url =urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Url Not Found"));

        url.setClickCount(url.getClickCount()+1);
        urlRepository.save(url);

        System.out.println("Click processved via Kafka :" +shortCode);
    }
    @PostConstruct
    public void init() {
        System.out.println("✅ Kafka Consumer Started");
    }
}
