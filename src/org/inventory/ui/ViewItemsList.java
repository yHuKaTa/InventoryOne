package org.inventory.ui;

import org.inventory.data.inventoryone.InventoryOne;
import org.inventory.models.items.Item;
import org.inventory.models.items.products.AnimalProduct;
import org.inventory.models.items.products.Detergent;
import org.inventory.models.items.products.DoughProduct;
import org.inventory.models.items.products.PlantProduct;
import org.inventory.models.users.Admin;
import org.inventory.models.users.Customer;
import org.inventory.models.users.User;
import org.inventory.util.ReadFromConsole;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Objects;

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
                        inventory.getItems().add(new AnimalProduct(name, price, quantity, date));
                        inventory.updateItems();
                        MainMenu.getMain();
                        break;
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
                        break;
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
                        break;
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
                        inventory.getItems().add(new PlantProduct(name, price, quantity, date));
                        inventory.updateItems();
                        MainMenu.getMain();
                        break;
                    }
                    default: {
                        MainMenu.getMain();
                        break;
                    }
                }
            } else {
                MainMenu.getMain();
            }
        } else {
            System.out.println("To add new item insert 1");
            System.out.println("To modify existing item insert 2");
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
                            inventory.getItems().add(new AnimalProduct(name, price, quantity, date));
                            inventory.updateItems();
                            MainMenu.getMain();
                            break;
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
                            break;
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
                            break;
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
                            inventory.getItems().add(new PlantProduct(name, price, quantity, date));
                            inventory.updateItems();
                            MainMenu.getMain();
                            break;
                        }
                        default: {
                            MainMenu.getMain();
                            break;
                        }
                    }
                    break;
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
                    Iterator<Item> iterator = inventory.getItems().iterator();
                    while (iterator.hasNext()) {
                        Item searchedItem = iterator.next();
                        if (searchedItem.getId() == productID) {
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
                                    break;
                                }
                                case 2: {
                                    for (Item item : inventory.getItems()) {
                                        if (item.getId() == searchedItem.getId()) {
                                            System.out.println("Insert new quantity of product:");
                                            float newQuantity = (float) read.readDouble();
                                            item.setQuantity(newQuantity);
                                            inventory.updateItems();
                                            MainMenu.getMain();
                                            break;
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
                                            break;
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
                                            break;
                                        }
                                    }
                                    break;
                                }
                                default: {
                                    MainMenu.getMain();
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    break;
                }
                default: {
                    MainMenu.getMain();
                    break;
                }
            }

        }
    }
}
