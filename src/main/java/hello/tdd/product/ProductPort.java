package hello.tdd.product;

interface ProductPort {

    void save(Product product);

    Product getProduct(long productId);
}
