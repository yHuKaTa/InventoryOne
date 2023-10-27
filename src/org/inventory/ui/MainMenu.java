package org.inventory.ui;

import org.inventory.data.inventoryone.InventoryOne;
import org.inventory.models.users.Admin;
import org.inventory.models.users.Customer;
import org.inventory.models.users.User;
import org.inventory.util.ReadFromConsole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

public class MainMenu {
    private static MainMenu main;
    private final InventoryOne inventory;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private static User user;
    private static ReadFromConsole read;

    private MainMenu() {
        inventory = InventoryOne.getInventory();
        read = ReadFromConsole.getInstance();
        while (Objects.isNull(user)) {
            user = getUser();
        }
    }

    public static void getMain() {
        if (Objects.isNull(main)) {
            main = new MainMenu();
            mainMenu();
        } else {
            mainMenu();
        }
    }

    private User login(String userName, String password) {
        User temp = null;
        for (User user : inventory.getUsers()) {
            if (user.getUserName().equals(userName)) {
                if (encoder.matches(password, user.getPassword())) {
                    temp = user;
                }
            }
        }
        if (Objects.isNull(temp)) {
            System.out.println("Invalid username or password!");
        }
        return temp;
    }

    private User getUser() {
        User user = null;
        System.out.println("Welcome to E-Commerce!");
        System.out.println("For login insert 1,");
        System.out.println("To register new user insert 2,");
        System.out.println("To exit the program insert every other digit.");
        int command = read.readInteger();
        if (command == 1) {
            System.out.println("Insert username:");
            String userName = read.readString();
            System.out.println("Insert password:");
            String password = read.readString();
            user = login(userName, password);
        } else if (command == 2) {
            System.out.println("Insert username:");
            String userName = read.readString();
            System.out.println("Insert password:");
            String password = read.readString();
            System.out.println("Insert phoneNumber:");
            String phoneNumber = read.readString();
            user = new Customer(userName, encoder.encode(password), phoneNumber);
            inventory.getUsers().add(user);
            inventory.updateUsers();
        } else {
            System.exit(0);
        }
        return user;
    }

    private static void mainMenu() {
        System.out.printf("Hello, %s!\n", user.getUserName());
        System.out.println("Main menu:");
        System.out.println("1) Buy items");
        System.out.println("2) View cart");
        System.out.println("3) View orders");
        System.out.println("4) Modify profile");
        if (user instanceof Admin) {
            System.out.println("5) View users list");
            System.out.println("6) View items list");
            System.out.println("7) Logout");
        } else {
            System.out.println("5) Logout");
        }
        int command = read.readInteger();
        if (user instanceof Admin) {
            while (command < 1 || command > 7) {
                System.out.println("Invalid command!");
                command = read.readInteger();
            }
            switch (command) {
                case 1: {
                    BuyMenu.getBuyMenu(user);
                    break;
                }
                case 2: {
                    ViewCart.getViewCartMenu(user);
                    break;
                }
                case 3: {
                    ViewOrders.getViewOrdersMenu(user);
                    break;
                }
                case 4: {

                    break;
                }
                case 5: {

                    break;
                }
                case 6: {

                    break;
                }
                case 7: {
                    user = main.getUser();
                    getMain();
                    break;
                }
            }
        } else {
            while (command < 1 || command > 5) {
                System.out.println("Invalid command!");
                command = read.readInteger();
            }
            switch (command) {
                case 1: {
                    BuyMenu.getBuyMenu(user);
                    break;
                }
                case 2: {
                    ViewCart.getViewCartMenu(user);
                    break;
                }
                case 3: {
                    ViewOrders.getViewOrdersMenu(user);
                    break;
                }
                case 4: {

                    break;
                }
                case 5: {
                    user = main.getUser();
                    getMain();
                    break;
                }
            }
        }
    }
}
