package shift.mceconomy.config;

import net.minecraftforge.common.ForgeConfigSpec;
import shift.mceconomy.MCEconomy;

/**
 * サーバー側のコンフィグ
 */
public class ServerConfig {

    final ForgeConfigSpec.IntValue maxMp;

    ServerConfig(final ForgeConfigSpec.Builder builder) {

        builder.push("general");
        maxMp = (ForgeConfigSpec.IntValue) builder
                .comment("Max Mp")
                .translation(MCEconomy.MOD_ID + ".config.server.maxMp")
                .defineInRange("maxMp", 999999999, 1, 2147483647);
        builder.pop();
    }

}
