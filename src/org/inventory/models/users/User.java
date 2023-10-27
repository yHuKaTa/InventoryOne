package org.inventory.models.users;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.inventory.models.items.Item;
import org.inventory.models.orders.Cart;
import org.inventory.models.orders.Order;
import org.inventory.models.orders.Status;

import java.time.LocalDateTime;
import java.util.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Admin.class, name = "Admin"),
        @JsonSubTypes.Type(value = Customer.class, name = "Customer")
})
public abstract class User {
    private final String uuid;
    private String type;
    private final String userName;
    private String password;
    private String phoneNumber;
    private final Cart cart;
    private final List<Order> orders;

    public User(String userName, String password, String phoneNumber, String type) {
        this.uuid = String.valueOf(UUID.randomUUID());
        this.type = type;
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.cart = new Cart(new ArrayList<>());
        this.orders = new LinkedList<>();
    }

    @JsonCreator
    public User(@JsonProperty("uuid") String uuid, @JsonProperty("type") String type, @JsonProperty("userName") String userName, @JsonProperty("password") String password, @JsonProperty("phoneNumber") String phoneNumber, @JsonProperty("cart") Cart cart, @JsonProperty("orders") List<Order> orders) {
        this.uuid = uuid;
        this.type = type;
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.cart = cart;
        this.orders = orders;
    }

    @JsonProperty("uuid")
    public String getUuid() {
        return uuid;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("userName")
    public String getUserName() {
        return userName;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty("cart")
    public Cart getCart() {
        return cart;
    }

    @JsonProperty("orders")
    public List<Order> getOrders() {
        return orders;
    }

    public void addToCart(Item item) {
        cart.items().add(item);
    }

    public boolean reduceQuantity(long id, float quantity) {
        boolean isReduced = false;
        boolean isFound = false;
        boolean isZero = quantity <= 0.0f;
        if (isZero) {
            isReduced = removeFromCart(id);
        } else {
            for (Item item : this.cart.items()) {
                if (item.getId() == id) {
                    isFound = true;
                    if (quantity > item.getQuantity()) {
                        System.out.printf("Insert valid quantity! Max quantity is: %f\n", item.getQuantity());
                        break;
                    } else {
                        item.setQuantity(item.getQuantity() - quantity);
                        isReduced = true;
                        break;
                    }
                }
            }
            if (!isFound) {
                System.out.println("Item not found in your cart!");
            }
        }
        return isReduced;
    }

    public boolean removeFromCart(long id) {
        boolean isFound = false;
        Iterator<Item> iterator = this.cart.items().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == id) {
                iterator.remove();
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            System.out.println("Item not found in your cart!");
        }
        return isFound;
    }

    public void viewCart() {
        System.out.println("Your cart:");
        int num = 1;
        for (Item item : this.cart.items()) {
            System.out.printf("%d) %s id:%d qty: %f price: %f total: %f\n", num, item.getName(), item.getId(), item.getQuantity(), item.getPrice(), item.getTotalPrice(item.getQuantity()));
            num++;
        }
    }

    public void viewHistory() {
        System.out.println("Your orders history:");
        int num = 1;
        for (Order order : this.orders) {
            System.out.printf("%d) ID: %s from %s total: %f status: %s\n", num, order.getId(), order.getDate().toString(), order.getTotalPrice(), order.getStatus().label);
        }
    }

    public void generateOrder() {
        List<Item> temp = List.copyOf(this.cart.items());
        orders.add(new Order(String.valueOf(UUID.randomUUID()), LocalDateTime.now(), temp, Status.ACTIVE));
        this.cart.items().clear();
    }

    void setPassword(String password) {
        this.password = password;
    }

    void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
