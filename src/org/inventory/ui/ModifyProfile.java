package org.inventory.ui;

import org.inventory.data.inventoryone.InventoryOne;
import org.inventory.models.users.User;
import org.inventory.util.ReadFromConsole;

import java.util.Objects;

public class ModifyProfile {
    private static ModifyProfile profile;
    private InventoryOne inventory;
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

    }
}
