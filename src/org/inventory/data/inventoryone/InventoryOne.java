package org.inventory.data.inventoryone;

import com.fasterxml.jackson.core.type.TypeReference;
import org.inventory.models.users.Admin;
import org.inventory.util.Encoder;
import org.inventory.util.Serializer;
import org.inventory.models.items.Item;
import org.inventory.models.orders.Order;
import org.inventory.models.users.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

public class InventoryOne {
    private static InventoryOne inventory;
    private List<Item> items;
    private List<User> users;
    private Map<User, List<Order>> history;

    private final Serializer<List<Item>> itemSerializer = new Serializer<>();
    private final Serializer<List<User>> userSerializer = new Serializer<>();
    private final Serializer<Map<User, List<Order>>> historySerializer = new Serializer<>();
    private InventoryOne() {
        items = importItems();
        users = importUsers();
        history = importHistory();
    }

    public static InventoryOne getInventory() {
        if (Objects.isNull(inventory)) {
            inventory = new InventoryOne();
        }
        return inventory;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<User> getUsers() {
        return users;
    }

    public Map<User, List<Order>> getHistory() {
        return history;
    }

    private List<Item> importItems() {
        TypeReference<List<Item>> typeReference = new TypeReference<>() {};
        List<Item> temp = itemSerializer.readFromJsonFile("src/org/inventory/data/items.json", typeReference);
        if (Objects.isNull(temp)) {
            temp = new ArrayList<>();
        }
        return temp;
    }

    private List<User> importUsers() {
        TypeReference<List<User>> typeReference = new TypeReference<>() {};
        List<User> temp = userSerializer.readFromJsonFile("src/org/inventory/data/users.json", typeReference);
        if (Objects.isNull(temp)) {
            Encoder encoder = Encoder.getInstance();
            temp = new ArrayList<>();
            temp.add(new Admin("Admin", encoder.encode("@Dmin1234"), "0879385550"));
        }
        return temp;
    }

    private Map<User, List<Order>> importHistory() {
        TypeReference<Map<User, List<Order>>> typeReference = new TypeReference<>() {};
        Map<User, List<Order>> temp = historySerializer.readFromJsonFile("src/org/inventory/data/history.json", typeReference);
        if (Objects.isNull(temp)) {
            temp = new LinkedHashMap<>();
        }
        return temp;
    }

    public void updateItems() {
        itemSerializer.writeToJsonFile("src/org/inventory/data/items.json", items);
    }

    public void updateUsers() {
        userSerializer.writeToJsonFile("src/org/inventory/data/users.json", users);
    }

    public void updateHistory() {
        historySerializer.writeToJsonFile("src/org/inventory/data/history.json", history);
    }
}
