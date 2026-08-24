package com.apps.ws.products.service;

import com.apps.ws.products.model.CreateProductRestModel;

import java.util.concurrent.ExecutionException;

public interface ProductService {
    String createProduct(CreateProductRestModel productRestModel) throws Exception;
}
