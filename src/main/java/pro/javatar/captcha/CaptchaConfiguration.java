/*
 * @author Serhii Petrychenko / Javatar LLC
 */

package pro.javatar.captcha;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "recaptcha")
public class CaptchaConfiguration {
    public static final String RECAPTCHA_RESPONSE = "g-recaptcha-response";
    private String secretKeyParam = "secretKey";
    private String responseParam = "response";
    private boolean isEnabled = true;
    private String siteVerifyUrl = "https://www.google.com/recaptcha/api/siteverify";
    private String secretKey;
    private String siteKey;


    public String getSecretKeyParam() {
        return secretKeyParam;
    }

    public void setSecretKeyParam(String secretKeyParam) {
        this.secretKeyParam = secretKeyParam;
    }

    public String getResponseParam() {
        return responseParam;
    }

    public void setResponseParam(String responseParam) {
        this.responseParam = responseParam;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSiteVerifyUrl() {
        return siteVerifyUrl;
    }

    public void setSiteVerifyUrl(String siteVerifyUrl) {
        this.siteVerifyUrl = siteVerifyUrl;
    }

    public String getSiteKey() {
        return siteKey;
    }

    public void setSiteKey(String siteKey) {
        this.siteKey = siteKey;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
