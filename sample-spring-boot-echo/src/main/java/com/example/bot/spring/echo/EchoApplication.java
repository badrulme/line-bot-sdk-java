/*
 * Copyright 2016 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.example.bot.spring.echo;

import com.linecorp.bot.model.event.message.ContentProvider;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.message.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
@LineMessageHandler
public class EchoApplication {
    private final Logger log = LoggerFactory.getLogger(EchoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EchoApplication.class, args);
    }

    /**
     * Text Message
     *
     * @param event
     * @return Message
     */
    @EventMapping
    public Message handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        log.info("event: " + event);
        final String originalMessageText = event.getMessage().getText();
        return new TextMessage(originalMessageText);
    }

    /**
     * Sticker Message
     *
     * @param event
     * @return Message
     */
    @EventMapping
    public Message handleStickerMessageContent(MessageEvent<StickerMessageContent> event) {
        log.info("event: " + event);
        return new StickerMessage(event.getMessage().getPackageId(), event.getMessage().getStickerId());
    }

    /**
     * Image Message
     *
     * @param event
     * @return Message
     */
    @EventMapping
    public Message handleImageMessageContent(MessageEvent<ImageMessageContent> event) throws URISyntaxException {
        log.info("Image Message event: " + event);
        log.info("Image URL: " + event);
        log.info("Preview URL: " + event);
        URI uri = new URI("https://i2.wp.com/chefsavvy.com/wp-content/uploads/Easy-Beef-Fried-Rice.jpg?w=665&ssl=1");
        return new ImageMessage(uri,uri);
    }


    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event-all: " + event);
    }
}
