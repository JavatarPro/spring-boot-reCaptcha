/*
 * @author Serhii Petrychenko / Javatar LLC
 */

package pro.javatar.captcha;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    private final RestTemplate restTemplate;
    private final CaptchaConfiguration config;

    public CaptchaServiceImpl(RestTemplate restTemplate, CaptchaConfiguration configuration) {
        this.restTemplate = restTemplate;
        this.config = configuration;
    }

    @Override
    public void validate(String captchaData) {
        URI uri = buildUrl(captchaData);

        CaptchaResponse response = restTemplate.postForObject(uri, null, CaptchaResponse.class);

        if (response == null) {
            throw new CaptchaException("Captcha can't be verified");
        }

        if (!response.isSuccess()) {
            List<String> errors = response.getErrors();
            String message = errors.isEmpty() ? "Unknown error" : errors.get(0);
            throw new CaptchaException(message);
        }
    }

    URI buildUrl(String captchaData) {
        return UriComponentsBuilder
                       .fromHttpUrl(config.getSiteVerifyUrl())
                       .queryParam(config.getSecretKeyParam(), config.getSecretKey())
                       .queryParam(config.getResponseParam(), captchaData)
                       .build().toUri();
    }
}
