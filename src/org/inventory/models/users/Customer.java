package org.inventory.models.users;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.inventory.models.orders.Cart;
import org.inventory.models.orders.Order;
import org.inventory.util.ReadFromConsole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public class Customer extends User{
    private final ReadFromConsole read = ReadFromConsole.getInstance();
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public Customer(String userName, String password, String phoneNumber) {
        super(userName, password, phoneNumber, "Customer");
    }

    @JsonCreator
    public Customer(@JsonProperty("uuid") String uuid, @JsonProperty("type") String type, @JsonProperty("userName") String userName, @JsonProperty("password") String password, @JsonProperty("phoneNumber") String phoneNumber, @JsonProperty("cart") Cart cart, @JsonProperty("orders") List<Order> orders) {
        super(uuid, type, userName, password, phoneNumber, cart, orders);
    }

    public void changePassword() {
        System.out.println("Input old password:");
        String oldPass = read.readString();
        if (encoder.matches(oldPass, super.getPassword())) {
            super.setPassword(encoder.encode(read.readPass()));
        } else {
            System.out.println("Invalid password! Insert correct password to change it!");
        }
    }

    public void changePhoneNumber() {
        System.out.println("Insert new phone number:");
        String phoneNumber = String.valueOf(read.readInteger());
        super.setPhoneNumber(phoneNumber);
    }

}
