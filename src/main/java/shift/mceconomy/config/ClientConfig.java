package shift.mceconomy.config;

import net.minecraftforge.common.ForgeConfigSpec;
import shift.mceconomy.MCEconomy;

/**
 * クライアント側のコンフィグ
 */
public class ClientConfig {

    final ForgeConfigSpec.BooleanValue enableMpHud;

    ClientConfig(final ForgeConfigSpec.Builder builder) {

        builder.push("general");
        enableMpHud = builder
                .comment("Allow HUD to show MP")
                .translation(MCEconomy.MOD_ID + ".config.client.enableMpHud")
                .define("enableMpHud", true);
        builder.pop();
    }


}
