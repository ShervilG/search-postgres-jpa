package com.example.productsearch.controller;

import com.example.productsearch.dto.ProductSearchResponse;
import com.example.productsearch.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping("/search")
  public ResponseEntity<ProductSearchResponse> searchProducts(@RequestParam final Map<String, String> params)
      throws UnsupportedEncodingException {
    return new ResponseEntity<>(productService.getSearchedProducts(params), HttpStatus.OK);
  }
}
