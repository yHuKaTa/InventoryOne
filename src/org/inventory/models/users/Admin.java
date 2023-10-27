package org.inventory.models.users;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.inventory.models.orders.Cart;
import org.inventory.models.orders.Order;

import java.util.List;

public class Admin extends User{
    public Admin(String userName, String password, String phoneNumber) {
        super(userName, password, phoneNumber, "Admin");
    }

    @JsonCreator
    public Admin(@JsonProperty("uuid") String uuid, @JsonProperty("type") String type,  @JsonProperty("userName") String userName, @JsonProperty("password") String password, @JsonProperty("phoneNumber") String phoneNumber, @JsonProperty("cart") Cart cart, @JsonProperty("orders") List<Order> orders) {
        super(uuid, type, userName, password, phoneNumber, cart, orders);
    }
}
