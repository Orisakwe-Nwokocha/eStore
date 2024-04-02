package africa.Semicolon.eStore.data.repositories;

import africa.Semicolon.eStore.data.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Orders extends MongoRepository<Order, String> {
}
