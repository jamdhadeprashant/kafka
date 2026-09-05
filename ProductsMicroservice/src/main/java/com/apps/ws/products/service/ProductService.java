package com.apps.ws.products.service;

import com.apps.ws.products.rest.CreateProductRestModel;

public interface ProductService {
	
	String createProduct(CreateProductRestModel productRestModel) throws Exception ;

}
