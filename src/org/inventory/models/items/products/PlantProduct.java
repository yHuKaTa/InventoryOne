package org.inventory.models.items.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.inventory.models.items.Item;
import org.inventory.models.items.appearances.Discountable;
import org.inventory.models.items.appearances.Perishable;

import java.time.LocalDate;

@JsonTypeName("Plant Product")
public class PlantProduct extends Item implements Perishable, Discountable {
    private LocalDate dateOfExpiration;
    private float discount;
    private boolean discounted;

    public PlantProduct(PlantProduct otherProduct) {
        super(otherProduct);
        this.dateOfExpiration = LocalDate.from(otherProduct.dateOfExpiration);
        this.discount = otherProduct.discount;
        this.discounted = otherProduct.discounted;
    }

    public PlantProduct(String name, float price, float quantity, LocalDate dateOfExpiration) {
        super("Plant Product", name, price, quantity);
        this.dateOfExpiration = dateOfExpiration;
        this.discount = 0.0f;
        this.discounted = false;
    }

    @JsonCreator
    public PlantProduct(@JsonProperty("id") long id, @JsonProperty("name") String name, @JsonProperty("price") float price, @JsonProperty("quantity") float quantity, @JsonProperty("dateOfExpiration") LocalDate dateOfExpiration, @JsonProperty("discount") float discount, @JsonProperty("discounted") boolean discounted) {
        super(id, "Plant Product", name, price, quantity);
        this.dateOfExpiration = dateOfExpiration;
        this.discount = discount;
        this.discounted = discounted;
    }

    public LocalDate getDateOfExpiration() {
        return dateOfExpiration;
    }

    public float getDiscount() {
        return discount;
    }

    @Override
    public void applyDiscount(int discountPercentage) {
        super.setPrice(super.getPrice() - (super.getPrice() * discountPercentage));
        this.discounted = true;
    }

    @Override
    public void setDiscount(int discountPercentage) {
        this.discount = (discountPercentage * 1.0f / 100);
    }

    @Override
    public void resetPrice() {
        super.setPrice(super.getPrice() + (super.getPrice() * discount));
        this.discounted = false;
    }

    @Override
    public void setExpiration(String dateOfExpiration) {
        LocalDate date = LocalDate.parse(dateOfExpiration);
        if (date.isAfter(LocalDate.now())) {
            this.dateOfExpiration = date;
        } else {
            System.out.println("Insert valid date of expiration!");
        }
    }

    @Override
    public void handleExpiration() {
        if (dateOfExpiration.isEqual(LocalDate.now().plusDays(2)) && !this.discounted) {
            applyDiscount(20);
        } else {
            resetPrice();
        }
    }
}
