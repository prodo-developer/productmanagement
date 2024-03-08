package kr.co.shorteurlservice.application;

import kr.co.shorteurlservice.api.domain.ShortenUrl;
import kr.co.shorteurlservice.api.domain.ShortenUrlRepository;
import kr.co.shorteurlservice.application.request.ShortenUrlCreateRequestDto;
import kr.co.shorteurlservice.application.request.ShortenUrlInformationDto;
import kr.co.shorteurlservice.application.response.ShortenUrlCreateResponseDto;
import kr.co.shorteurlservice.core.aop.endpoint.exception.LackOfShortenUrlKeyException;
import kr.co.shorteurlservice.core.aop.endpoint.exception.NotFoundShortenUrlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortenUrlService {

    private final ShortenUrlRepository shortenUrlRepository;

    @Autowired
    public ShortenUrlService(ShortenUrlRepository shortenUrlRepository) {
        this.shortenUrlRepository = shortenUrlRepository;
    }


    public ShortenUrlCreateResponseDto generateShortenUrl(ShortenUrlCreateRequestDto shortenUrlCreateRequestDto) {

        String originalUrl = shortenUrlCreateRequestDto.getOriginalUrl();
//        String shortenUrlKey = ShortenUrl.generateShortenUrlKey();
        String shortenUrlKey = getUniqueShortenUrlKey();

        ShortenUrl shortenUrl = new ShortenUrl(originalUrl, shortenUrlKey);
        shortenUrlRepository.saveShortenUrl(shortenUrl);

        ShortenUrlCreateResponseDto shortenUrlCreateResponseDto = new ShortenUrlCreateResponseDto(shortenUrl);

        return shortenUrlCreateResponseDto;
    }

    public ShortenUrlInformationDto getShortenUrlInfomationByShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

        if(null == shortenUrl)
            throw new NotFoundShortenUrlException();

        ShortenUrlInformationDto shortenUrlInformationDto = new ShortenUrlInformationDto(shortenUrl);
        return shortenUrlInformationDto;
    }

    /**
     * 단축 URL 리다이렉트
     * 단축퇸 URL -> 원본 URL로 "리다이렉트 될 때마다 카운트 증가"
     * @param shortenUrlKey
     * @return
     */
    public String getOriginalUrlByShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

        if(null == shortenUrl)
            throw new NotFoundShortenUrlException();

        shortenUrl.increaseRedirectCount();
        shortenUrlRepository.saveShortenUrl(shortenUrl);

        String originalUrl = shortenUrl.getOriginalUrl();

        return originalUrl;
    }

    private String getUniqueShortenUrlKey() {
        final int MAX_RETRY_COUNT = 5;
        int count = 0;

        while(count++ < MAX_RETRY_COUNT) {
            String shortenUrlKey = ShortenUrl.generateShortenUrlKey();
            ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

            if(null == shortenUrl) {
                return shortenUrlKey;
            }
        }

        throw new LackOfShortenUrlKeyException();
    }
}
