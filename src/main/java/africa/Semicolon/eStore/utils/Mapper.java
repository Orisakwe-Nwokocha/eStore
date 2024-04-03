package africa.Semicolon.eStore.utils;

import africa.Semicolon.eStore.data.models.Product;
import africa.Semicolon.eStore.data.models.ProductCategory;
import africa.Semicolon.eStore.data.models.Role;
import africa.Semicolon.eStore.data.models.User;
import africa.Semicolon.eStore.dtos.requests.AddProductRequest;
import africa.Semicolon.eStore.dtos.requests.RegisterRequest;
import africa.Semicolon.eStore.dtos.responses.*;
import africa.Semicolon.eStore.exceptions.InvalidArgumentException;

import java.util.List;

import static africa.Semicolon.eStore.utils.Cleaner.lowerCaseValueOf;
import static africa.Semicolon.eStore.utils.Cleaner.upperCaseValueOf;
import static africa.Semicolon.eStore.utils.Cryptography.encode;

public final class Mapper {
    public static User map(RegisterRequest registerRequest) {
        String username = lowerCaseValueOf(registerRequest.getUsername());
        String password = encode(registerRequest.getPassword());
        String role = upperCaseValueOf(registerRequest.getRole());
        User user = new User();
        try {
            user.setRole(Role.valueOf(role));
        }
        catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Invalid role: " + role);
        }
        user.setUsername(username);
        user.setPassword(password);
        user.setName(registerRequest.getName());
        user.setEmailAddress(registerRequest.getEmailAddress());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        return user;
    }
    public static RegisterResponse mapRegisterResponseWith(User user) {
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setId(user.getId());
        registerResponse.setUsername(user.getUsername());
        return registerResponse;
    }

    public static LoginResponse mapLoginResponseWith(User user) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setId(user.getId());
        loginResponse.setUsername(user.getUsername());
        loginResponse.setLoggedIn(true);
        return loginResponse;
    }

    public static LogoutResponse mapLogoutResponseWith(User user) {
        LogoutResponse logoutResponse = new LogoutResponse();
        logoutResponse.setId(user.getId());
        logoutResponse.setUsername(user.getUsername());
        logoutResponse.setLoggedIn(false);
        return logoutResponse;
    }

    public static Product mapAddItemResponse(AddProductRequest addProductRequest) {
        String productCategory = upperCaseValueOf(addProductRequest.getCategory());
        Product product = new Product();
        try {
            product.setCategory(ProductCategory.valueOf(productCategory));
        }
        catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Category is not valid");
        }
        product.setName(addProductRequest.getName());
        product.setDescription(addProductRequest.getDescription());
        product.setPrice(addProductRequest.getPrice());
        product.setQuantity(addProductRequest.getQuantity());
        return product;
    }

    public static AddProductResponse mapAddProductResponseWith(Product product) {
        AddProductResponse addProductResponse = new AddProductResponse();
        addProductResponse.setProductId(product.getId());
        addProductResponse.setProductName(product.getName());
        addProductResponse.setCategory(product.getCategory().toString());
        addProductResponse.setPrice(product.getPrice());
        addProductResponse.setQuantity(product.getQuantity());
        addProductResponse.setDescription(product.getDescription());
        return addProductResponse;
    }

    public static GetProductsResponse mapGetProductsResponse(List<Product> allProducts) {
        GetProductsResponse getProductsResponse = new GetProductsResponse();
        getProductsResponse.setProducts(allProducts.toString());
        return getProductsResponse;
    }

    public static FindProductResponse mapFindProductResponse(Product foundProduct) {
        FindProductResponse findProductResponse = new FindProductResponse();
        findProductResponse.setProduct(foundProduct.getDescription());
        return findProductResponse;
    }

    public static AddItemResponse mapAddItemResponse(User user) {
        AddItemResponse addItemResponse = new AddItemResponse();
        addItemResponse.setUsername(user.getUsername());
        addItemResponse.setShoppingCart(user.getCart().toString());
        return addItemResponse;
    }

    public static RemoveItemResponse mapRemoveItemResponse(User user) {
        RemoveItemResponse removeItemResponse = new RemoveItemResponse();
        removeItemResponse.setUsername(user.getUsername());
        removeItemResponse.setShoppingCart(user.getCart().toString());
        return removeItemResponse;
    }

    public static ViewCartResponse mapViewCartResponse(User user) {
        ViewCartResponse viewCartResponse = new ViewCartResponse();
        viewCartResponse.setUsername(user.getUsername());
        viewCartResponse.setShoppingCart(user.getCart().toString());
        return viewCartResponse;
    }
}
