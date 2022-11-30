package shift.mceconomy;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import shift.mceconomy.api.money.CapabilityMoneyStorage;
import shift.mceconomy.config.ConfigHolder;
import shift.mceconomy.gui.MpHud;
import shift.mceconomy.gui.MpTooltip;
import shift.mceconomy.packet.PacketHandler;
import shift.mceconomy.player.CapabilityMPHandler;
import shift.mceconomy.proxy.ClientProxy;
import shift.mceconomy.proxy.CommonProxy;
import shift.mceconomy.proxy.IProxy;

@Mod(MCEconomy.MOD_ID)
public class MCEconomy {

    public static final String MOD_ID = "mceconomy4";

    public static final IProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);


    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = create(ForgeRegistries.RECIPE_TYPES);

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = create(ForgeRegistries.RECIPE_SERIALIZERS);

    public MCEconomy() {

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.register(new MpHud());
        modEventBus.register(new MCEClientEvents());
        MinecraftForge.EVENT_BUS.register(new MpCommand());
        MinecraftForge.EVENT_BUS.register(new MCECommonEventManager());

        MinecraftForge.EVENT_BUS.register(new CapabilityMoneyStorage());
        MinecraftForge.EVENT_BUS.register(new CapabilityMPHandler());
        MinecraftForge.EVENT_BUS.register(new MpTooltip());

        final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, ConfigHolder.SERVER_SPEC);

        //TODO : Forgeが未実装
//        modLoadingContext.registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (mc, screen) -> {
//            return new ConfigScreen();
//        });
//
//        StartupMessageManager.addModMessage("MCEconomy version " + "1.0.4");

        modEventBus.addListener(this::preInit);

        modEventBus.addListener(this::preInit);

        //各種登録
        RECIPE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MCERecipes.register();

    }


    public void preInit(FMLCommonSetupEvent evt) {
        PacketHandler.init(evt);
    }

    private static <T> DeferredRegister<T> create(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, MCEconomy.MOD_ID);
    }

}
