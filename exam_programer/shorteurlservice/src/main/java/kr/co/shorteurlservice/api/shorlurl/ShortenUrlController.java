package kr.co.shorteurlservice.api.shorlurl;

import jakarta.validation.Valid;
import kr.co.shorteurlservice.application.ShortenUrlService;
import kr.co.shorteurlservice.application.request.ShortenUrlCreateRequestDto;
import kr.co.shorteurlservice.application.request.ShortenUrlInformationDto;
import kr.co.shorteurlservice.application.response.ShortenUrlCreateResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @return
     */
    @RequestMapping(value = "/{shorentUrlKey}", method = RequestMethod.GET)
    public ResponseEntity<?> redirectShortenUrl(@PathVariable String shorentUrlKey) {
        return ResponseEntity.ok().body(null);
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
