package org.inventory.ui;

import org.inventory.data.inventoryone.InventoryOne;
import org.inventory.models.items.Item;
import org.inventory.models.items.Promotion;
import org.inventory.models.items.products.AnimalProduct;
import org.inventory.models.items.products.Detergent;
import org.inventory.models.items.products.DoughProduct;
import org.inventory.models.items.products.PlantProduct;
import org.inventory.models.users.Admin;
import org.inventory.models.users.Customer;
import org.inventory.models.users.User;
import org.inventory.util.ReadFromConsole;

import java.time.LocalDate;
import java.util.*;

public class ViewItemsList {
    private static ViewItemsList itemsList;
    private final InventoryOne inventory;
    private User user;
    private final ReadFromConsole read;

    private ViewItemsList(User user) {
        this.inventory = InventoryOne.getInventory();
        this.read = ReadFromConsole.getInstance();
        this.user = user;
    }

    public static void getViewItemsList(User user) {
        if (user instanceof Customer) {
            System.out.println("You not have permission for this operations!");
            return;
        } else {
            if (Objects.isNull(itemsList)) {
                itemsList = new ViewItemsList(user);
            } else {
                itemsList.setUser(user);
            }
            itemsList.getItems();
        }
    }

    private void setUser(User user) {
        this.user = user;
    }

    private void getItems() {
        if (Objects.isNull(inventory.getItems()) || inventory.getItems().isEmpty()) {
            System.out.println("Sorry! There aren't items in warehouse!");
            System.out.println("To add new item insert 1");
            System.out.println("To exit insert other else");
            int choice = read.readInteger();
            if (choice == 1) {
                System.out.println("Insert product type:");
                System.out.println("for animal product insert 1");
                System.out.println("for detergent insert 2");
                System.out.println("for dough product insert 3");
                System.out.println("for plant product insert 4");
                System.out.println("To exit menu insert every other digit");
                choice = read.readInteger();
                switch (choice) {
                    case 1: {
                        System.out.println("Insert product name:");
                        String name = read.readString();
                        System.out.println("Insert product price:");
                        float price = (float) read.readDouble();
                        System.out.println("Insert initial quantity:");
                        float quantity = (float) read.readDouble();
                        System.out.println("Insert product date of expiration in format yyyy-mm-dd:");
                        LocalDate date = LocalDate.parse(read.readDate());
                        while (LocalDate.now().isAfter(date)) {
                            date = LocalDate.parse(read.readDate());
                        }
                        inventory.getItems().add(new AnimalProduct(name, price, quantity, date));
                        inventory.updateItems();
                        MainMenu.getMain();
                        return;
                    }
                    case 2: {
                        System.out.println("Insert product name:");
                        String name = read.readString();
                        System.out.println("Insert product price:");
                        float price = (float) read.readDouble();
                        System.out.println("Insert initial quantity:");
                        float quantity = (float) read.readDouble();
                        inventory.getItems().add(new Detergent(name, price, quantity));
                        inventory.updateItems();
                        MainMenu.getMain();
                        return;
                    }
                    case 3: {
                        System.out.println("Insert product name:");
                        String name = read.readString();
                        System.out.println("Insert product price:");
                        float price = (float) read.readDouble();
                        System.out.println("Insert initial quantity:");
                        float quantity = (float) read.readDouble();
                        inventory.getItems().add(new DoughProduct(name, price, quantity));
                        inventory.updateItems();
                        MainMenu.getMain();
                        return;
                    }
                    case 4: {
                        System.out.println("Insert product name:");
                        String name = read.readString();
                        System.out.println("Insert product price:");
                        float price = (float) read.readDouble();
                        System.out.println("Insert initial quantity:");
                        float quantity = (float) read.readDouble();
                        System.out.println("Insert product date of expiration in format yyyy-mm-dd:");
                        LocalDate date = LocalDate.parse(read.readDate());
                        while (LocalDate.now().isAfter(date)) {
                            date = LocalDate.parse(read.readDate());
                        }
                        inventory.getItems().add(new PlantProduct(name, price, quantity, date));
                        inventory.updateItems();
                        MainMenu.getMain();
                        return;
                    }
                    default: {
                        MainMenu.getMain();
                        return;
                    }
                }
            } else {
                MainMenu.getMain();
                return;
            }
        } else {
            System.out.println("To add new item insert 1");
            System.out.println("To modify existing item insert 2");
            System.out.println("To review breakage of items insert 3");
            System.out.println("To review promotion campaigns insert 4");
            System.out.println("To exit menu insert every other digit");
            int choice = read.readInteger();
            switch (choice) {
                case 1: {
                    System.out.println("Insert product type:");
                    System.out.println("for animal product insert 1");
                    System.out.println("for detergent insert 2");
                    System.out.println("for dough product insert 3");
                    System.out.println("for plant product insert 4");
                    System.out.println("To exit menu insert every other digit");
                    choice = read.readInteger();
                    switch (choice) {
                        case 1: {
                            System.out.println("Insert product name:");
                            String name = read.readString();
                            System.out.println("Insert product price:");
                            float price = (float) read.readDouble();
                            System.out.println("Insert initial quantity:");
                            float quantity = (float) read.readDouble();
                            System.out.println("Insert product date of expiration in format yyyy-mm-dd:");
                            LocalDate date = LocalDate.parse(read.readDate());
                            while (LocalDate.now().isAfter(date)) {
                                date = LocalDate.parse(read.readDate());
                            }
                            inventory.getItems().add(new AnimalProduct(name, price, quantity, date));
                            inventory.updateItems();
                            MainMenu.getMain();
                            return;
                        }
                        case 2: {
                            System.out.println("Insert product name:");
                            String name = read.readString();
                            System.out.println("Insert product price:");
                            float price = (float) read.readDouble();
                            System.out.println("Insert initial quantity:");
                            float quantity = (float) read.readDouble();
                            inventory.getItems().add(new Detergent(name, price, quantity));
                            inventory.updateItems();
                            MainMenu.getMain();
                            return;
                        }
                        case 3: {
                            System.out.println("Insert product name:");
                            String name = read.readString();
                            System.out.println("Insert product price:");
                            float price = (float) read.readDouble();
                            System.out.println("Insert initial quantity:");
                            float quantity = (float) read.readDouble();
                            inventory.getItems().add(new DoughProduct(name, price, quantity));
                            inventory.updateItems();
                            MainMenu.getMain();
                            return;
                        }
                        case 4: {
                            System.out.println("Insert product name:");
                            String name = read.readString();
                            System.out.println("Insert product price:");
                            float price = (float) read.readDouble();
                            System.out.println("Insert initial quantity:");
                            float quantity = (float) read.readDouble();
                            System.out.println("Insert product date of expiration in format yyyy-mm-dd:");
                            LocalDate date = LocalDate.parse(read.readDate());
                            while (LocalDate.now().isAfter(date)) {
                                date = LocalDate.parse(read.readDate());
                            }
                            inventory.getItems().add(new PlantProduct(name, price, quantity, date));
                            inventory.updateItems();
                            MainMenu.getMain();
                            return;
                        }
                        default: {
                            MainMenu.getMain();
                            return;
                        }
                    }
                }
                case 2: {
                    int num = 1;
                    System.out.println("Select item ID to manipulate it:");
                    for (Item item : inventory.getItems()) {
                        System.out.printf("%d) %s type: %s id: %d qty: %f price: %f\n", num, item.getName(), item.getCategory(), item.getId(), item.getQuantity(), item.getPrice());
                    }
                    System.out.println("To exit items menu insert 0.");
                    long productID = read.readLong();
                    if (productID == 0) {
                        MainMenu.getMain();
                        return;
                    }
                    boolean isFound = false;
                    Iterator<Item> iterator = inventory.getItems().iterator();
                    while (iterator.hasNext()) {
                        Item searchedItem = iterator.next();
                        if (searchedItem.getId() == productID) {
                            isFound = true;
                            System.out.println("To remove item insert 1");
                            System.out.println("To change item's quantity insert 2");
                            System.out.println("To change item's price insert 3");
                            System.out.println("To change item's name insert 4");
                            System.out.println("To exit menu insert every other digit");
                            choice = read.readInteger();
                            switch (choice) {
                                case 1: {
                                    iterator.remove();
                                    inventory.updateItems();
                                    MainMenu.getMain();
                                    return;
                                }
                                case 2: {
                                    for (Item item : inventory.getItems()) {
                                        if (item.getId() == searchedItem.getId()) {
                                            System.out.println("Insert new quantity of product:");
                                            float newQuantity = (float) read.readDouble();
                                            item.setQuantity(newQuantity);
                                            inventory.updateItems();
                                            MainMenu.getMain();
                                            return;
                                        }
                                    }
                                    break;
                                }
                                case 3: {
                                    for (Item item : inventory.getItems()) {
                                        if (item.getId() == searchedItem.getId()) {
                                            System.out.println("Insert new price of product:");
                                            float newPrice = (float) read.readDouble();
                                            item.setPrice(newPrice);
                                            inventory.updateItems();
                                            MainMenu.getMain();
                                            return;
                                        }
                                    }
                                    break;
                                }
                                case 4: {
                                    for (Item item : inventory.getItems()) {
                                        if (item.getId() == searchedItem.getId()) {
                                            System.out.println("Insert new name of product:");
                                            String newName = read.readString();
                                            item.setName(newName);
                                            inventory.updateItems();
                                            MainMenu.getMain();
                                            return;
                                        }
                                    }
                                    break;
                                }
                                default: {
                                    MainMenu.getMain();
                                    return;
                                }
                            }
                            break;
                        }
                    }
                    if (!isFound) {
                        System.out.println("Insert valid item ID!");
                        getItems();
                        return;
                    }
                    break;
                }
                case 3: {
                    int num = 1;
                    System.out.println("Select item ID to review breakage:");
                    for (Item searchedItem : inventory.getItems()) {
                        if (searchedItem instanceof Detergent) {
                            System.out.printf("%d) %s type: %s id: %d qty: %f price: %f\n", num, searchedItem.getName(), searchedItem.getCategory(), searchedItem.getId(), searchedItem.getQuantity(), searchedItem.getPrice());
                            num++;
                        }
                    }
                    boolean isFound = false;
                    long breakage = read.readLong();
                    for (Item item : inventory.getItems()) {
                        if (item instanceof Detergent && item.getId() == breakage) {
                            isFound = true;
                            System.out.println("Insert quantity of broken items:");
                            int quantity = read.readInteger();
                            ((Detergent) item).handleBreakage(quantity);
                            inventory.updateItems();
                            MainMenu.getMain();
                            return;
                        }
                    }
                    System.out.println("Insert valid item ID!");
                    getItems();
                    return;
                }
                case 4: {
                    System.out.println("Insert item ID to review promotions campaigns");
                    int num = 1;
                    for (Item item : inventory.getItems()) {
                        if (item instanceof AnimalProduct) {
                            System.out.printf("%d) %s type: %s ID: %d\n", num, item.getName(), item.getCategory(), item.getId());
                        } else if (item instanceof Detergent) {
                            System.out.printf("%d) %s type: %s ID: %d\n", num, item.getName(), item.getCategory(), item.getId());
                        } else if (item instanceof DoughProduct) {
                            System.out.printf("%d) %s type: %s ID: %d\n", num, item.getName(), item.getCategory(), item.getId());
                        }
                        num++;
                    }
                    long productId = read.readLong();
                    boolean isFound = false;
                    for (Item item : inventory.getItems()) {
                        if (item.getId() == productId) {
                            isFound = true;
                            switch (item) {
                                case AnimalProduct animalProduct -> {
                                    if (animalProduct.getPromotions().isEmpty()) {
                                        System.out.println("There aren't promotion campaigns!");
                                        System.out.println("To add new insert 1");
                                        System.out.println("To exit insert every else digit");
                                    } else {
                                        num = 1;
                                        for (Promotion promotion : animalProduct.getPromotions()) {
                                            System.out.printf("%d) %s - start date : %s end date: %s discount: %f\n", num, promotion.promotion(), promotion.startDate().toString(), promotion.endDate().toString(), promotion.discount());
                                        }
                                        System.out.println("To add new promotion insert 1");
                                        System.out.println("To exit insert every else");
                                    }
                                    choice = read.readInteger();
                                    if (choice == 1) {
                                        addPromotion(animalProduct);
                                    }
                                    getItems();
                                    return;
                                }
                                case Detergent detergent -> {
                                    if (detergent.getPromotions().isEmpty()) {
                                        System.out.println("There aren't promotion campaigns!");
                                        System.out.println("To add new insert 1");
                                        System.out.println("To exit insert every else digit");
                                    } else {
                                        num = 1;
                                        for (Promotion promotion : detergent.getPromotions()) {
                                            System.out.printf("%d) %s - start date : %s end date: %s discount: %f\n", num, promotion.promotion(), promotion.startDate().toString(), promotion.endDate().toString(), promotion.discount());
                                        }
                                        System.out.println("To add new promotion insert 1");
                                        System.out.println("To exit insert every else");
                                    }
                                    choice = read.readInteger();
                                    if (choice == 1) {
                                        addPromotion(detergent);
                                    }
                                    getItems();
                                    return;
                                }
                                case DoughProduct doughProduct -> {
                                    if (doughProduct.getPromotions().isEmpty()) {
                                        System.out.println("There aren't promotion campaigns!");
                                        System.out.println("To add new insert 1");
                                        System.out.println("To exit insert every else digit");
                                    } else {
                                        num = 1;
                                        for (Promotion promotion : doughProduct.getPromotions()) {
                                            System.out.printf("%d) %s - start date : %s end date: %s discount: %f\n", num, promotion.promotion(), promotion.startDate().toString(), promotion.endDate().toString(), promotion.discount());
                                        }
                                        System.out.println("To add new promotion insert 1");
                                        System.out.println("To exit insert every else");
                                    }
                                    choice = read.readInteger();
                                    if (choice == 1) {
                                        addPromotion(doughProduct);
                                    }
                                    getItems();
                                    return;
                                }
                                default -> {
                                    getItems();
                                    return;
                                }
                            }
                        }
                    }
                    System.out.println("Insert valid item ID!");
                    getItems();
                    return;
                }
                default: {
                    MainMenu.getMain();
                    return;
                }
            }

        }
    }


    private void addPromotion(Item item) {
        System.out.println("Insert name of campaign:");
        String name = read.readString();
        while (name.isEmpty()) {
            System.out.println("Insert valid campaign name with at least one character!");
            name = read.readString();
        }
        System.out.println("Insert start date of promotion in format yyyy-mm-dd:");
        String startDate = read.readDate();
        LocalDate start = LocalDate.parse(startDate);
        while (start.isBefore(LocalDate.now()) ||
                start.isEqual(LocalDate.now())) {
            System.out.println("Insert valid date that is grater than today!");
            startDate = read.readDate();
            start = LocalDate.parse(startDate);
        }
        String endDate = read.readDate();
        LocalDate end = LocalDate.parse(endDate);
        while (end.isBefore(LocalDate.now()) ||
                end.isEqual(LocalDate.now()) ||
                end.isBefore(start) ||
                end.isEqual(start)) {
            System.out.println("Insert valid date that is after start date!");
            endDate = read.readDate();
            end = LocalDate.parse(endDate);
        }
        System.out.println("Insert percent of discount:");
        int percent = read.readInteger();
        if (item instanceof AnimalProduct) {
            ((AnimalProduct) item).setNewPromotion(name, start, end, percent);
            inventory.updateItems();
            ((AnimalProduct) item).handleExpiration();
        } else if (item instanceof Detergent) {
            ((Detergent) item).setNewPromotion(name, start, end, percent);
            inventory.updateItems();
            ((Detergent) item).handleExpiration();
        } else if (item instanceof DoughProduct) {
            ((DoughProduct) item).setNewPromotion(name, start, end, percent);
            inventory.updateItems();
            ((DoughProduct) item).handleExpiration();
        }
    }
}
