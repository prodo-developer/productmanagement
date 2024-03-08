package kr.co.shorteurlservice.application;

import kr.co.shorteurlservice.application.request.ShortenUrlCreateRequestDto;
import kr.co.shorteurlservice.application.response.ShortenUrlCreateResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ShortenUrlServiceTest {

    @Autowired
    private ShortenUrlService shortenUrlService;

    @Test
    @DisplayName("URL을 단축한 후 단축된 URL 키로 조회하면 원래 URL이 조회되어야 한다.")
    public void shortenUrlAddTest() {
        String expectedOriginalUrl = "https://music.youtube.com/";
        ShortenUrlCreateRequestDto shortenUrlCreateRequestDto = new ShortenUrlCreateRequestDto(expectedOriginalUrl);

        ShortenUrlCreateResponseDto shortenUrlCreateResponseDto = shortenUrlService.generateShortenUrl(shortenUrlCreateRequestDto);
        String shortenUrlKey = shortenUrlCreateResponseDto.getShortenUrlKey();

        String originalUrl = shortenUrlService.getOriginalUrlByShortenUrlKey(shortenUrlKey);
        assertTrue(originalUrl.equals(expectedOriginalUrl));
    }
}