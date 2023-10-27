package org.inventory.models.items;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private long id;
    private String category;
    private String name;
    private float price;
    private float quantity;

    public Item(String category, String name, float price, float quantity) {
        Random random = new Random();
        this.id = random.nextLong(1,Long.MAX_VALUE);
        this.category = category;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @JsonCreator
    public Item(@JsonProperty("id") long id, @JsonProperty("category") String category, @JsonProperty("name") String name, @JsonProperty("price") float price, @JsonProperty("quantity") float quantity) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @JsonProperty("id")
    public long getId() {
        return id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("quantity")
    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void sell(float quantity) {
        if (this.quantity > quantity) {
            this.quantity =- quantity;
        } else {
            System.out.println("Insufficient quantity!");
        }
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    @JsonProperty("category")
    public String getCategory() {
        return this.category;
    }

    @Override
    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    @JsonProperty("price")
    public float getPrice() {
        return price;
    }

    @Override
    public float getTotalPrice(float quantity) {
        return quantity * this.price;
    }
}
