package org.inventory.models.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.inventory.models.items.Item;

import java.util.List;

public record Cart(@JsonProperty("items") List<Item> items) {
}
