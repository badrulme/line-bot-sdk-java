package com.example.bot.spring.echo;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    public void test() throws InvalidKeyException, NoSuchAlgorithmException, IOException {

        final String signatureHeader = request.getHeader("X-Line-Signature");
        final byte[] payload = StreamUtils.copyToByteArray(request.getInputStream());


        log.info("signatureHeader: " + signatureHeader);
        log.info("payload: " + payload);


        log.info("request: " + request.getHeader("X-Line-Signature"));

        String channelSecret = "3c5542ac2dc6fa1cc6f131f984bc3622"; // Channel secret string
        String httpRequestBody = payload.toString(); // Request body string
        SecretKeySpec key = new SecretKeySpec(channelSecret.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);
        byte[] source = httpRequestBody.getBytes("UTF-8");
        String signature = Base64.encodeBase64String(mac.doFinal(source));
// Compare x-line-signature request header string and the signature

        if (request.getHeader("X-Line-Signature").equals(signature)) {
            log.info("matches");
        } else {
            log.info("does not matches");
        }


    }
}
