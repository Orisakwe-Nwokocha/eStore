package africa.Semicolon.eStore.controllers;

import africa.Semicolon.eStore.dtos.requests.FindProductRequest;
import africa.Semicolon.eStore.dtos.responses.ApiResponse;
import africa.Semicolon.eStore.exceptions.EstoreAppException;
import africa.Semicolon.eStore.services.InventoryServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/user")
public class InventoryControllers {
    @Autowired
    private InventoryServices inventoryServices;

    @GetMapping("/find-product")
    public ResponseEntity<?> findProductWith(@Valid @RequestBody FindProductRequest findProductRequest) {
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
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), NO_CONTENT);
        }
    }
}