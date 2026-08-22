package com.apps.ws.products.service;

import com.apps.ws.products.model.CreateProductRestModel;

public interface ProductService {
    String createProduct(CreateProductRestModel productRestModel);
}
