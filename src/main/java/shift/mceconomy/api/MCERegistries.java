package shift.mceconomy.api;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import shift.mceconomy.api.shop.IShop;

public class MCERegistries {

    public static final class Keys {

        public static final ResourceKey<Registry<IShop>> SHOPS = ResourceKey.createRegistryKey(new ResourceLocation(MCEconomyAPI.MODID, "shop"));

    }
}
