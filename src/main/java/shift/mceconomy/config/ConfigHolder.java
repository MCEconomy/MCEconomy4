package shift.mceconomy.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Configの構成データを保持するクラス
 */
public class ConfigHolder {

    public static final ForgeConfigSpec CLIENT_SPEC;
    static final ClientConfig CLIENT;

    public static final ForgeConfigSpec SERVER_SPEC;
    static final ServerConfig SERVER;

    static {
        {
            final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
            CLIENT = specPair.getLeft();
            CLIENT_SPEC = specPair.getRight();

            final Pair<ServerConfig, ForgeConfigSpec> specPair2 = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
            SERVER = specPair2.getLeft();
            SERVER_SPEC = specPair2.getRight();
        }
    }

}
