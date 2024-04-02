package africa.Semicolon.eStore.data.repositories;

import africa.Semicolon.eStore.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Users extends MongoRepository<User, String> {
    boolean existsByUsername(String username);

    User findByUsername(String username);
}
