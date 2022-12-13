package shift.mceconomy;

import net.minecraftforge.registries.RegistryObject;
import shift.mceconomy.api.shop.IShop;
import shift.mceconomy.api.shop.ShopBase;

public class MCEShops {

    public static final RegistryObject<IShop> SIMPLE_SHOP = MCEconomy.SHOPS.register("simple", () -> new ShopBase("simple"));

    static void register() {
    }

}
