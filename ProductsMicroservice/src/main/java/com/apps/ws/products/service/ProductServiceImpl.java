package com.apps.ws.products.service;

import com.apps.ws.products.event.ProductCreatedEvent;
import com.apps.ws.products.model.CreateProductRestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements ProductService {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    KafkaTemplate<String,ProductCreatedEvent> kafkaTemplate;

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductRestModel productRestModel) {
        String productId = UUID.randomUUID().toString();
        //TODO: persist product details into database table before publish event
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId, productRestModel.getTitle(), productRestModel.getPrice(), productRestModel.getQuantity());
        CompletableFuture<SendResult<String,ProductCreatedEvent>> future= kafkaTemplate.send("product-created-event-topic",productId,productCreatedEvent);
        future.whenComplete((result,exception)->{
           if(exception!=null){
               logger.error("********** Failed to send message:"+exception.getMessage());
           }else {
               logger.info("********** Message sent successfullys"+result.getRecordMetadata());
           }
        });
        logger.info("********** returning product id");
        return productId;
    }
}
