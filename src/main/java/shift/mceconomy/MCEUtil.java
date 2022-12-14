package shift.mceconomy;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryManager;
import shift.mceconomy.api.MCERegistries;
import shift.mceconomy.api.shop.IShop;

public class MCEUtil {

    public static IShop getShop(String shopId) {
        ForgeRegistry<IShop> registry = RegistryManager.ACTIVE.getRegistry(MCERegistries.Keys.SHOPS);
        IShop shop = registry.getValue(new ResourceLocation(shopId));
        return shop;
    }

}
