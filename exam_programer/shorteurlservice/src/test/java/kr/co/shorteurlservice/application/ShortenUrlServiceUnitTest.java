package kr.co.shorteurlservice.application;

import kr.co.shorteurlservice.api.domain.ShortenUrl;
import kr.co.shorteurlservice.api.domain.ShortenUrlRepository;
import kr.co.shorteurlservice.application.request.ShortenUrlCreateRequestDto;
import kr.co.shorteurlservice.core.aop.endpoint.exception.LackOfShortenUrlKeyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShortenUrlServiceUnitTest {

    @Mock
    private ShortenUrlRepository shortenUrlRepository;

    @InjectMocks
    private ShortenUrlService shortenUrlService;

    @Test
    @DisplayName("단축 URL이 계속 중복되면 LackOfShortenUrlKeyException 예외가 발생해야한다.")
    public void throwLackOfShortenUrlKeyExceptionTest() {
        ShortenUrlCreateRequestDto shortenUrlCreateRequestDto = new ShortenUrlCreateRequestDto(null);

        when(shortenUrlRepository.findShortenUrlByShortenUrlKey(any())).thenReturn(new ShortenUrl(null, null));

        Assertions.assertThrows(LackOfShortenUrlKeyException.class, () -> {
            shortenUrlService.generateShortenUrl(shortenUrlCreateRequestDto);
        });
    }
}