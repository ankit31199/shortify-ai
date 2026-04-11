package com.example.url_shortener.service;

import com.example.url_shortener.entity.Url;
import com.example.url_shortener.kafka.UrlClickProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.example.url_shortener.repository.UrlRepository;
import com.example.url_shortener.util.Base62Encoder;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final Base62Encoder encoder;
    private final RedisTemplate<String, Object> redisTemplate;
    private final UrlClickProducer producer;

    public String shortenUrlInternal(String originalUrl)
    {
        Optional<Url> existing = urlRepository.findByOriginalUrl(originalUrl);
        if(existing.isPresent())
        {
            return existing.get().getShortCode();
        }
        Url url= Url.builder().originalUrl(originalUrl).createdAt(LocalDateTime.now()).clickCount(0L).build();

        url=urlRepository.save(url);

        String shortCode= encoder.encode(url.getId());
        url.setShortCode(shortCode);

        urlRepository.save(url);
        return shortCode;
    }
    public String shortenUrl(String originalUrl) {

        try {
            return shortenUrlInternal(originalUrl);

        } catch (DataIntegrityViolationException e) {

            // 🔥 fallback: someone else inserted same URL
            return urlRepository.findByOriginalUrl(originalUrl)
                    .orElseThrow(() -> new RuntimeException("URL exists but not found"))
                    .getShortCode();
        }
    }

    public String getOriginalUrl(String shortCode)
    {
        System.out.println("Checking Redis...");
        Object cachedUrl=redisTemplate.opsForValue().get(shortCode);
        if(cachedUrl != null) {
            System.out.println("Returned from redis");
            return cachedUrl.toString();
        }

        Url url= urlRepository.findByShortCode(shortCode)
                .orElseThrow(() ->  new RuntimeException("URl not found"));
        System.out.println("Saving to Redis...");
        redisTemplate.opsForValue().set(shortCode, url.getOriginalUrl(),10,
                TimeUnit.MINUTES);

//        url.setClickCount(url.getClickCount() + 1);
//        urlRepository.save(url);
        System.out.println("📤 Sending event to Kafka: " + shortCode);
        producer.sendClickEvent(shortCode);

        return url.getOriginalUrl();
    }
}
