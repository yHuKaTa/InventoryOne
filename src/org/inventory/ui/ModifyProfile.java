package org.inventory.ui;

import org.inventory.data.inventoryone.InventoryOne;
import org.inventory.models.users.User;
import org.inventory.util.ReadFromConsole;

import java.util.Objects;

public class ModifyProfile {
    private static ModifyProfile profile;
    private final InventoryOne inventory;
    private User user;
    private final ReadFromConsole read;

    private ModifyProfile(User user) {
        this.inventory = InventoryOne.getInventory();
        this.read = ReadFromConsole.getInstance();
        this.user = user;
    }

    private void setUser(User user) {
        this.user = user;
    }

    public static void getModifyProfileMenu(User user) {
        if (Objects.isNull(profile)) {
            profile = new ModifyProfile(user);
        } else {
            profile.setUser(user);
        }
        profile.getMenu();
    }

    private void getMenu() {
        System.out.println("To change password insert 1");
        System.out.println("To change phone number insert 2");
        System.out.println("To return to main menu insert other else");
        int choice = read.readInteger();
        switch (choice) {
            case 1: {
                if (user.changePassword()) {
                    inventory.updateUsers();
                }
                getMenu();
                break;
            }
            case 2: {
                user.changePhoneNumber();
                inventory.updateUsers();
                getMenu();
                break;
            }
            default: {
                MainMenu.getMain();
                break;
            }
        }
    }
}
