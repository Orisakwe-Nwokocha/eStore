package africa.Semicolon.eStore.data.repositories;

import africa.Semicolon.eStore.data.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@DataMongoTest
public class InventoryTest {
    @Autowired
    private Inventory inventory;

    @Test
    public void inventoryTest() {
        inventory.deleteAll();
        Product newProduct = new Product();
        var savedProduct = inventory.save(newProduct);
        assertThat(savedProduct.getId(), notNullValue());
        assertThat(inventory.count(), is(1L));
    }

}