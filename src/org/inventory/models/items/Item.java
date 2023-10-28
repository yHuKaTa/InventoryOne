package org.inventory.models.items;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.inventory.models.items.appearances.Categorizable;
import org.inventory.models.items.appearances.Sellable;
import org.inventory.models.items.products.AnimalProduct;
import org.inventory.models.items.products.Detergent;
import org.inventory.models.items.products.DoughProduct;
import org.inventory.models.items.products.PlantProduct;

import java.util.Random;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "category")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PlantProduct.class, name = "Plant Product"),
        @JsonSubTypes.Type(value = DoughProduct.class, name = "Dough Product"),
        @JsonSubTypes.Type(value = Detergent.class, name = "Detergent"),
        @JsonSubTypes.Type(value = AnimalProduct.class, name = "Animal Product")
})
public abstract class Item implements Sellable, Categorizable {
    private final long id;
    private final String category;
    private String name;
    private float price;
    private float quantity;

    public Item(Item otherItem) {
        this.id = otherItem.id;
        this.category = otherItem.category;
        this.name = otherItem.name;
        this.price = otherItem.price;
        this.quantity = otherItem.quantity;
    }

    public Item(String category, String name, float price, float quantity) {
        Random random = new Random();
        this.id = random.nextLong(1,Long.MAX_VALUE);
        this.category = category;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Item(long id, String category, String name, float price, float quantity) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    @Override
    public String getCategory() {
        return this.category;
    }

    @Override
    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public float getTotalPrice(float quantity) {
        return quantity * this.price;
    }
}
