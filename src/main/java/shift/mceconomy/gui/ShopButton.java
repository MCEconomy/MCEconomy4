package shift.mceconomy.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import shift.mceconomy.MCEconomy;

public class ShopButton extends Button {

    public static final ResourceLocation optionsBackground = new ResourceLocation(MCEconomy.MOD_ID, "textures/gui/shop.png");

    private final boolean mirrored;

    public ShopButton(int pX, int pY, int pWidth, int pHeight, boolean mirrored, Component pMessage, OnPress pOnPress) {
        super(pX, pY, pWidth, pHeight, pMessage, pOnPress);
        this.mirrored = mirrored;
    }

    public void renderButton(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {

        if (this.visible) {

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, optionsBackground);

            boolean flag = pMouseX >= this.x && pMouseY >= this.y && pMouseX < this.x + this.width
                    && pMouseY < this.y + this.height;
            int k = 0;
            int l = 176;

            if (!this.active) {
                l += this.width * 2;
            } else if (flag) {
                l += this.width;
            }

            if (!this.mirrored) {
                k += this.height;
            }

            this.blit(pPoseStack, this.x, this.y, l, k, this.width, this.height);
        }

    }
}
