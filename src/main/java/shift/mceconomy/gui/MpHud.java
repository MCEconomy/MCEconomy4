package shift.mceconomy.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import shift.mceconomy.MCEconomy;
import shift.mceconomy.player.MPEntityProperty;
import shift.mceconomy.player.MPManager;


/**
 * プレイヤーのHUDに所持MPを追加するクラス
 */
public class MpHud {

    public static final ResourceLocation icons = new ResourceLocation(MCEconomy.MOD_ID, "textures/gui/icons.png");

    private static Minecraft mc = Minecraft.getInstance();

    @OnlyIn(Dist.CLIENT)
    private static int count = 0;

    @SubscribeEvent
    public void onRegisterGuiOverlaysEvent(RegisterGuiOverlaysEvent event) {

        //MP追加のHUD
        event.registerAbove(VanillaGuiOverlay.FOOD_LEVEL.id(), "add_mp_level", (gui, poseStack, partialTick, screenWidth, screenHeight) -> {

            boolean isMounted = gui.getMinecraft().player.getVehicle() instanceof LivingEntity;
            if (!isMounted && !gui.getMinecraft().options.hideGui && gui.shouldDrawSurvivalElements()) {
                gui.setupOverlayRenderState(true, false);
                renderAddMoney(gui, screenWidth, screenHeight, poseStack);
            }

        });

        //MPのHUD
        event.registerAbove(VanillaGuiOverlay.FOOD_LEVEL.id(), "mp_level", (gui, poseStack, partialTick, screenWidth, screenHeight) -> {

            boolean isMounted = gui.getMinecraft().player.getVehicle() instanceof LivingEntity;
            if (!isMounted && !gui.getMinecraft().options.hideGui && gui.shouldDrawSurvivalElements()) {
                gui.setupOverlayRenderState(true, false);
                renderMoney(gui, screenWidth, screenHeight, poseStack);
            }

        });


    }

    //@SubscribeEvent
    public void onRenderGameOverlayEvent(RenderGuiOverlayEvent.Pre event) {

    }

    //@SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRenderGameOverlayEvent(RenderGuiOverlayEvent.Post event) {

    }

    public static void renderMoney(ForgeGui gui, int width, int height, PoseStack poseStack) {

        mc.getProfiler().push("money");

        RenderSystem.enableBlend();
        bind(icons);

        int left = width / 2 + 9;
        int top = height - gui.rightHeight;
        gui.rightHeight += 10;

        int money = 0;

        if (mc.player != null && MPManager.getMPEntityProperty(mc.player) != null) {
            //TODO: nullのときの処理
            money = MPManager.getMPEntityProperty(mc.player).getMp();
        }

        //金持ち
        int m = 0;
        if (String.valueOf(money).length() == 8) {
            m = -8;
        } else if (String.valueOf(money).length() == 9) {
            m = -16;
        } else if (String.valueOf(money).length() == 10) {
            m = -24;
        }

        GuiComponent.blit(poseStack, left + m, top, 9, 0, 9, 9, 256, 256);//コイン
        GuiComponent.blit(poseStack, left + 65, top, 0, 18, 9, 9, 256, 256);//M
        GuiComponent.blit(poseStack, left + 74, top, 9, 18, 9, 9, 256, 256);//P

        left += 56;

        for (int i = 1; i <= String.valueOf(money).length() && i <= 10; i += 1) {
            String s = String.valueOf(money).substring(String.valueOf(money).length() - i, String.valueOf(money).length() - i + 1);

            GuiComponent.blit(poseStack, left, top, 9 * Integer.parseInt(s), 9, 9, 9, 256, 256);
            left -= 8;
        }

        RenderSystem.disableBlend();
        mc.getProfiler().pop();
        bind(Gui.GUI_ICONS_LOCATION);

    }

    public static void renderAddMoney(ForgeGui gui, int width, int height, PoseStack poseStack) {

        mc.getProfiler().push("addmoney");

        RenderSystem.enableBlend();
        bind(icons);

        int air = -9;

        int left = width / 2 + 9;
        int top = height - gui.rightHeight;

        int moneyD = 0;

        int moneyC = 0;

        boolean p = false;

        MPEntityProperty pmp = MPManager.getMPEntityProperty(mc.player);

        if (pmp != null) {

            moneyD = pmp.getMpDisplay();
            moneyC = pmp.getMpCount();

            if (moneyC != 0) {
                count = 210;
                pmp.setMpCount(0);
            }

            if (moneyD < 0) {
                moneyD *= -1;
                p = true;
            }

        }

        if (count > 0) {

            int g = 0;
            int m = 0;

            if (String.valueOf(count).length() == 2) {

                if (Integer.parseInt(String.valueOf(count).substring(0, 1)) < 8) {
                    if (p) {
                        m = 10 - (Integer.parseInt(String.valueOf(count).substring(0, 1)) + 2);
                    } else {
                        g = 10 - (Integer.parseInt(String.valueOf(count).substring(0, 1)) + 2);
                    }

                }

            } else if (String.valueOf(count).length() == 1) {

                if (p) {
                    m = 8;
                } else {
                    g = 8;
                }

            }

            left += 56;

            int pm = 0;
            if (p) {
                pm = 9;
            }

            for (int i = 1; i <= String.valueOf(moneyD).length(); i += 1) {
                String s = String.valueOf(moneyD).substring(String.valueOf(moneyD).length() - i, String.valueOf(moneyD).length() - i + 1);
                GuiComponent.blit(poseStack, left, top + g, 9 * Integer.parseInt(s), 45 + pm + m, 9, 9 - g - m, 256, 256);
                left -= 8;
            }

            GuiComponent.blit(poseStack, left, top + g, 9 * 10, 45 + pm + m, 9, 9 - g - m, 256, 256);

            count -= 4;
            if (count <= 0 && mc.player != null) {
                count = 0;
                pmp.setMpDisplay(0);
            }
            gui.rightHeight += 10;
        }

        RenderSystem.disableBlend();
        mc.getProfiler().pop();
        bind(Gui.GUI_ICONS_LOCATION);

    }

    private static void bind(ResourceLocation res) {
        RenderSystem.setShaderTexture(0, res);
    }

}
