package africa.Semicolon.eStore.data.repositories;

import africa.Semicolon.eStore.data.models.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@DataMongoTest
public class OrdersTest {
    @Autowired
    private Orders orders;

    @Test
    public void ordersTest(){
        Order newOrder = new Order();
        var savedUser = orders.save(newOrder);
        assertThat(savedUser.getId(), notNullValue());
        assertThat(orders.count(), is(1L));
    }

}