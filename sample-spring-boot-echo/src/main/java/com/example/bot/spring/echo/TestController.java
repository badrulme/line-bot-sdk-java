package com.example.bot.spring.echo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @PostMapping("test")
    public void test(){
        log.info("event");
    }
}
