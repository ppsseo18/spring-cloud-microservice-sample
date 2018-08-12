package com.tiple3e.productservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    WebClient.Builder webClientBuilder;

    @GetMapping("/user")
    public Mono<String> getUserTest() {
        return webClientBuilder.build().get().uri("http://user-service/")
                .retrieve()
                .bodyToMono(String.class);
    }

    @GetMapping("/products")
    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Mono<ResponseEntity<Product>> getProductById(@PathVariable(value = "id") String id) {
        return productRepository.findById(id)
                .map(product -> ResponseEntity.ok(product))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/products")
    public Mono<Product> createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/products/{id}")
    public Mono<ResponseEntity<Void>> updateProduct(@PathVariable(value = "id") String id, @RequestBody Product requestProduct) {
        return productRepository.findById(id)
                .flatMap(product -> {
                    product.setName(requestProduct.getName());
                    product.setPrice(requestProduct.getPrice());
                    product.setQuantity(requestProduct.getQuantity());
                    return productRepository.save(product);
                }).map(updatedProduct -> new ResponseEntity<Void>(HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/products/{id}/quantity")
    public Mono<ResponseEntity<Void>> updateProductQuantity(@PathVariable(value = "id") String id, @RequestBody Map<String, Integer> requestBody) {
        return productRepository.findById(id)
                .flatMap(product -> {
                    product.setQuantity(product.getQuantity() + requestBody.get("quantity"));
                    return productRepository.save(product);
                }).map(updatedProduct -> new ResponseEntity<Void>(HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/products/{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable(value = "id") String id) {
        return productRepository.findById(id)
                .flatMap(product ->
                        productRepository.delete(product).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/products/{id}/quantity")
    public Mono<ResponseEntity<Void>> deleteProductQuantity(@PathVariable(value = "id") String id, @RequestBody Map<String, Integer> requestBody) {
        return productRepository.findById(id)
                .flatMap(product -> {
                    product.setQuantity(product.getQuantity() - requestBody.get("quantity"));
                    return productRepository.save(product);
                }).map(updatedProduct -> new ResponseEntity<Void>(HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
