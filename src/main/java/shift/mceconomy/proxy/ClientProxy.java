package shift.mceconomy.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;


public class ClientProxy extends CommonProxy {

    private final Minecraft mc = Minecraft.getInstance();

    @Override
    public Player getPlayer() {
        return mc.player;
    }

}
