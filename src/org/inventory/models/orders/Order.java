package org.inventory.models.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.inventory.models.items.Item;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private final String id;
    private final LocalDateTime date;
    private final List<Item> items;
    private Status status;

    @JsonCreator
    public Order(@JsonProperty("id") String id, @JsonProperty("date") LocalDateTime date, @JsonProperty("items") List<Item> items, @JsonProperty("status") Status status) {
        this.id  = id;
        this.date = date;
        this.items = items;
        this.status = status;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("date")
    public LocalDateTime getDate() {
        return date;
    }

    @JsonProperty("items")
    public List<Item> getItems() {
        return items;
    }

    @JsonProperty("status")
    public Status getStatus() {
        return status;
    }

    public float getTotalPrice() {
        float total = 0.0f;
        for (Item item : items) {
            total += item.getTotalPrice(item.getQuantity());
        }
        return total;
    }

    public void changeStatus(Status status) {
        this.status = status;
    }
}
