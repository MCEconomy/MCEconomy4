package shift.mceconomy.config;

import net.minecraftforge.fml.config.ModConfig;

/**
 * コンフィグの値を実際に反映するクラス
 */
public final class ConfigHelper {

    private static ModConfig clientConfig;

    private static ModConfig serverConfig;

    public static void bakeClient(final ModConfig config) {
        clientConfig = config;

        MCEConfig.enableMpHud = ConfigHolder.CLIENT.enableMpHud.get();

    }

    public static void bakeServer(final ModConfig config) {
        serverConfig = config;

        MCEConfig.maxMp = ConfigHolder.SERVER.maxMp.get();

    }


}
