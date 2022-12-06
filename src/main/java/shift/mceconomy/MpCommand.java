package shift.mceconomy;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.NetworkHooks;
import shift.mceconomy.config.MCEConfig;
import shift.mceconomy.gui.ShopMenu;
import shift.mceconomy.player.MPManager;

import java.util.Collection;
import java.util.Collections;

/**
 * コマンドを登録するクラス
 */
public class MpCommand {

    @SubscribeEvent
    public void init(ServerStartingEvent event) {

    }

    @SubscribeEvent
    public void onCommandsRegister(RegisterCommandsEvent event) {

        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        // mp
        LiteralArgumentBuilder<CommandSourceStack> literalArgumentBuilder = Commands.literal("mp").requires((source) -> {
            return source.hasPermission(Commands.LEVEL_GAMEMASTERS);
        });

        literalArgumentBuilder
                .then(Commands.literal("set")
                        .then(Commands.argument("mp", IntegerArgumentType.integer(0, MCEConfig.maxMp)).executes((sourceCommandContext) -> {
                            return setMp(sourceCommandContext, Collections.singleton(sourceCommandContext.getSource().getPlayer()), IntegerArgumentType.getInteger(sourceCommandContext, "mp"));
                        }))
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("mp", IntegerArgumentType.integer(0, MCEConfig.maxMp)).executes((sourceCommandContext) -> {
                                    return setMp(sourceCommandContext, EntityArgument.getPlayers(sourceCommandContext, "target"), IntegerArgumentType.getInteger(sourceCommandContext, "mp"));
                                }))
                        )
                ).then(Commands.literal("add")
                        .then(Commands.argument("mp", IntegerArgumentType.integer(0, MCEConfig.maxMp)).executes((sourceCommandContext) -> {
                            return addMp(sourceCommandContext, Collections.singleton(sourceCommandContext.getSource().getPlayer()), IntegerArgumentType.getInteger(sourceCommandContext, "mp"));
                        }))
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("mp", IntegerArgumentType.integer(0, MCEConfig.maxMp)).executes((sourceCommandContext) -> {
                                    return addMp(sourceCommandContext, EntityArgument.getPlayers(sourceCommandContext, "target"), IntegerArgumentType.getInteger(sourceCommandContext, "mp"));
                                }))
                        )
                );

        dispatcher.register(literalArgumentBuilder);

        //shop
        LiteralArgumentBuilder<CommandSourceStack> shopArgumentBuilder = Commands.literal("shop").requires((source) -> {
            return source.hasPermission(Commands.LEVEL_GAMEMASTERS);
        });

        shopArgumentBuilder
                .then(Commands.literal("open")
                        .then(Commands.argument("id", IntegerArgumentType.integer(0, MCEConfig.maxMp)).executes((sourceCommandContext) -> {
                            return openShop(sourceCommandContext, Collections.singleton(sourceCommandContext.getSource().getPlayer()), IntegerArgumentType.getInteger(sourceCommandContext, "id"));
                        }))
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("id", IntegerArgumentType.integer(0, MCEConfig.maxMp)).executes((sourceCommandContext) -> {
                                    return openShop(sourceCommandContext, EntityArgument.getPlayers(sourceCommandContext, "target"), IntegerArgumentType.getInteger(sourceCommandContext, "id"));
                                }))
                        )
                );

        dispatcher.register(shopArgumentBuilder);

    }

    private static int setMp(CommandContext<CommandSourceStack> source, Collection<ServerPlayer> players, int mp) {
        int i = 0;

        for (ServerPlayer serverPlayerEntity : players) {

            MPManager.getInstance().setPlayerMP(serverPlayerEntity, mp);
            sendMpFeedback(source.getSource(), serverPlayerEntity, mp);
            ++i;

        }

        return i;
    }

    private static int addMp(CommandContext<CommandSourceStack> source, Collection<ServerPlayer> players, int mp) {
        int i = 0;

        for (ServerPlayer serverPlayerEntity : players) {

            MPManager.getInstance().addPlayerMP(serverPlayerEntity, mp, false);
            sendMpFeedback(source.getSource(), serverPlayerEntity, mp);
            ++i;

        }

        return i;
    }

    private static int openShop(CommandContext<CommandSourceStack> source, Collection<ServerPlayer> players, int id) {
        int i = 0;

        for (ServerPlayer serverPlayerEntity : players) {

            NetworkHooks.openScreen(serverPlayerEntity, new SimpleMenuProvider(
                    (containerId, playerInventory, player) -> new ShopMenu(containerId, playerInventory, null),
                    Component.translatable("シンプルショップ")
            ));
            ++i;

        }

        return i;
    }

    private static void sendMpFeedback(CommandSourceStack source, ServerPlayer player, int mp) {
        MutableComponent itextComponent = Component.translatable("commands.mp.set", mp);

        if (source.isPlayer()) {
            player.sendSystemMessage(Component.translatable("commands.mp.success.self", itextComponent), true);
        }

    }

}
