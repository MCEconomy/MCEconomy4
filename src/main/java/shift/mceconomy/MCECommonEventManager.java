package shift.mceconomy;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import shift.mceconomy.player.MPEntityProperty;
import shift.mceconomy.player.MPManager;

public class MCECommonEventManager {

    @SubscribeEvent
    public void onEntityConstructing(AttachCapabilitiesEvent<Entity> event) {

        if (event.getObject() instanceof Player player) {

            event.addCapability(MPManager.r, new MPEntityProperty(player));

        }

    }

    @SubscribeEvent
    /* ワールドに入った時に呼ばれるイベント。 */
    public void onEntityJoinWorld(EntityJoinLevelEvent event) {

        if (!event.getEntity().getLevel().isClientSide() && event.getEntity() instanceof ServerPlayer) {

            MPManager.loadProxyData((ServerPlayer) event.getEntity());

        }

    }

    @SubscribeEvent
    /* ログインした時に呼ばれるイベント。 */
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {

        if (!event.getEntity().getLevel().isClientSide()) {

            MPManager.loadProxyData((ServerPlayer) event.getEntity());

        }
    }


    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        // プレイヤーがディメンション間を移動したときの処理

        if (!event.getEntity().getLevel().isClientSide()) {
            MPManager.loadProxyData((ServerPlayer) event.getEntity());
        }

    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        // プレイヤーがリスポーンした時の処理

        if (!event.getEntity().getLevel().isClientSide()) {

            MPManager.loadProxyData((ServerPlayer) event.getEntity());

        }

    }


}
