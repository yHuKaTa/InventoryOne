package org.inventory.data.inventoryone;

import com.fasterxml.jackson.core.type.TypeReference;
import org.inventory.models.items.products.AnimalProduct;
import org.inventory.models.items.products.DoughProduct;
import org.inventory.models.items.products.PlantProduct;
import org.inventory.models.users.Admin;
import org.inventory.util.Encoder;
import org.inventory.util.Serializer;
import org.inventory.models.items.Item;
import org.inventory.models.orders.Order;
import org.inventory.models.users.User;

import java.util.*;

/**
 * This singleton class is responsible for DB information from files.
 * Information for the API are read from files by call of methods starts with "import".
 * When information is changed, API will call methods starts with "update" to update information by replacing old one.
 * All we need is proper file location. File locator is in methods for storing and reading information.
 * To serialize objects attributes API use custom Serializer with help of Jackson's Object mapper.
 * <p>
 * In this API will storage lists of items and users. To generate history of orders API will storage Map of List of orders for current user ID.
 */
public class InventoryOne {
    private static InventoryOne inventory;
    private final List<Item> items;
    private final List<User> users;
    private final Map<String, List<Order>> history;

    private final Serializer<List<Item>> itemSerializer = new Serializer<>();
    private final Serializer<List<User>> userSerializer = new Serializer<>();
    private final Serializer<Map<String, List<Order>>> historySerializer = new Serializer<>();

    private InventoryOne() {
        items = importItems();
        handleExpiration();
        users = importUsers();
        history = importHistory();
    }

    public static InventoryOne getInventory() {
        if (Objects.isNull(inventory)) {
            inventory = new InventoryOne();
        }
        return inventory;
    }

    private void handleExpiration() {
        for (Item item : items) {
            if (item instanceof AnimalProduct) {
                ((AnimalProduct) item).handleExpiration();
            } else if (item instanceof DoughProduct) {
                ((DoughProduct) item).handleExpiration();
            } else if (item instanceof PlantProduct) {
                ((PlantProduct) item).handleExpiration();
            }
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public List<User> getUsers() {
        return users;
    }

    public Map<String, List<Order>> getHistory() {
        return history;
    }

    private List<Item> importItems() {
        TypeReference<List<Item>> typeReference = new TypeReference<>() {
        };
        List<Item> temp = itemSerializer.readFromJsonFile("src/org/inventory/data/items.json", typeReference);
        if (Objects.isNull(temp)) {
            temp = new ArrayList<>();
        }
        return temp;
    }

    private List<User> importUsers() {
        TypeReference<List<User>> typeReference = new TypeReference<>() {
        };
        List<User> temp = userSerializer.readFromJsonFile("src/org/inventory/data/users.json", typeReference);
        if (Objects.isNull(temp)) {
            Encoder encoder = Encoder.getInstance();
            temp = new ArrayList<>();
            temp.add(new Admin("Admin", encoder.encode("@Dmin1234"), "0879385550"));
        }
        return temp;
    }

    private Map<String, List<Order>> importHistory() {
        TypeReference<Map<String, List<Order>>> typeReference = new TypeReference<>() {
        };
        Map<String, List<Order>> temp = historySerializer.readFromJsonFile("src/org/inventory/data/history.json", typeReference);
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
