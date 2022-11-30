package shift.mceconomy;

import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import shift.mceconomy.gui.MpClientTooltipComponent;
import shift.mceconomy.gui.MpTooltipComponent;

public class MCEClientEvents {

    @SubscribeEvent
    public void onRegisterClientTooltipComponentFactoriesEvent(RegisterClientTooltipComponentFactoriesEvent event) {

        event.register(MpTooltipComponent.class, (tooltipComponent) -> {
            return new MpClientTooltipComponent(tooltipComponent.isNotForSale(), tooltipComponent.getPrice());
        });

    }

}
