package com.project.pixelstore.services;


import com.project.pixelstore.dtos.ProductDTO;
import com.project.pixelstore.dtos.ProductImageDTO;
import com.project.pixelstore.exceptions.DataNotFoundException;
import com.project.pixelstore.models.Product;
import com.project.pixelstore.models.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProductService {
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException;
    public Product getProductById(Long id) throws DataNotFoundException;
//    public void deleteProduct(Long id);
    public Product updateProduct(Long productId, ProductDTO productDTO) throws DataNotFoundException;

    public Page<Product> getAllProducts(String keyWord, Long categoryId, PageRequest pageRequest);

    public boolean existsByName(String name);
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception;
    public List<ProductImage> getProductImagesByProductId(Long productId);
    public List<Product> getProductsByIds(List<String> ids);
}
