package africa.Semicolon.eStore.services;

import africa.Semicolon.eStore.data.models.Product;
import africa.Semicolon.eStore.data.repositories.Inventory;
import africa.Semicolon.eStore.dtos.requests.AddProductRequest;
import africa.Semicolon.eStore.dtos.responses.AddProductResponse;
import africa.Semicolon.eStore.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static africa.Semicolon.eStore.utils.Mapper.map;
import static africa.Semicolon.eStore.utils.Mapper.mapAddProductResponseWith;

@Service
public class InventoryServicesImpl implements InventoryServices {
    @Autowired
    private Inventory inventory;

    @Override
    public AddProductResponse addProductWith(AddProductRequest addProductRequest) {
        Product newProduct = map(addProductRequest);
        inventory.save(newProduct);
        return mapAddProductResponseWith(newProduct);
    }
}
