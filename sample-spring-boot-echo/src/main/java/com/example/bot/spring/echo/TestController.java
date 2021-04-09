package com.example.bot.spring.echo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.model.event.CallbackRequest;
import com.linecorp.bot.model.objectmapper.ModelObjectMapper;
import lombok.NonNull;
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
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
public class TestController {
    @Autowired
    private final HttpServletRequest request;
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    private final ObjectMapper objectMapper = ModelObjectMapper.createNewObjectMapper();

    public TestController(HttpServletRequest request) {
        this.request = request;
    }

    @PostMapping("test")
    public void test() throws InvalidKeyException, NoSuchAlgorithmException, IOException {
        String channelSecret = "3c5542ac2dc6fa1cc6f131f984bc3622"; // Channel secret string

                final byte[] payload = StreamUtils.copyToByteArray(request.getInputStream());

        String httpRequestBody = new String(payload, StandardCharsets.UTF_8);

        SecretKeySpec key = new SecretKeySpec(channelSecret.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);
        byte[] source = httpRequestBody.getBytes("UTF-8");

        String signature = Base64.encodeBase64String(mac.doFinal(source));

        log.info("signature: "+signature);
        log.info("X-Line-Signature: "+request.getHeader("X-Line-Signature"));

//        final String signatureHeader = request.getHeader("X-Line-Signature");
//        final byte[] payload = StreamUtils.copyToByteArray(request.getInputStream());
//
////        String httpRequestBody = new String(payload, StandardCharsets.UTF_8);
//
////        final byte[] signature = generateSignature(content);
////        final byte[] decodeHeaderSignature = java.util.Base64.getDecoder().decode(headerSignature);
////         MessageDigest.isEqual(decodeHeaderSignature, signature);
//
//
//        log.info("signatureHeader: " + signatureHeader);
//        log.info("payload: " + new String(payload, StandardCharsets.UTF_8));
//
//        final CallbackRequest callbackRequest = objectMapper.readValue(payload, CallbackRequest.class);
//        log.info("callbackRequest: " + callbackRequest);
//        String channelSecret = "3c5542ac2dc6fa1cc6f131f984bc3622"; // Channel secret string
//        String httpRequestBody = new String(payload, StandardCharsets.UTF_8); // Request body string
//        SecretKeySpec key = new SecretKeySpec(channelSecret.getBytes(), "HmacSHA256");
//        Mac mac = Mac.getInstance("HmacSHA256");
//        mac.init(key);
//        byte[] source = httpRequestBody.getBytes("UTF-8");
//        String signature = Base64.encodeBase64String(mac.doFinal(source));
//// Compare x-line-signature request header string and the signature
//        log.info("signature: "+signature);


        if (request.getHeader("X-Line-Signature").equals(signature)) {
            log.info("matches");
        } else {
            log.info("does not matches");
        }


    }

    /**
     * Generate signature value.
     *
     * @param content Body of the http request.
     * @return generated signature value.
     */
//    public byte[] generateSignature(@NonNull byte[] content) {
//        try {
//            final Mac mac = Mac.getInstance(HASH_ALGORITHM);
//            mac.init(secretKeySpec);
//            return mac.doFinal(content);
//        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
//            // "HmacSHA256" is always supported in Java 8 platform.
//            //   (see https://docs.oracle.com/javase/8/docs/api/javax/crypto/Mac.html)
//            // All valid-SecretKeySpec-instance are not InvalidKey.
//            //   (because the key for HmacSHA256 can be of any length. see RFC2104)
//            throw new IllegalStateException(e);
//        }
//    }
}
