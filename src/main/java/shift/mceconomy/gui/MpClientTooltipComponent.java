package shift.mceconomy.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.NotNull;
import shift.mceconomy.MCEconomy;

/**
 * MPをツールチップで描画するクラス
 */
public class MpClientTooltipComponent implements ClientTooltipComponent {

    private static final ResourceLocation icons = new ResourceLocation(MCEconomy.MOD_ID, "textures/gui/icons.png");

    private final ClientTextTooltip clientTextTooltip;

    public MpClientTooltipComponent(boolean notForSale, int price) {

        if (notForSale) {
            this.clientTextTooltip = new ClientTextTooltip(FormattedCharSequence.forward("    " + Component.translatable("tooltip.mp.not_for_sale").getString(), Style.EMPTY));
        } else {
            this.clientTextTooltip = new ClientTextTooltip(FormattedCharSequence.forward("    " + String.format("%1$ ,9d", price) + " MP", Style.EMPTY));
        }

    }

    public int getWidth(@NotNull Font pFont) {
        return this.clientTextTooltip.getWidth(pFont);
    }

    public int getHeight() {
        return this.clientTextTooltip.getHeight();
    }

    public void renderText(@NotNull Font pFont, int pX, int pY, @NotNull Matrix4f pMatrix4f, MultiBufferSource.@NotNull BufferSource pBufferSource) {
        this.clientTextTooltip.renderText(pFont, pX, pY, pMatrix4f, pBufferSource);
    }

    public void renderImage(@NotNull Font pFont, int pMouseX, int pMouseY, PoseStack pPoseStack, @NotNull ItemRenderer pItemRenderer, int pBlitOffset) {

        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, icons);

        pPoseStack.pushPose();
        GuiComponent.blit(pPoseStack, pMouseX, pMouseY, 9, 0, 9, 9, 256, 256);

        pPoseStack.popPose();

    }
}
