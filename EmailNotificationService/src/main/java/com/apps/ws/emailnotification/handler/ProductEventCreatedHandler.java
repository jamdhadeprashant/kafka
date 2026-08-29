package com.apps.ws.emailnotification.handler;

import com.apps.ws.core.ProductCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "product-created-event-topic")
public class ProductEventCreatedHandler {

    private final Logger logger= LoggerFactory.getLogger(ProductEventCreatedHandler.class);

    @KafkaHandler
    public void handle(ProductCreatedEvent productCreatedEvent){
        logger.info("Recieved a new event:{}",productCreatedEvent.getTitle());
    }
}
