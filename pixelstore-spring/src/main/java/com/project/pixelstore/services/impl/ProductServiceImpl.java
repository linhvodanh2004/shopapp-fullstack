package com.project.pixelstore.services.impl;

import com.project.pixelstore.dtos.ProductDTO;
import com.project.pixelstore.dtos.ProductImageDTO;
import com.project.pixelstore.exceptions.DataNotFoundException;
import com.project.pixelstore.mappers.ProductImageMapper;
import com.project.pixelstore.mappers.ProductMapper;
import com.project.pixelstore.models.Category;
import com.project.pixelstore.models.Product;
import com.project.pixelstore.models.ProductImage;
import com.project.pixelstore.repositories.CategoryRepository;
import com.project.pixelstore.repositories.ProductImageRepository;
import com.project.pixelstore.repositories.ProductRepository;
import com.project.pixelstore.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductMapper productMapper;
    private final ProductImageMapper productImageMapper;

    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category not found"));

        Product product = productMapper.toProduct(productDTO);
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) throws DataNotFoundException {

        return productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));

    }

//    @Override
//    @Transactional
//    public void deleteProduct(Long id) {
//        Optional<Product> product = productRepository.findById(id);
//        product.ifPresent(productRepository::delete);
//    }

    @Override
    @Transactional
    public Product updateProduct(Long productId, ProductDTO productDTO) throws DataNotFoundException {

        Product existingProduct = getProductById(productId);
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category not found"));

        productMapper.updateProduct(existingProduct, productDTO);
        existingProduct.setCategory(category);

        return productRepository.save(existingProduct);
    }

    @Override
    public Page<Product> getAllProducts(String keyWord, Long categoryId, PageRequest pageRequest) {
        return productRepository.searchProduct(keyWord, categoryId, pageRequest);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        ProductImage productImage = productImageMapper.toProductImage(productImageDTO);
        productImage.setProduct(product);
        // Never insert more than 5 images in one product
        int size = productImageRepository.findByProductId(productId).size();
        if (size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException("Product already has "
                    + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT + " images");
        }
        return productImageRepository.save(productImage);
    }

    @Override
    public List<ProductImage> getProductImagesByProductId(Long productId) {
        return productImageRepository.findByProductId(productId);
    }

    @Override
    public List<Product> getProductsByIds(List<String> ids) {
        return productRepository.getProductsByIds(ids);
    }

}
