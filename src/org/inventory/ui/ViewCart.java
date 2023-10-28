package org.inventory.ui;

import org.inventory.data.inventoryone.InventoryOne;
import org.inventory.models.items.Item;
import org.inventory.models.users.User;
import org.inventory.util.ReadFromConsole;

import java.util.*;

public class ViewCart {
    private static ViewCart cartMenu;
    private final InventoryOne inventory;
    private User user;
    private final ReadFromConsole read;

    private ViewCart(User user) {
        this.inventory = InventoryOne.getInventory();
        this.read = ReadFromConsole.getInstance();
        this.user = user;
    }

    private void setUser(User user) {
        this.user = user;
    }

    public static void getViewCartMenu(User user) {
        if (Objects.isNull(cartMenu)) {
            cartMenu = new ViewCart(user);
        } else {
            cartMenu.setUser(user);
        }
        cartMenu.getCart();
    }

    private void getCart() {
        if (Objects.isNull(user.getCart()) || user.getCart().items().isEmpty()) {
            System.out.println("Your cart is empty!");
            MainMenu.getMain();
        } else {
            user.viewCart();
            System.out.println("To generate order with items from cart insert 1");
            System.out.println("To remove item from cart insert 2");
            System.out.println("To change quantity of item from cart insert 3");
            System.out.println("To return to main menu insert other else");
            int choice = read.readInteger();
            switch (choice) {
                case 1: {
                    for (Item item : inventory.getItems()) {
                        for (Item wishedItem : user.getCart().items()) {
                            if (item.getId() == wishedItem.getId()) {
                                item.setQuantity(item.getQuantity() - wishedItem.getQuantity());
                            }
                        }
                    }
                    user.generateOrder();
                    System.out.printf("Successfully generated new order! Your order ID is %s\n", user.getOrders().getLast().getId());
                    if (inventory.getHistory().containsKey(user.getUuid())) {
                        inventory.getHistory().get(user.getUuid()).add(user.getOrders().getLast());
                    } else {
                        inventory.getHistory().putIfAbsent(user.getUuid(), new LinkedList<>());
                        inventory.getHistory().get(user.getUuid()).add(user.getOrders().getLast());
                    }
                    inventory.updateItems();
                    inventory.updateUsers();
                    inventory.updateHistory();
                    MainMenu.getMain();
                    break;
                }
                case 2: {
                    System.out.println("Provide ID of item to remove:");
                    long id = read.readLong();
                    if (user.removeFromCart(id)) {
                        System.out.println("Successfully removed item from cart!");
                    }
                    inventory.updateUsers();
                    getCart();
                    break;
                }
                case 3: {
                    System.out.println("Provide ID of item to change quantity:");
                    long id = read.readLong();
                    System.out.println("Insert new quantity:");
                    float quantity = (float) read.readDouble();
                    if (user.reduceQuantity(id, quantity)) {
                        System.out.println("Successfully changed quantity!");
                    }
                    inventory.updateUsers();
                    getCart();
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
