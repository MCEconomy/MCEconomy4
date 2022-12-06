package shift.mceconomy.player;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.network.NetworkDirection;
import shift.mceconomy.MCEconomy;
import shift.mceconomy.config.MCEConfig;
import shift.mceconomy.packet.PacketHandler;
import shift.mceconomy.packet.PlayerMpPacket;


public class MPManager {//} implements IMPManager {

    private static final MPManager instance = new MPManager();

    public static final ResourceLocation r = new ResourceLocation(MCEconomy.MOD_ID, "mp_entity_property");

    static Capability<MPEntityProperty> MP_CAPABILITY = CapabilityMPHandler.MP_HANDLER_CAPABILITY;

    private MPManager() {

    }

    public static MPManager getInstance() {
        return instance;
    }

    public static MPEntityProperty getMPEntityProperty(Player p) {

        if (p == null) return new MPEntityProperty();

        return p.getCapability(MP_CAPABILITY, null).orElse(null);

    }

    //@Override
    public int addPlayerMP(Player playerEntity, int amount, boolean simulation) {

        if (amount < 0 || getMPEntityProperty(playerEntity) == null) {
            return 0;
        }

        if (playerEntity instanceof ServerPlayer && !playerEntity.getLevel().isClientSide()) {

            ServerPlayer serverPlayer = (ServerPlayer) playerEntity;

            MPEntityProperty p = getMPEntityProperty(playerEntity);

            int add = 0;
            if (amount + p.getMp() > MCEConfig.maxMp) {

                add = MCEConfig.maxMp - p.getMp();
                if (!simulation) p.setMp(MCEConfig.maxMp);

            } else {

                add = amount;
                if (!simulation) p.setMp(p.getMp() + amount);

            }

            sendPacket(serverPlayer);

            return add;

        }

        return 0;

    }

    //@Override
    public int reducePlayerMP(Player playerEntity, int amount, boolean simulation) {

        if (amount < 0 || getMPEntityProperty(playerEntity) == null) {
            return 0;
        }

        if (playerEntity instanceof ServerPlayer && !playerEntity.getLevel().isClientSide()) {

            ServerPlayer serverPlayer = (ServerPlayer) playerEntity;

            MPEntityProperty p = getMPEntityProperty(playerEntity);

            int reduce = 0;

            if (getPlayerMP(playerEntity) >= amount) {

                reduce = amount;

                if (!simulation) setPlayerMP(serverPlayer, getPlayerMP(playerEntity) - reduce);

            } else {

                reduce = getPlayerMP(playerEntity);

                if (!simulation) setPlayerMP(serverPlayer, 0);

            }

            sendPacket(serverPlayer);

            return reduce;

        }

        return 0;
    }

    //Shop用の裏ルート
    public int reduceShopPlayerMP(Player player, int amount, boolean simulation) {

        if (amount < 0 || getMPEntityProperty(player) == null) {
            return 0;
        }

        if (player instanceof Player) {

            MPEntityProperty p = getMPEntityProperty(player);

            int reduce = 0;

            if (getPlayerMP(player) >= amount) {

                reduce = amount;

                if (!simulation)
                    getMPEntityProperty(player).setMp(Math.max(0, Math.min(getPlayerMP(player) - reduce, MCEConfig.maxMp)));

            } else {

                reduce = getPlayerMP(player);

                if (!simulation) getMPEntityProperty(player).setMp(Math.max(0, Math.min(0, MCEConfig.maxMp)));

            }

            //sendPacket(player);

            return reduce;

        }

        return 0;

    }

    //@Override
    public void setPlayerMP(ServerPlayer serverPlayer, int amount) {
        getMPEntityProperty(serverPlayer).setMp(Math.max(0, Math.min(amount, MCEConfig.maxMp)));
        sendPacket(serverPlayer);
    }

    //@Override
    public int getPlayerMP(Player Player) {
        return getMPEntityProperty(Player).getMp();
    }

    public static void loadProxyData(ServerPlayer player) {
        //TODO: いい感じに
        PacketHandler.INSTANCE.sendTo(PlayerMpPacket.createPlayerMpPacket(player, true), player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
    }

    /**
     * クライアントにデータを飛ばす
     *
     * @param player 飛ばす先のプレイヤー
     */
    public static void sendPacket(ServerPlayer player) {

        if (player instanceof FakePlayer) return;

        PacketHandler.INSTANCE.sendTo(PlayerMpPacket.createPlayerMpPacket(player), player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);

    }

}
