package africa.Semicolon.eStore.data.repositories;

import africa.Semicolon.eStore.data.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Inventory extends MongoRepository<Product, String> {
    Product findByName(String productName);
}
