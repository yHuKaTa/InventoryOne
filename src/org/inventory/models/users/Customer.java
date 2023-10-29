package org.inventory.models.users;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.inventory.models.orders.Cart;
import org.inventory.models.orders.Order;

import java.util.List;

@JsonTypeName("Customer")
public class Customer extends User{
    public Customer(String userName, String password, String phoneNumber) {
        super(userName, "Customer", password, phoneNumber);
    }

    public Customer(User user) {
        super(user);
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Customer(@JsonProperty("uuid") String uuid, @JsonProperty("userName") String userName, @JsonProperty("password") String password, @JsonProperty("phoneNumber") String phoneNumber, @JsonProperty("cart") Cart cart, @JsonProperty("orders") List<Order> orders) {
        super(uuid, "Customer", userName, password, phoneNumber, cart, orders);
    }
}
