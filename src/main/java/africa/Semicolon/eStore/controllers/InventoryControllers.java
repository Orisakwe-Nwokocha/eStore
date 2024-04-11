package africa.Semicolon.eStore.controllers;

import africa.Semicolon.eStore.dto.requests.FindProductRequest;
import africa.Semicolon.eStore.dto.responses.ApiResponse;
import africa.Semicolon.eStore.exceptions.EstoreAppException;
import africa.Semicolon.eStore.services.InventoryServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/user")
public class InventoryControllers {
    @Autowired
    private InventoryServices inventoryServices;

    @GetMapping("/find-product")
    public ResponseEntity<?> findProductWith(@Valid @RequestBody FindProductRequest findProductRequest, Errors errors) {
        if (errors.hasErrors()) return getValidationErrorMessageOf(errors);
        try {
            var result = inventoryServices.findProductWith(findProductRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), OK);
        } catch (EstoreAppException e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
    }

    @GetMapping("/find-all-products")
    public ResponseEntity<?> findAllProducts() {
        try {
            var result = inventoryServices.findAllProducts();
            return new ResponseEntity<>(new ApiResponse(true, result), OK);
        } catch (EstoreAppException e) {
            return new ResponseEntity<>(new ApiResponse(true, e.getMessage()), NO_CONTENT);
        }
    }

    private static ResponseEntity<String> getValidationErrorMessageOf(Errors errors) {
        return new ResponseEntity<>(String.format("Operation failed: %s is null",
                requireNonNull(errors.getFieldError()).getField()), BAD_REQUEST);
    }
}
