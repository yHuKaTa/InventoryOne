package org.inventory.ui;

import org.inventory.data.inventoryone.InventoryOne;
import org.inventory.models.items.Item;
import org.inventory.models.items.appearances.Discountable;
import org.inventory.models.orders.Order;
import org.inventory.models.orders.Status;
import org.inventory.models.users.Admin;
import org.inventory.models.users.Customer;
import org.inventory.models.users.User;
import org.inventory.util.ReadFromConsole;

import java.util.Iterator;
import java.util.Objects;

public class ViewUsersList {
    private static ViewUsersList usersList;
    private final InventoryOne inventory;
    private User user;
    private final ReadFromConsole read;

    private ViewUsersList(User user) {
        this.inventory = InventoryOne.getInventory();
        this.read = ReadFromConsole.getInstance();
        this.user = user;
    }

    public static void getViewItemsList(User user) {
        if (user instanceof Customer) {
            System.out.println("You not have permission for this operations!");
            return;
        } else {
            if (Objects.isNull(usersList)) {
                usersList = new ViewUsersList(user);
            } else {
                usersList.setUser(user);
            }
            usersList.getUsers();
        }
    }

    private void setUser(User user) {
        this.user = user;
    }

    private void getUsers() {
        int num = 1;
        for (User users : inventory.getUsers()) {
            System.out.printf("%d) %s type: %s ID: %s\n", num, users.getUserName(), users.getType(), users.getUuid());
            num++;
        }
        System.out.println("Insert user's ID or type exit to return into main menu:");
        String id = read.readString();
        if (id.equalsIgnoreCase("exit") || id.isEmpty()) {
            MainMenu.getMain();
            return;
        }
        boolean isFound = false;
        Iterator<User> iterator = inventory.getUsers().iterator();
        while (iterator.hasNext()) {
            User searchedUser = iterator.next();
            if (searchedUser.getUuid().equals(id)) {
                isFound = true;
                System.out.println("To view user's information insert 1");
                System.out.println("To change user's type insert 2");
                System.out.println("To remove user insert 3");
                System.out.println("To exit insert every other digit");
                int choice = read.readInteger();
                switch (choice) {
                    case 1: {
                        viewInfo(searchedUser);
                        getUsers();
                        return;
                    }
                    case 2: {
                        System.out.println("For Customer insert 1");
                        System.out.println("For Admin insert 2");
                        System.out.println("To exit insert every other digit");
                        choice = read.readInteger();
                        if (choice == 1) {
                            User temp = new Customer(searchedUser);
                            iterator.remove();
                            inventory.getUsers().add(temp);
                            inventory.updateUsers();
                            getUsers();
                            return;
                        } else if (choice == 2) {
                            User temp = new Admin(searchedUser);
                            iterator.remove();
                            inventory.getUsers().add(temp);
                            inventory.updateUsers();
                            getUsers();
                            return;
                        } else {
                            getUsers();
                            return;
                        }
                    }
                    case 3: {
                        iterator.remove();
                        getUsers();
                        return;
                    }
                    default: {
                        getUsers();
                        return;
                    }
                }
            }
        }
        System.out.println("Insert valid user ID!");
        getUsers();
        return;
    }

    private void viewInfo(User user) {
        int choice = read.readInteger();
        System.out.println("To view user's orders insert 1");
        System.out.println("To view user's cart insert 2");
        System.out.println("To exit insert every other digit");
        if (choice == 1) {
            if (user.getOrders().isEmpty()) {
                System.out.println("There aren't any orders!");
                return;
            }
            int num = 1;
            for (Order order : user.getOrders()) {
                System.out.printf("%d) %s date: %s total price: %f status: %s\n", num, order.getId(), order.getDate(), order.getTotalPrice(), order.getStatus());
                num++;
            }
            System.out.println("Insert order ID:");
            String id = read.readString();
            boolean isFound = false;
            for (Order order : user.getOrders()) {
                if (order.getId().equals(id)) {
                    isFound = true;
                    System.out.println("To apply discount insert 1");
                    System.out.println("To cancel order insert 2");
                    System.out.println("To finish order insert 3");
                    System.out.println("To exit insert every other digit");
                    choice = read.readInteger();
                    switch (choice) {
                        case 1 : {
                            boolean noDiscount = true;
                            int numItem = 1;
                            for (Item item : order.getItems()) {
                                if (item instanceof Discountable) {
                                    noDiscount = false;
                                    System.out.printf("%d) %s total price: %f\n", numItem, item.getName(), item.getTotalPrice(item.getQuantity()));
                                    System.out.println("Insert discount to item:");
                                    int percent = read.readInteger();
                                    if (percent > 0) {
                                        ((Discountable) item).applyDiscount(percent);
                                    }
                                    numItem++;
                                }
                            }
                            if (noDiscount) {
                                System.out.println("No discountable items in order!");
                            }
                            break;
                        }
                        case 2 : {
                            if (order.getStatus().equals(Status.ACTIVE) || order.getStatus().equals(Status.CANCELED)) {
                                order.changeStatus(Status.CANCELED);
                            } else {
                                System.out.println("Can't change order status! Order is finished.");
                            }
                            break;
                        }
                        case 3 : {
                            if (order.getStatus().equals(Status.ACTIVE) || order.getStatus().equals(Status.FINISHED)) {
                                order.changeStatus(Status.FINISHED);
                            } else {
                                System.out.println("Can't finish canceled order!");
                            }
                            break;
                        }
                        default : {
                            return;
                        }
                    }
                    break;
                }
            }
            if (!isFound) {
                System.out.println("Provide valid order ID!");
                return;
            }
        } else if (choice == 2) {
            int num = 1;
            for (Item item : user.getCart().items()) {
                System.out.printf("%d) %s ID: %d quantity: %f total price: %f\n", num, item.getName(), item.getId(), item.getQuantity(), item.getTotalPrice(item.getQuantity()));
                num++;
            }
            System.out.println("Insert item ID to manipulate it:");
            long id = read.readLong();
            boolean isFound = false;
            Iterator<Item> iterator = user.getCart().items().iterator();
            while (iterator.hasNext()) {
                Item item = iterator.next();
                if (item.getId() == id) {
                    isFound = true;
                    System.out.println("To change quantity of item insert 1");
                    System.out.println("To remove item from cart 2");
                    if (item instanceof Discountable) {
                        System.out.println("To apply discount insert 3");
                    }
                    System.out.println("To exit insert every other digit");
                    choice = read.readInteger();
                    if (choice == 1) {
                        System.out.println("Insert new quantity of item:");
                        float quantity = (float) read.readDouble();
                        for (Item originalItem : inventory.getItems()) {
                            if (item.getId() == originalItem.getId()) {
                                while (originalItem.getQuantity() < quantity) {
                                    System.out.printf("Insert valid quantity of item! Max quantity is %f:\n", originalItem.getQuantity());
                                    quantity = (float) read.readDouble();
                                }
                                break;
                            }
                        }
                        item.setQuantity(quantity);
                    } else if (choice == 2) {
                        iterator.remove();
                    } else if (choice == 3 && item instanceof Discountable) {
                        System.out.println("Insert discount percents:");
                        ((Discountable) item).applyDiscount(read.readInteger());
                    } else {
                        return;
                    }
                }
            }
            if (!isFound) {
                System.out.println("Provide valid item ID!");
                return;
            }
        }
    }
}
