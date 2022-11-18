package shift.mceconomy.config;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import shift.mceconomy.MCEconomy;

/**
 * コンフィグクラス
 */
public class MCEConfig {

    public static boolean enableMpHud;

    public static int maxMp = 1000000;

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MCEconomy.MOD_ID)
    public static class EventHandler {

        @SubscribeEvent
        public static void onModConfigEvent(final ModConfigEvent event) {

            final ModConfig config = event.getConfig();

            // Configファイルの再生成
            if (config.getSpec() == ConfigHolder.CLIENT_SPEC) {
                ConfigHelper.bakeClient(config);
            }
            if (config.getSpec() == ConfigHolder.SERVER_SPEC) {
                ConfigHelper.bakeServer(config);
            }
        }

    }

}


