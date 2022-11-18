package shift.mceconomy.packet;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import shift.mceconomy.MCEconomy;
import shift.mceconomy.player.MPEntityProperty;
import shift.mceconomy.player.MPManager;

import java.util.function.Supplier;

/**
 * Playerの所持MPを同期するクラス
 */
public class PlayerMpPacket {

    private CompoundTag data;

    private PlayerMpPacket(CompoundTag data) {
        this.data = data;
    }

    public static PlayerMpPacket createPlayerMpPacket(Player playerEntity) {
        return createPlayerMpPacket(playerEntity, false);
    }

    /**
     * ワールドに入ったタイミングなどで使う
     *
     * @param playerEntity 同期するプレイヤー
     * @param init         アニメーションフラグ
     * @return パケット
     */
    public static PlayerMpPacket createPlayerMpPacket(Player playerEntity, boolean init) {
        CompoundTag data = MPManager.getMPEntityProperty(playerEntity).serializeNBT();
        data.putBoolean(MPEntityProperty.INIT, init);
        return new PlayerMpPacket(data);
    }

    public static PlayerMpPacket decode(FriendlyByteBuf buf) {
        return new PlayerMpPacket(buf.readNbt());
    }

    public static void encode(PlayerMpPacket playerMpPacket, FriendlyByteBuf buf) {
        buf.writeNbt(playerMpPacket.data);
    }

    public static void handle(PlayerMpPacket playerMpPacket, Supplier<NetworkEvent.Context> context) {

        //正常なPacketの設定
        context.get().enqueueWork(() -> {
            MPManager.getMPEntityProperty(MCEconomy.proxy.getPlayer()).deserializeNBT(playerMpPacket.data);
        });
        context.get().setPacketHandled(true);


    }

}
