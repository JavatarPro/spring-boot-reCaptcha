# Spring Boot reCaptcha

## How to use: 
1. Clone or download sources from [gitHub](https://github.com/JavatarPro/spring-boot-reCaptcha). Run command **mvn clean package**
2. Add **spring-boot-reCaptcha-0.1-SNAPSHOT.jar** dependency to your application
3. Goto https://www.google.com/recaptcha/admin. Create new captcha
4. Add to your **application.yml** file next parameters
    ```
    recaptcha:
        secretKey: PLACE_HERE_SECRET_KEY
        siteKey: PLACE_HERE_SITE_KEY
    ```
    and replace **secretKey** and **siteKey** parameters from just created google reCaptcha page
  
5. To see howto integrate reCaptcha with your web page, please visit [gitHub page](https://github.com/JavatarPro/spring-boot-reCaptcha-example)