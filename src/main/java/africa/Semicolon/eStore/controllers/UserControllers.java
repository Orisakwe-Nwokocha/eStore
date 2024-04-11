package africa.Semicolon.eStore.controllers;

import africa.Semicolon.eStore.dto.requests.*;
import africa.Semicolon.eStore.dto.responses.*;
import africa.Semicolon.eStore.exceptions.EstoreAppException;
import africa.Semicolon.eStore.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/user")
public class UserControllers {
    @Autowired
    private UserServices userServices;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest, Errors errors) {
        if (errors.hasErrors()) return getValidationErrorMessageOf(errors);
        try {
            var result = userServices.register(registerRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (EstoreAppException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, Errors errors) {
        if (errors.hasErrors()) return getValidationErrorMessageOf(errors);
        try {
            var result = userServices.login(loginRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), OK);
        } catch (EstoreAppException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody LogoutRequest logoutRequest, Errors errors) {
        if (errors.hasErrors()) return getValidationErrorMessageOf(errors);
        try {
            var result = userServices.logout(logoutRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), OK);
        } catch (EstoreAppException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/add-product")
    public ResponseEntity<?> addProduct(@Valid @RequestBody AddProductRequest addProductRequest, Errors errors) {
        if (errors.hasErrors()) return getValidationErrorMessageOf(errors);
        try {
            var result = userServices.addProduct(addProductRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (EstoreAppException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@Valid @RequestBody AddItemRequest addItemRequest, Errors errors) {
        if (errors.hasErrors()) return getValidationErrorMessageOf(errors);
        try {
            var result = userServices.addToCart(addItemRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), OK);
        } catch (EstoreAppException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/remove-from-cart")
    public ResponseEntity<?> removeFromCart(@Valid @RequestBody RemoveItemRequest removeItemRequest, Errors errors) {
        if (errors.hasErrors()) return getValidationErrorMessageOf(errors);
        try {
            var result = userServices.removeFromCart(removeItemRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), OK);
        } catch (EstoreAppException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/view-cart")
    public ResponseEntity<?> viewCart(@Valid @RequestBody ViewCartRequest viewCartRequest, Errors errors) {
        if (errors.hasErrors()) return getValidationErrorMessageOf(errors);
        try {
            var result = userServices.viewCart(viewCartRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), OK);
        } catch (EstoreAppException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), NO_CONTENT);
        }
    }

    @PatchMapping("/update-delivery-details")
    public ResponseEntity<?> updateDeliveryDetails(@Valid @RequestBody UpdateDeliveryDetailsRequest updateDeliveryDetailsRequest, Errors errors) {
        if (errors.hasErrors()) return getValidationErrorMessageOf(errors);
        try {
            var result = userServices.updateDeliveryDetails(updateDeliveryDetailsRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), OK);
        } catch (EstoreAppException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/update-credit-card-info")
    public ResponseEntity<?> updateCreditCardInfo(@Valid @RequestBody UpdateCreditCardInfoRequest updateCreditCardInfoRequest, Errors errors) {
        if (errors.hasErrors()) return getValidationErrorMessageOf(errors);
        try {
            var result = userServices.updateCreditCardInfo(updateCreditCardInfoRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), OK);
        } catch (EstoreAppException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@Valid @RequestBody CheckoutRequest checkoutRequest, Errors errors) {
        if (errors.hasErrors()) return getValidationErrorMessageOf(errors);
        try {
            var result = userServices.checkout(checkoutRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), OK);
        } catch (EstoreAppException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/view-order")
    public ResponseEntity<?> viewOrder(@Valid @RequestBody ViewOrderRequest viewOrderRequest, Errors errors) {
        if (errors.hasErrors()) return getValidationErrorMessageOf(errors);
        try {
            var result = userServices.viewOrder(viewOrderRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), OK);
        } catch (EstoreAppException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/view-all-orders")
    public ResponseEntity<?> viewAllOrders(@Valid @RequestBody ViewAllOrdersRequest viewAllOrdersRequest, Errors errors) {
        if (errors.hasErrors()) return getValidationErrorMessageOf(errors);
        try {
            var result = userServices.viewAllOrders(viewAllOrdersRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), OK);
        } catch (EstoreAppException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    private static ResponseEntity<String> getValidationErrorMessageOf(Errors errors) {
        return new ResponseEntity<>(String.format("Operation failed: %s is null",
                requireNonNull(errors.getFieldError()).getField()), BAD_REQUEST);
    }
}
