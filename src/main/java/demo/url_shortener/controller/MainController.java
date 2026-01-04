package demo.url_shortener.controller;

import demo.url_shortener.model.UrlEntity;
import demo.url_shortener.service.AnalyticsService;
import demo.url_shortener.service.UrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class MainController {
    private final UrlService urlService;
    private final AnalyticsService analyticsService;

    public MainController(UrlService urlService, AnalyticsService analyticsService){
        this.urlService = urlService;
        this.analyticsService = analyticsService;
    }

    @GetMapping("/")
    public ResponseEntity<String> openingResponse(){
        return ResponseEntity.ok("Welcome to the URL Shortener API");
    }

    @PostMapping("/api/v1/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody String originalUrl){
        if(originalUrl == null || originalUrl.isBlank()){
            return ResponseEntity.badRequest().body("Original Url provided should not be blank or empty");
        }
        String sanitizedUrl = originalUrl.trim().replaceAll("^\"|\"$", "");
        UrlEntity entity = urlService.shortenUrl(sanitizedUrl);
        String shortUrl = "http://localhost:8080/" + entity.getShortCode();
        return new ResponseEntity<>(shortUrl, HttpStatus.CREATED);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginalUrl(
            @PathVariable String shortCode,
            @RequestHeader(value = HttpHeaders.USER_AGENT, required = false) String userAgent){
        analyticsService.recordClick(shortCode, userAgent != null ? userAgent : "Unknown  ");
        return urlService.getOriginalUrl(shortCode).map(entity -> {
            String originalUrl = entity.getOriginalUrl().trim();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(originalUrl));
            return new ResponseEntity<Void>(headers, HttpStatus.FOUND);
        }).orElseGet(()->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
