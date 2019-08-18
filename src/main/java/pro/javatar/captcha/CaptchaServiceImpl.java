/*
 * @author Serhii Petrychenko / Javatar LLC
 */

package pro.javatar.captcha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    private final static Logger log = LoggerFactory.getLogger(CaptchaServiceImpl.class);
    private final RestTemplate restTemplate;
    private final CaptchaConfiguration config;

    public CaptchaServiceImpl(RestTemplate restTemplate, CaptchaConfiguration configuration) {
        this.restTemplate = restTemplate;
        this.config = configuration;
    }

    @Override
    public void validate(String captchaData) {

        log.debug("Captcha service is {}", (config.isEnabled() ? "enabled" : "disabled"));
        if (!config.isEnabled()) return;

        URI uri = buildUrl(captchaData);
        log.debug("Captcha verification URL is {}", uri);

        CaptchaResponse response = restTemplate.postForObject(uri, null, CaptchaResponse.class);
        log.debug("Verification response from the server {}", response);

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
