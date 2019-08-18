/*
 * @author Serhii Petrychenko / Javatar LLC
 */

package pro.javatar.captcha;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.security.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = {CaptchaServiceImpl.class, CaptchaConfiguration.class})
@ImportAutoConfiguration(classes = ConfigurationPropertiesAutoConfiguration.class)
public class CaptchaServiceImplTest {
    private static final String CAPTCHA_DATA = "captcha-data";

    @Autowired
    private CaptchaService captchaService;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private CaptchaConfiguration config;

    private URI uri;

    @Before
    public void setUp() {
        uri = ((CaptchaServiceImpl) captchaService).buildUrl(CAPTCHA_DATA);
    }

    @Test
    public void validate() {

        when(restTemplate.postForObject(eq(uri), isNull(), eq(CaptchaResponse.class)))
                .thenReturn(createCaptchaResponse());

        captchaService.validate(CAPTCHA_DATA);

        verify(restTemplate, times(1)).postForObject(eq(uri), isNull(), eq(CaptchaResponse.class));
    }

    @Test(expected = CaptchaException.class)
    public void validateResponseIsNull() {

        when(restTemplate.postForObject(eq(uri), isNull(), eq(CaptchaResponse.class))).thenReturn(null);

        captchaService.validate(CAPTCHA_DATA);
    }

    @Test(expected = CaptchaException.class)
    public void validateBotDetected() {

        CaptchaResponse response = createCaptchaResponse();
        response.setSuccess(false);
        response.getErrors().add("Bot detected");

        when(restTemplate.postForObject(eq(uri), isNull(), eq(CaptchaResponse.class))).thenReturn(response);

        captchaService.validate(CAPTCHA_DATA);
    }

    @Test
    public void buildUrl() {
        String expectedUrl = "http://google-captcha-verify-url?secret-param=secret-code&response-param=" + CAPTCHA_DATA;

        assertThat(uri.toString(), is(expectedUrl));
    }

    @Test
    public void disableCaptcha(){
        config.setEnabled(false);

        captchaService.validate(CAPTCHA_DATA);

        verify(restTemplate, times(0)).postForObject(eq(uri), isNull(), eq(CaptchaResponse.class));
    }

    private CaptchaResponse createCaptchaResponse() {
        CaptchaResponse response = new CaptchaResponse();
        response.setSuccess(true);
        response.setChallenge(LocalDateTime.now());
        response.setHostname("localhost");
        response.setScore(0.9);
        return response;
    }
}