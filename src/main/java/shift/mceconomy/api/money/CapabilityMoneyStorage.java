package shift.mceconomy.api.money;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CapabilityMoneyStorage {

    public static Capability<IMoneyStorage> MONEY = CapabilityManager.get(new CapabilityToken<>() {
    });

    @SubscribeEvent
    public void onRegisterCapabilitiesEvent(RegisterCapabilitiesEvent event) {

        event.register(IMoneyStorage.class);
    }

}
