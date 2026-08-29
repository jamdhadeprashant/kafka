package com.apps.ws.products.service;

import com.apps.ws.core.ProductCreatedEvent;
import com.apps.ws.products.model.CreateProductRestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ProductServiceImpl implements ProductService {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    KafkaTemplate<String,ProductCreatedEvent> kafkaTemplate;

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductRestModel productRestModel) throws Exception {
        String productId = UUID.randomUUID().toString();
        //TODO: persist product details into database table before publish event
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId, productRestModel.getTitle(), productRestModel.getPrice(), productRestModel.getQuantity());
        logger.info("********** Before publishing a productCreatedEvent");

        SendResult<String,ProductCreatedEvent> result= kafkaTemplate.send("product-created-event-topic",productId,productCreatedEvent).get();

        logger.info("Partition:"+result.getRecordMetadata().partition());
        logger.info("Topic:"+result.getRecordMetadata().topic());
        logger.info("offset:"+result.getRecordMetadata().offset());

        logger.info("********** returning product id");
        return productId;
    }
}
