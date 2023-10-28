package org.inventory.models.users;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.inventory.models.orders.Cart;
import org.inventory.models.orders.Order;

import java.util.List;

@JsonTypeName("Admin")
public class Admin extends User {
    public Admin(String userName, String password, String phoneNumber) {
        super(userName, "Admin", password, phoneNumber);
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Admin(@JsonProperty("uuid") String uuid, @JsonProperty("userName") String userName, @JsonProperty("password") String password, @JsonProperty("phoneNumber") String phoneNumber, @JsonProperty("cart") Cart cart, @JsonProperty("orders") List<Order> orders) {
        super(uuid, "Admin", userName, password, phoneNumber, cart, orders);
    }
}
