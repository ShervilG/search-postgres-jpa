package com.example.productsearch.dto;

import com.example.productsearch.entity.Product;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
public class ProductSearchResponse {

  private List<Product> result;
  private int totalPages;
  private int currentPage;
}
