package shift.mceconomy;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import shift.mceconomy.api.MCERegistries;
import shift.mceconomy.api.money.CapabilityMoneyStorage;
import shift.mceconomy.api.shop.IShop;
import shift.mceconomy.config.ConfigHolder;
import shift.mceconomy.gui.MpHud;
import shift.mceconomy.gui.MpTooltip;
import shift.mceconomy.gui.ShopContainerScreen;
import shift.mceconomy.packet.PacketHandler;
import shift.mceconomy.player.CapabilityMPHandler;
import shift.mceconomy.proxy.ClientProxy;
import shift.mceconomy.proxy.CommonProxy;
import shift.mceconomy.proxy.IProxy;

import java.util.function.Supplier;

@Mod(MCEconomy.MOD_ID)
public class MCEconomy {

    public static final String MOD_ID = "mceconomy4";

    public static final IProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);


    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = create(ForgeRegistries.RECIPE_TYPES);

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = create(ForgeRegistries.RECIPE_SERIALIZERS);

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = create(ForgeRegistries.MENU_TYPES);

    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENT_TYPES = create(ForgeRegistries.COMMAND_ARGUMENT_TYPES);

    //SHOP
    public static final DeferredRegister<IShop> DEFERRED_SHOPS = DeferredRegister.create(MCERegistries.Keys.SHOPS, MCEconomy.MOD_ID);

    public static final Supplier<IForgeRegistry<IShop>> SHOP_REGISTRY = DEFERRED_SHOPS.makeRegistry(RegistryBuilder::new);

    public static DeferredRegister<IShop> SHOPS = create(MCERegistries.Keys.SHOPS);
    
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

        modEventBus.addListener(this::clientSetup);


        //各種登録
        RECIPE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MCERecipes.register();

        MENU_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        MCEGuis.register();

        COMMAND_ARGUMENT_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        MCECommands.register();

        DEFERRED_SHOPS.register(FMLJavaModLoadingContext.get().getModEventBus());

        SHOPS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MCEShops.register();

    }


    public void preInit(FMLCommonSetupEvent evt) {
        PacketHandler.init(evt);
    }

    public void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(
                () -> MenuScreens.register(MCEGuis.SHOP_MENU.get(), ShopContainerScreen::new)
        );
    }

    private static <T> DeferredRegister<T> create(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, MCEconomy.MOD_ID);
    }

    private static <T> DeferredRegister<T> create(ResourceKey<? extends Registry<T>> key) {
        return DeferredRegister.createOptional(key, MCEconomy.MOD_ID);
    }

}
