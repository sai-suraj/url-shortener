package demo.url_shortener;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import demo.url_shortener.model.UrlEntity;
import demo.url_shortener.repository.UrlRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class UrlShortenerApplicationTests {

    @Autowired
    private UrlRepository urlRepository;

    @Test
    void contextLoads() {
        // This test will verify that the application context loads successfully
        assertThat(urlRepository).isNotNull();
    }

    @Test
    void shouldSaveAndRetrieveUrl() {
        // Given
        UrlEntity url = new UrlEntity();
        url.setOriginalUrl("https://example.com");
        url.setShortCode("abc123");
        
        // When
        UrlEntity savedUrl = urlRepository.save(url);
        
        // Then
        assertThat(savedUrl).isNotNull();
        assertThat(savedUrl.getId()).isNotNull();
        assertThat(savedUrl.getOriginalUrl()).isEqualTo("https://example.com");
        assertThat(savedUrl.getShortCode()).isEqualTo("abc123");
    }
}
