package org.inventory.ui;

import org.inventory.data.inventoryone.InventoryOne;
import org.inventory.models.items.Item;
import org.inventory.models.orders.Order;
import org.inventory.models.orders.Status;
import org.inventory.models.users.User;
import org.inventory.util.ReadFromConsole;

import java.util.Objects;

public class ViewOrders {
    private static ViewOrders orders;
    private final InventoryOne inventory;
    private User user;
    private final ReadFromConsole read;

    private ViewOrders(User user) {
        this.inventory = InventoryOne.getInventory();
        this.read = ReadFromConsole.getInstance();
        this.user = user;
    }

    private void setUser(User user) {
        this.user = user;
    }

    public static void getViewOrdersMenu(User user) {
        if (Objects.isNull(orders)) {
            orders = new ViewOrders(user);
        } else {
            orders.setUser(user);
        }
        orders.getOrders();
    }

    private void getOrders() {
        if (Objects.isNull(user.getOrders()) || user.getOrders().isEmpty()) {
            System.out.println("Your orders history is empty!");
            MainMenu.getMain();
        } else {
            user.viewHistory();
            System.out.println("To checkout order insert 1");
            System.out.println("To cancel order insert 2");
            System.out.println("To return to main menu insert other else");
            int choice = read.readInteger();
            switch (choice) {
                case 1 : {
                    System.out.println("Provide order ID:");
                    String id = read.readString();
                    boolean notFound = true;
                    for (Order order : user.getOrders()) {
                        if (order.getId().equals(id)) {
                            notFound = false;
                            if (order.getStatus().equals(Status.FINISHED)) {
                                System.out.println("Can not checkout order with provided ID! This order is completed.");
                                break;
                            }
                            if (order.getStatus().equals(Status.CANCELED)) {
                                System.out.println("Can not checkout order with provided ID! This order is canceled.");
                                break;
                            }
                            // We can implement pay service here
                            for (Order history : inventory.getHistory().get(user.getUuid())) {
                                if (history.getId().equals(order.getId())) {
                                    history.changeStatus(Status.FINISHED);
                                    break;
                                }
                            }
                            order.changeStatus(Status.FINISHED);
                            System.out.println("Your order have been successfully finished!");
                            inventory.updateUsers();
                            inventory.updateHistory();
                            break;
                        }
                    }
                    if (notFound) {
                        System.out.println("No such order with provided ID!");
                    }
                    MainMenu.getMain();
                    break;
                }
                case 2 : {
                    System.out.println("Provide order ID:");
                    String id = read.readString();
                    boolean notFound = true;
                    for (Order order : user.getOrders()) {
                        if (order.getId().equals(id)) {
                            notFound = false;
                            if (order.getStatus().equals(Status.FINISHED)) {
                                System.out.println("Can not cancel order with provided ID! This order is completed.");
                                break;
                            }
                            for (Item orderedItem : order.getItems()) {
                                for (Item item : inventory.getItems()) {
                                    if (item.getId() == orderedItem.getId()) {
                                        item.setQuantity(item.getQuantity() + orderedItem.getQuantity());
                                    }
                                }
                            }
                            for (Order history : inventory.getHistory().get(user.getUuid())) {
                                if (history.getId().equals(order.getId())) {
                                    history.changeStatus(Status.CANCELED);
                                    break;
                                }
                            }
                            order.changeStatus(Status.CANCELED);
                            System.out.println("Your order have been successfully canceled!");
                            inventory.updateItems();
                            inventory.updateUsers();
                            inventory.updateHistory();
                            break;
                        }
                    }
                    if (notFound) {
                        System.out.println("No such order with provided ID!");
                    }
                    MainMenu.getMain();
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
