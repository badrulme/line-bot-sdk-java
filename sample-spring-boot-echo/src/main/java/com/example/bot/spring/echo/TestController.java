package com.example.bot.spring.echo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {
    @Autowired
    private final HttpServletRequest request;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public TestController(HttpServletRequest request) {
        this.request = request;
    }

    @PostMapping("test")
    public void test(@RequestBody Object o) {
        log.info("event object: " + o);
        log.info("request: " + request.getHeaderNames());
    }
}
