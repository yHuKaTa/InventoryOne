package org.inventory.ui;

import org.inventory.data.inventoryone.InventoryOne;
import org.inventory.models.orders.Order;
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
                    case 1 : {
                        viewInfo(searchedUser);
                        getUsers();
                        return;
                    }
                    case 2 : {
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
                    case 3 : {
                        iterator.remove();
                        getUsers();
                        return;
                    }
                    default : {
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
                System.out.printf("%d) %s date: %s total price: %f status: %s", num, order.getId(), order.getDate(), order.getTotalPrice(), order.getStatus());
                num++;
            }
            System.out.println("Insert order ID:");
            String id = read.readString();
            for (Order order : user.getOrders()) {
                if (order.getId().equals(id)) {

                    break;
                }
            }
        } else if (choice == 2) {

        }
    }
}
