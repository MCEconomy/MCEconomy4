package shift.mceconomy.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;
import shift.mceconomy.gui.ShopMenu;

import java.util.Objects;
import java.util.function.Supplier;

public class ShopButtonPacket {

    private final int currentRecipeIndex;

    private ShopButtonPacket(int currentRecipeIndex) {
        this.currentRecipeIndex = currentRecipeIndex;
    }

    public static ShopButtonPacket createShopButtonPacket(int currentRecipeIndex) {
        return new ShopButtonPacket(currentRecipeIndex);
    }

    public int getCurrentRecipeIndex() {
        return currentRecipeIndex;
    }

    public static ShopButtonPacket decode(FriendlyByteBuf buf) {
        return new ShopButtonPacket(buf.readInt());
    }

    public static void encode(ShopButtonPacket shopButtonPacket, FriendlyByteBuf buf) {
        buf.writeInt(shopButtonPacket.currentRecipeIndex);
    }

    public static void handle(ShopButtonPacket shopButtonPacket, Supplier<NetworkEvent.Context> context) {

        //正常なPacketの設定
        context.get().enqueueWork(() -> {

            AbstractContainerMenu containerMenu = Objects.requireNonNull(context.get().getSender()).containerMenu;

            if (containerMenu instanceof ShopMenu shopMenu) {
                shopMenu.setCurrentRecipeIndex(shopButtonPacket.getCurrentRecipeIndex());
            }
        });
        context.get().setPacketHandled(true);

    }

}
