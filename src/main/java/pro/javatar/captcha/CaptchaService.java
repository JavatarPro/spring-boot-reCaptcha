/*
 * @author Serhii Petrychenko / Javatar LLC
 */

package pro.javatar.captcha;

public interface CaptchaService {
    void validate(String captchaData);
}
