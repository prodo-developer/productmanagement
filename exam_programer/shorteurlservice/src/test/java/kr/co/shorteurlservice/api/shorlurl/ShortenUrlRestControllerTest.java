package kr.co.shorteurlservice.api.shorlurl;

import kr.co.shorteurlservice.application.ShortenUrlService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ShortenUrlController.class)
class ShortenUrlRestControllerTest {

    @MockBean
    private ShortenUrlService shortenUrlService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("원래의 URL로 리다이렉트 되어야 한다.")
    public void redirectTest() throws Exception {
        String expectedOriginalUrl = "www.facebook.co.kr";

        when(shortenUrlService.getOriginalUrlByShortenUrlKey(any())).thenReturn(expectedOriginalUrl);

        mockMvc.perform(get("/any-key"))
                .andExpect(status().isMovedPermanently())
                .andExpect(header().string("Location", expectedOriginalUrl));
    }

}