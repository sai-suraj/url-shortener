package demo.url_shortener.service;


import demo.url_shortener.model.UrlEntity;
import demo.url_shortener.repository.UrlRepository;
import demo.url_shortener.util.Base62;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository){
        this.urlRepository = urlRepository;
    }

    @Cacheable(value = "urls", key = "#shortCode")
    @Transactional
    public Optional<UrlEntity> getOriginalUrl(String shortCode){
        System.out.println(">>> Database access for shortcode : " + shortCode);
        return urlRepository.findByShortCode(shortCode);
    }

    public UrlEntity shortenUrl(String originalUrl){
        UrlEntity entity = new UrlEntity();
        entity.setOriginalUrl(originalUrl);
        urlRepository.save(entity);
        String shortCode = Base62.encode(entity.getId());
        entity.setShortCode(shortCode);
        return urlRepository.save(entity);
    }

}
