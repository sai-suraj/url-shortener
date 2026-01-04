package demo.url_shortener.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {

    @Async
    public void recordClick(String shortCode, String userAgent) {
        System.out.println(" >>> click recorded for shortcode :  " + shortCode + " by user : " + userAgent +
                " in thread : " + Thread.currentThread().getName());
        try{
            Thread.sleep(500);
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(" <<< ANALYTICS : Finished processing click for short code : " + shortCode);

    }


}
