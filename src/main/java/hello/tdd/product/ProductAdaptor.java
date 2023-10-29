package hello.tdd.product;

import org.springframework.stereotype.Component;

@Component
class ProductAdaptor implements ProductPort {

    private final ProductRepository productRepository;

    ProductAdaptor(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product getProduct(long productId){
        return productRepository.findById(productId)
                .orElseThrow(()->new IllegalArgumentException("상품이 존재하지 않습니다."));
    }
}
