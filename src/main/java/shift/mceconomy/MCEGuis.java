package shift.mceconomy;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;
import shift.mceconomy.gui.ShopMenu;

public class MCEGuis {

    public static final RegistryObject<MenuType<ShopMenu>> SHOP_MENU = MCEconomy.MENU_TYPES.register("shop", () -> IForgeMenuType.create(ShopMenu::new));


    static void register() {
    }

}
