package shift.mceconomy.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import shift.mceconomy.MCEconomy;

@Mod.EventBusSubscriber(modid = MCEconomy.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MCEDataGenerators {

    private MCEDataGenerators() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gen.addProvider(event.includeServer(), new MCEVanillaPurchaseProvider(gen));

    }

}
