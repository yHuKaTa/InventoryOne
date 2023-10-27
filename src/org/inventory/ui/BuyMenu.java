package org.inventory.ui;

import org.inventory.data.inventoryone.InventoryOne;
import org.inventory.models.items.Item;
import org.inventory.models.users.User;
import org.inventory.util.ReadFromConsole;

import java.util.Objects;

public class BuyMenu {
    private static BuyMenu buyMenu;
    private InventoryOne inventory;
    private User user;
    private final ReadFromConsole read;

    private BuyMenu(User user) {
        this.inventory = InventoryOne.getInventory();
        this.read = ReadFromConsole.getInstance();
        this.user = user;

    }

    private void setUser(User user) {
        this.user = user;
    }

    public static void getBuyMenu(User user) {
        if (Objects.isNull(buyMenu)) {
            buyMenu = new BuyMenu(user);
        } else {
            buyMenu.setUser(user);
        }
        buyMenu.getItems();
    }

    private void getItems() {
        if (Objects.isNull(inventory.getItems()) || inventory.getItems().isEmpty()) {
            System.out.println("Sorry! There aren't items in warehouse!");
            MainMenu.getMain();
        } else {
            int num = 1;
            System.out.println("Select item ID to buy it:");
            for (Item item : inventory.getItems()) {
                System.out.printf("%d) %s ID:%d quantity:%f price:%f\n", num, item.getName(), item.getId(), item.getQuantity(), item.getPrice());
            }
            System.out.println("To exit buy menu type exit.");
            String wish = read.readString();
            while (wish.isBlank() || wish.isEmpty()) {
                System.out.println("Invalid selection! Insert valid item ID or exit buy menu with sentence \"exit\".");
                wish = read.readString();
            }
            if (wish.equalsIgnoreCase("exit")) {
                MainMenu.getMain();
            } else {
                Item wished = null;
                while (Objects.isNull(wished)) {
                    while (!wish.matches("[0-9]+")) {
                        System.out.println("Please provide valid ID with digits!");
                        wish = read.readString();
                    }
                    for (Item item : inventory.getItems()) {
                        if (item.getId() == Long.parseLong(wish)) {
                            System.out.println("Insert quantity:");
                            float qty = (float) read.readDouble();
                            while (qty > item.getQuantity()) {
                                System.out.printf("Insufficient quantity! Maximum quantity is %f\n", item.getQuantity());
                                qty = (float) read.readDouble();
                            }
                            wished = item;
                            wished.setQuantity(qty);
//                            item.setQuantity(item.getQuantity() - qty);
                            break;
                        }
                    }
                    if (Objects.isNull(wished)) {
                        System.out.println("Please provide valid item ID or exit buy menu with sentence \"exit\".");
                        wish = read.readString();
                    }
                    if (wish.equalsIgnoreCase("exit")) {
                        MainMenu.getMain();
                        return;
                    }
                }
                user.addToCart(wished);
                inventory.updateUsers();
                inventory.updateHistory();
                getItems();
            }
        }
    }
}
