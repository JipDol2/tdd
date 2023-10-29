package hello.tdd.product;

import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
class ProductService {

    private final ProductPort productPort;
    private long productId;
    private UpdateProductRequest request;

    ProductService(ProductPort productPort) {
        this.productPort = productPort;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Void> addProduct(@RequestBody AddProductRequest request) {
        Product product = new Product(request.name(), request.price(), request.discountPolicy());

        productPort.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Void> test(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{productId}")
    public GetProductResponse getProduct(@PathVariable final Long productId) {
        final Product product = productPort.getProduct(productId);

        return new GetProductResponse(product.getId(),product.getName(),product.getPrice(),product.getDiscountPolicy());
    }

    public void updateProduct(long productId, UpdateProductRequest request) {
        Product product = productPort.getProduct(productId);
        product.update(request.name(),request.price(),request.discountPolicy());
        productPort.save(product);
    }

    @PatchMapping("/{productId}")
    @Transactional
    public ResponseEntity<Void> updateProduct(
            @PathVariable final Long productId,
            @RequestBody final UpdateProductRequest request
            ){
        Product product = productPort.getProduct(productId);

        product.update(request.name(),request.price(),request.discountPolicy());

        productPort.save(product);
        return ResponseEntity.ok().build();
    }
}
