package org.inventory.models.items.products;

import org.inventory.models.items.Item;

public class ProductFactory {
    public static Item generate(Item item) {
        Item generated = null;
        if (item instanceof AnimalProduct) {
            generated = new AnimalProduct((AnimalProduct) item);
        } else if (item instanceof Detergent) {
            generated = new Detergent((Detergent) item);
        } else if (item instanceof DoughProduct) {
            generated = new DoughProduct((DoughProduct) item);
        } else if (item instanceof PlantProduct) {
            generated = new PlantProduct((PlantProduct) item);
        }
        return generated;
    }
}
