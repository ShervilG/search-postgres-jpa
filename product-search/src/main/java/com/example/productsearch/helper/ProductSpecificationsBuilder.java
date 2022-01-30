package com.example.productsearch.helper;

import com.example.productsearch.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class ProductSpecification implements Specification<Product> {

  private final String searchFilter;

  public ProductSpecification(String searchFilter) {
    this.searchFilter = searchFilter;
  }

  @Override
  public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    String[] criteria = this.searchFilter.split(" ");

    if (criteria[1].equalsIgnoreCase(">")) {
      return builder.greaterThanOrEqualTo(root.get(criteria[0]), criteria[2]);
    } else if (criteria[1].equalsIgnoreCase("<")) {
      return builder.lessThanOrEqualTo(
          root.get(criteria[0]), criteria[2]);
    } else if (criteria[1].equalsIgnoreCase(":")) {
      if (root.get(criteria[0]).getJavaType() == String.class) {
        return builder.like(
            root.get(criteria[0]), "%" + criteria[2] + "%");
      } else {
        return builder.equal(root.get(criteria[0]), criteria[2]);
      }
    } else if (criteria[1].equalsIgnoreCase("=")) {
      return builder.equal(root.get(criteria[0]), criteria[2]);
    }
    return null;
  }
}

@Component
public class ProductSpecificationsBuilder {

  public Specification<Product> getProductSpecification(String searchQuery) throws UnsupportedEncodingException {
    String decodedQuery = URLDecoder.decode(searchQuery, "UTF-8" );
    String[] filters = decodedQuery.split(",");

    List<Specification<Product>> specList = Arrays.stream(filters).map(ProductSpecification::new)
        .collect(Collectors.toList());

    Specification<Product> result = null;
    if (specList.size() != 0) {
      result = specList.get(0);
      for (int i = 1;i < specList.size();i++) {
        result = Specification.where(result).and(specList.get(i));
      }
    }

    return result;
  }
}
