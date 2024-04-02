package africa.Semicolon.eStore.data.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("Users")
public class User {
    @Id
    private String id;
    private String name;
    private String emailAddress;
    private String phoneNumber;
    private Address homeAddress;
    @NotNull(message = "Please enter a username")
    private String username;
    @NotNull(message = "Please enter a password")
    private String password;
    @NotNull(message = "Please select a role")
    private Role role;
    private ShoppingCart cart;
    private List<Order> orders = new ArrayList<>();
}
