package com.project.pixelstore.controllers;

import com.github.javafaker.Faker;
import com.project.pixelstore.components.LocalizationUtils;
import com.project.pixelstore.dtos.ProductDTO;
import com.project.pixelstore.dtos.ProductImageDTO;
import com.project.pixelstore.mappers.ProductImageMapper;
import com.project.pixelstore.mappers.ProductMapper;
import com.project.pixelstore.models.Product;
import com.project.pixelstore.models.ProductImage;
import com.project.pixelstore.responses.ApiResponse;
import com.project.pixelstore.responses.ProductImageResponse;
import com.project.pixelstore.responses.ProductResponse;
import com.project.pixelstore.services.CategoryService;
import com.project.pixelstore.services.ProductService;
import com.project.pixelstore.utils.MessageKeys;
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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final LocalizationUtils localizationUtils;
    private final ProductMapper productMapper;
    private final ProductImageMapper productImageMapper;

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
        return ApiResponse.ok(productResponses);
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getProductById(@PathVariable("id") String productId) {
        try {
            Product product = productService.getProductById(Long.parseLong(productId));
            ProductResponse productResponse = productMapper.toProductResponse(product);
            return ApiResponse.ok(productResponse);
        } catch (Exception e) {
            return ApiResponse.builder()
                    .message("BAD REQUEST")
                    .errors(List.of(e.getMessage()))
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
    }

    @PostMapping(value = "")
    public ApiResponse<?> insertProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ApiResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .errors(errors)
                        .message("BAD REQUEST")
                        .build();
            }
            Product newProduct = productService.createProduct(productDTO);
            return ApiResponse.ok(productMapper.toProductResponse(newProduct));
        } catch (Exception e) {
            return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errors(List.of(e.getMessage()))
                    .message("BAD REQUEST")
                    .build();
        }
    }

    @PostMapping(value = "/uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> uploadImages(
            @PathVariable("id") Long productId,
            @RequestParam("files") List<MultipartFile> files) {
        try {
            Product newProduct = productService.getProductById(productId);
            files = (files == null) ? new ArrayList<MultipartFile>() : files;
            if (files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
                return ApiResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("BAD REQUEST")
                        .errors(List.of(
                                localizationUtils.getLocalizedMessage(MessageKeys.INVALID_IMAGE_FILE)
                        ))
                        .build();
            }
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file.getSize() == 0) {
                    continue;
                }
                if (file.getSize() > 10 * 1024 * 1024) {
                    return ApiResponse.builder()
                            .status(HttpStatus.PAYLOAD_TOO_LARGE.value())
                            .message("PAYLOAD TOO LARGE")
                            .errors(List.of(
                                    localizationUtils.getLocalizedMessage(MessageKeys.TOO_LARGE_FILE, "10MB")
                            ))
                            .build();
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ApiResponse.builder()
                            .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                            .message("UNSUPPORTED MEDIA TYPE")
                            .errors(List.of(
                                    localizationUtils.getLocalizedMessage(MessageKeys.INVALID_IMAGE_FILE)
                            ))
                            .build();
                }
                String fileName = storeFile(file);
                ProductImage productImage = productService.createProductImage(newProduct.getId(),
                        ProductImageDTO
                                .builder()
                                .imageUrl(fileName)
                                .build());
                productImages.add(productImage);
            }
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message(localizationUtils.getLocalizedMessage(MessageKeys.STORE_IMAGE_SUCCESSFULLY))
                    .data(productImages
                            .stream()
                            .map(productImageMapper::toProductImageResponse)
                            .toList())
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("BAD REQUEST")
                    .errors(List.of(
                            localizationUtils.getLocalizedMessage(MessageKeys.STORE_IMAGE_FAILED, e.getMessage()))
                    )
                    .build();
        }
    }

    private String storeFile(MultipartFile multipartFile) throws IOException {
        if (!isImage(multipartFile) || multipartFile.getOriginalFilename() == null) {
            throw new IOException(localizationUtils.getLocalizedMessage(MessageKeys.INVALID_IMAGE_FILE));
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String uniqueName = UUID.randomUUID().toString() + "_" + fileName;
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        Path destination = Paths.get(uploadDir.toString(), uniqueName);
        Files.copy(multipartFile.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueName;
    }

    private boolean isImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    @PutMapping("/{id}")
    public ApiResponse<?> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDTO productDTO) {
        try {
            Product product = productService.updateProduct(id, productDTO);
            return ApiResponse.ok(productMapper.toProductResponse(product));
        } catch (Exception e) {
            return ApiResponse.builder()
                    .errors(List.of(e.getMessage()))
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("BAD REQUEST")
                    .build();
        }
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
//        try {
//            productService.deleteProduct(id);
//            return ResponseEntity.ok("Delete successfully product: " + id);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @PostMapping("/generateFakeProducts")
    private ResponseEntity<?> generateFakeProducts() {
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
            String productName = faker.commerce().productName();
            if (productService.existsByName(productName)) {
                continue;
            }
            ProductDTO productDTO = ProductDTO.builder()
                    .name(productName)
                    .price((float) faker.number().numberBetween(100, 999999))
                    .description(faker.lorem().sentence())
                    .thumbnail("")
                    .categoryId(1L)
                    .build();
            try {
                productService.createProduct(productDTO);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.ok("Done");
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> getProductImage(@PathVariable("imageName") String imageName) {
        try {
            Path uploadDir = Paths.get("uploads/" + imageName);
            UrlResource resource = new UrlResource(uploadDir.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.badRequest().contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/404notfound.jpg").toUri()));
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/images")
    public ApiResponse<?> getProductImagesByProductId(@PathVariable("id") String productId) {
        try {
            List<ProductImage> productImages = productService.getProductImagesByProductId(Long.parseLong(productId));
            if (productImages.isEmpty()) {
                return ApiResponse.builder()
                        .message("NOT FOUND")
                        .data("404notfound.jpg")
                        .status(HttpStatus.NOT_FOUND.value())
                        .build();
            }
            List<ProductImageResponse> productImageResponses =
                    productImages.stream().map(productImageMapper::toProductImageResponse).toList();
            return ApiResponse.ok(productImageResponses);
        } catch (Exception e) {
            return ApiResponse.builder()
                    .message("NOT FOUND")
                    .data("404notfound.jpg")
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
        }
    }

    @GetMapping("/by-ids")
    public ApiResponse<?> getProductsByIds(@RequestParam("ids") String params){
        List<String> ids = Arrays.stream(params.split(",")).toList();
        List<Product> products = productService.getProductsByIds(ids);
        return ApiResponse.ok(products.stream().map(
                productMapper::toProductResponse
        ));
    }
}
