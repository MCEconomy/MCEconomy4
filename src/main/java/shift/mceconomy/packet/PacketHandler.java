package shift.mceconomy.packet;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import shift.mceconomy.MCEconomy;

import java.util.Objects;


public class PacketHandler {

    /*
     * MOD固有のSimpleNetworkWrapperを取得。
     */
    public static final SimpleChannel INSTANCE = NetworkRegistry
            .ChannelBuilder.named(new ResourceLocation(MCEconomy.MOD_ID, "packet")).
            clientAcceptedVersions(s -> Objects.equals(s, "1")).
            serverAcceptedVersions(s -> Objects.equals(s, "1")).
            networkProtocolVersion(() -> "1").simpleChannel();

    public static void init(FMLCommonSetupEvent event) {

        /*
         * Messageクラスの登録。
         * 第一引数：登録番号
         * 第二引数：送るMessageクラス
         * 第三引数：Messageクラス -> NBT
         * 第四引数：NBT -> Messageクラス
         * 第五引数：Messageを受け取った時の処理
         */
        INSTANCE.registerMessage(0, PlayerMpPacket.class, PlayerMpPacket::encode, PlayerMpPacket::decode, PlayerMpPacket::handle);

        //INSTANCE.registerMessage(MessagePlayerProperties.class, MessagePlayerProperties.class, 0, Side.CLIENT);
        //INSTANCE.registerMessage(0,MessagePlayerProperties.class);
//		INSTANCE.registerMessage(ShopButtonHandler.class,PacketShopButton.class, 1, Side.SERVER);
//		INSTANCE.registerMessage(MessageGuiId.class,PacketGuiId.class, 2, Side.SERVER);
//
//		INSTANCE.registerMessage(PlayerLoginHandler.class,PacketPlayerLogin.class, 3, Side.SERVER);

    }

}
