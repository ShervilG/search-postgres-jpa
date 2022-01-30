package com.example.productsearch.service;

import com.example.productsearch.dto.ProductSearchResponse;
import com.example.productsearch.entity.Product;
import com.example.productsearch.helper.ProductSpecificationsBuilder;
import com.example.productsearch.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ProductSpecificationsBuilder productSpecificationsBuilder;

  public ProductSearchResponse getSearchedProducts(Map<String, String> searchParams)
      throws UnsupportedEncodingException {
    ProductSearchResponse productSearchResponse = new ProductSearchResponse();
    Pageable page = PageRequest.of(searchParams.get("page") != null ? Integer.parseInt(searchParams.get("page")) :
        0, searchParams.get("offset") != null ? Math.min(Integer.parseInt(searchParams.get("offset")), 2) : 2
    );

    Page<Product> productsPage;
    if (searchParams.get("query") == null) {
      productsPage = productRepository.findAll(page);
    } else {
      Specification<Product> specification = productSpecificationsBuilder.getProductSpecification(
          searchParams.get("query")
      );
      productsPage = productRepository.findAll(specification, page);
    }

    productSearchResponse.setResult(productsPage.getContent());
    productSearchResponse.setTotalPages(productsPage.getTotalPages());
    productSearchResponse.setCurrentPage(productsPage.getNumber());

    return productSearchResponse;
  }
}
