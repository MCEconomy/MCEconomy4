package shift.mceconomy;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.RegistryObject;
import shift.mceconomy.api.shop.IShop;
import shift.mceconomy.api.shop.ProductBase;
import shift.mceconomy.api.shop.ShopBase;

public class MCEShops {

    public static final RegistryObject<IShop> SIMPLE_SHOP = MCEconomy.SHOPS.register("simple", () -> new ShopBase("simple"));

    static void register() {
    }

    static void registerShopItem() {
        SIMPLE_SHOP.get().addProduct(new ProductBase(new ItemStack(Items.APPLE), 50));
        SIMPLE_SHOP.get().addProduct(new ProductBase(new ItemStack(Items.STICK), 10));
        SIMPLE_SHOP.get().addProduct(new ProductBase(new ItemStack(Items.DIAMOND), 10000));
    }

}
