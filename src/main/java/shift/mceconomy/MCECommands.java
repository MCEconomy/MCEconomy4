package shift.mceconomy;

import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraftforge.registries.RegistryObject;

public class MCECommands {

    public static final RegistryObject<ArgumentTypeInfo<?, ?>> SHOP_ARGUMENT = MCEconomy.COMMAND_ARGUMENT_TYPES.register("shop", () ->
            ArgumentTypeInfos.registerByClass(ShopArgument.class,
                    SingletonArgumentInfo.contextFree(ShopArgument::shopArgument)));


    static void register() {
    }

}
