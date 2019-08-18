/*
 * @author Serhii Petrychenko / Javatar LLC
 */

package pro.javatar.captcha;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class CaptchaResponse {
    private boolean success = false;
    @JsonProperty("challenge_ts")
    private LocalDateTime challenge;
    private String hostname;
    private Double score;
    @JsonProperty("apk_package_name")
    private String packageName;
    @JsonProperty("error-codes")
    private List<String> errors = new ArrayList<>();

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public LocalDateTime getChallenge() {
        return challenge;
    }

    public void setChallenge(LocalDateTime challenge) {
        this.challenge = challenge;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "CaptchaResponse{" +
                       "success=" + success +
                       ", challenge=" + challenge +
                       ", hostname='" + hostname + '\'' +
                       ", score=" + score +
                       ", packageName='" + packageName + '\'' +
                       ", errors=" + errors +
                       '}';
    }
}
