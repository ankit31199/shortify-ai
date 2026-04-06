package service;

import entity.Url;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.UrlRepository;
import util.Base62Encoder;


import java.time.LocalDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final Base62Encoder encoder;

    public String urlShortener(String originalUrl)
    {
        Url url= Url.builder().originalUrl(originalUrl).createdAt(LocalDateTime.now()).clickCount(0L).build();

        url=urlRepository.save(url);

        String shortCode= encoder.encode(url.getId());
        url.setShortCode(shortCode);

        urlRepository.save(url);
        return shortCode;
    }

    public String getOriginalUrl(String shortCode)
    {
        Url url= urlRepository.findByShortCode(shortCode)
                .orElseThrow(() ->  new RuntimeException("URl not found"));

        url.setClickCount(url.getClickCount() + 1);
        urlRepository.save(url);

        return url.getOriginalUrl();
    }
}
