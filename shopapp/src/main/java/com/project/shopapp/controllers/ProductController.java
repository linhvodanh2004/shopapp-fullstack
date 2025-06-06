package com.project.shopapp.controllers;

import com.github.javafaker.Faker;
import com.project.shopapp.components.LocalizationUtils;
import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.mappers.ProductMapper;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.responses.ApiResponse;
import com.project.shopapp.responses.ProductImageResponse;
import com.project.shopapp.responses.ProductListResponse;
import com.project.shopapp.responses.ProductResponse;
import com.project.shopapp.services.ICategoryService;
import com.project.shopapp.services.IProductService;
import com.project.shopapp.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;
    private final ICategoryService categoryService;
    private final LocalizationUtils localizationUtils;
    private final ProductMapper productMapper;

    @GetMapping("")
    public ApiResponse<?> getAllProducts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "12") int limit,
            @RequestParam(name = "category_id", defaultValue = "0") Long categoryId,
            @RequestParam(name = "keyword", defaultValue = "") String keyWord
    ) {
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by("id").ascending());
        Page<Product> productPage = productService.getAllProducts(keyWord, categoryId, pageRequest);
        List<ProductResponse> productResponses = productPage.stream().map(
                productMapper::toProductResponse
        ).toList();
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(productResponses)
                .message("OK")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getProductById(@PathVariable("id") String productId) {
        try {
            Product product = productService.getProductById(Long.parseLong(productId));
            ProductResponse productResponse = productMapper.toProductResponse(product);
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("OK")
                    .data(productResponse)
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .message("BAD REQUEST")
                    .errors(List.of(e.getMessage()))
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<?> insertProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errors);
            }
            Product newProduct = productService.createProduct(productDTO);
            return ResponseEntity.status(HttpStatus.OK).body(newProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @PostMapping(value = "/uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> uploadImages(
//            @PathVariable("id") Long productId,
//            @RequestParam("files") List<MultipartFile> files) {
//        try {
//            Product newProduct = productService.getProductById(productId);
//            files = files == null ? new ArrayList<MultipartFile>() : files;
//            if (files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
//                return ResponseEntity
//                        .status(HttpStatus.BAD_REQUEST)
//                        .body(ProductImageResponse.builder()
//                                .message(localizationUtils
//                                        .getLocalizedMessage(MessageKeys.INVALID_IMAGE_FILE)).build());
//            }
//            List<ProductImage> productImages = new ArrayList<>();
//            for (MultipartFile file : files) {
//                if (file.getSize() == 0) {
//                    continue;
//                }
//                if (file.getSize() > 10 * 1024 * 1024) {
//                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(
//                            ProductImageResponse.builder()
//                                    .message(localizationUtils
//                                            .getLocalizedMessage(MessageKeys.TOO_LARGE_FILE, "10MB"))
//                    );
//                }
//                String contentType = file.getContentType();
//                if (contentType == null || !contentType.startsWith("image/")) {
//                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
//                            .body(ProductImageResponse.builder()
//                                    .message(localizationUtils
//                                            .getLocalizedMessage(MessageKeys.INVALID_IMAGE_FILE))
//                                    .build());
//                }
//                String fileName = storeFile(file);
//                ProductImage productImage = productService.createProductImage(newProduct.getId(),
//                        ProductImageDTO
//                                .builder()
//                                .imageUrl(fileName)
//                                .build());
//                productImages.add(productImage);
//            }
//            return ResponseEntity.ok(
//                    productImages.stream().map(productImage
//                            -> ProductImageResponse.builder()
//                            .imageUrl(productImage.getImageUrl())
//                            .message(localizationUtils.getLocalizedMessage(MessageKeys.STORE_IMAGE_SUCCESSFULLY))
//                            .productId(productId)
//                            .build()
//                    )
//            );
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(ProductImageResponse
//                    .builder()
//                    .message(localizationUtils.getLocalizedMessage(MessageKeys.STORE_IMAGE_FAILED, e.getMessage()))
//                    .build());
//        }
//    }
//
//    private String storeFile(MultipartFile multipartFile) throws IOException {
//        if (!isImage(multipartFile) || multipartFile.getOriginalFilename() == null) {
//            throw new IOException(localizationUtils.getLocalizedMessage(MessageKeys.INVALID_IMAGE_FILE));
//        }
//        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//        String uniqueName = UUID.randomUUID().toString() + "_" + fileName;
//        Path uploadDir = Paths.get("uploads");
//        if (!Files.exists(uploadDir)) {
//            Files.createDirectories(uploadDir);
//        }
//        Path destination = Paths.get(uploadDir.toString(), uniqueName);
//        Files.copy(multipartFile.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
//        return uniqueName;
//    }
//
//    private boolean isImage(MultipartFile file) {
//        String contentType = file.getContentType();
//        return contentType != null && contentType.startsWith("image/");
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateProduct(
//            @PathVariable Long id,
//            @Valid @RequestBody ProductDTO productDTO) {
//        try {
//            Product product = productService.updateProduct(id, productDTO);
//            return ResponseEntity.status(HttpStatus.OK).body(ProductResponse.fromProduct(product));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
//        try {
//            productService.deleteProduct(id);
//            return ResponseEntity.ok("Delete successfully product: " + id);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @PostMapping("/generateFakeProducts")
//    private ResponseEntity<?> generateFakeProducts() {
//        Faker faker = new Faker();
//        for (int i = 0; i < 20; i++) {
//            String productName = faker.commerce().productName();
//            if (productService.existsByName(productName)) {
//                continue;
//            }
//            ProductDTO productDTO = ProductDTO.builder()
//                    .name(productName)
//                    .price((float) faker.number().numberBetween(100, 999999))
//                    .description(faker.lorem().sentence())
//                    .thumbnail("")
//                    .categoryId(1L)
//                    .build();
//            try {
//                productService.createProduct(productDTO);
//            } catch (Exception e) {
//                return ResponseEntity.badRequest().body(e.getMessage());
//            }
//        }
//        return ResponseEntity.ok("Done");
//    }
//
//    @GetMapping("/images/{imageName}")
//    public ResponseEntity<?> getProductImage(@PathVariable("imageName") String imageName) {
//        try {
//            Path uploadDir = Paths.get("uploads/" + imageName);
//            UrlResource resource = new UrlResource(uploadDir.toUri());
//            if (resource.exists()) {
//                return ResponseEntity.ok()
//                        .contentType(MediaType.IMAGE_JPEG)
//                        .body(resource);
//            } else {
//                return ResponseEntity.badRequest().contentType(MediaType.IMAGE_JPEG)
//                        .body(new UrlResource(Paths.get("uploads/404notfound.jpg").toUri()));
//            }
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @GetMapping("/{id}/images")
//    public ResponseEntity<?> getProductImagesByProductId(@PathVariable("id") String productId) {
//        try {
//            List<ProductImage> productImages = productService.getProductImagesByProductId(Long.parseLong(productId));
//            if(productImages.isEmpty()){
//                return ResponseEntity.badRequest()
//                        .body("404notfound.jpg");
//            }
//            List<ProductImageResponse> productImageResponses = productImages.stream().map(productImage
//                    -> ProductImageResponse.builder()
//                    .imageUrl(productImage.getImageUrl())
//                    .productId(Long.parseLong(productId))
//                    .build()).toList();
//            return ResponseEntity.ok().body(productImageResponses);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(ProductImageResponse
//                    .builder()
//                    .message(localizationUtils.getLocalizedMessage(MessageKeys.STORE_IMAGE_FAILED, e.getMessage()))
//                    .build());
//        }
//    }
}
