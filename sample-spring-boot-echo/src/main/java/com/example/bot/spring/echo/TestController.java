package com.example.bot.spring.echo;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
public class TestController {
    @Autowired
    private final HttpServletRequest request;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public TestController(HttpServletRequest request) {
        this.request = request;
    }

    @PostMapping("test")
    public void test(@RequestBody Object o) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        log.info("event object: " + o);
        log.info("request: " + request.getHeader("x-line-signature"));

        String channelSecret = "3c5542ac2dc6fa1cc6f131f984bc3622"; // Channel secret string
        String httpRequestBody = o.toString(); // Request body string
        SecretKeySpec key = new SecretKeySpec(channelSecret.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);
        byte[] source = httpRequestBody.getBytes("UTF-8");
        String signature = Base64.encodeBase64String(mac.doFinal(source));
// Compare x-line-signature request header string and the signature

        if (request.getHeader("x-line-signature").equals(signature)) {
            log.info("event object: " + o);
        } else {

        }


    }
}
