package com.example.url_shortener.kafka;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UrlClickProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC= "url-clicks";

    public void sendClickEvent(String shortCode)
    {
        System.out.println("🚀 Inside Producer: " + shortCode);
        kafkaTemplate.send(TOPIC, shortCode);
    }
}
