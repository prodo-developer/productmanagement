package kr.co.shorteurlservice.api.shorlurl;

import jakarta.validation.Valid;
import kr.co.shorteurlservice.application.ShortenUrlService;
import kr.co.shorteurlservice.application.request.ShortenUrlCreateRequestDto;
import kr.co.shorteurlservice.application.request.ShortenUrlInformationDto;
import kr.co.shorteurlservice.application.response.ShortenUrlCreateResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ShortenUrlController {

    private final ShortenUrlService shortenUrlService;

    @Autowired
    public ShortenUrlController(ShortenUrlService shortenUrlService) {
        this.shortenUrlService = shortenUrlService;
    }

    @RequestMapping(value = "/shortenUrl", method = RequestMethod.POST)
    public ResponseEntity<ShortenUrlCreateResponseDto> createShortenUrl(
            @Valid @RequestBody ShortenUrlCreateRequestDto shortenUrlCreateRequestDto) {

        ShortenUrlCreateResponseDto shortenUrlCreateResponseDto = shortenUrlService.generateShortenUrl(shortenUrlCreateRequestDto);

        return ResponseEntity.ok(shortenUrlCreateResponseDto);
    }

    /**
     * 단축 URL 리다이렉트
     * 서비스에서 가져온 originalUrl을 응답 헤더의 Location으로 설정해 주고
     * 상태 코드를 301로 변경해 주는 두 가지 동작을 해야 한다.
     * @return
     */
    @RequestMapping(value = "/{shortenUrlKey}", method = RequestMethod.GET)
    public ResponseEntity<?> redirectShortenUrl(@PathVariable String shortenUrlKey) throws URISyntaxException {

        String originalUrl = shortenUrlService.getOriginalUrlByShortenUrlKey(shortenUrlKey);

        URI redirectUri = new URI(originalUrl);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);

        return new ResponseEntity(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    /**
     * 단축 URL 정보 조회 API
     * @return
     */
    @RequestMapping(value = "/shortenUrl/{shortenUrlKey}", method = RequestMethod.GET)
    public ResponseEntity<ShortenUrlInformationDto> getShortenUrlInformation(@PathVariable String shortenUrlKey) {
        ShortenUrlInformationDto shortenUrlInformationDto = shortenUrlService.getShortenUrlInfomationByShortenUrlKey(shortenUrlKey);
        return ResponseEntity.ok().body(shortenUrlInformationDto);
    }

}
