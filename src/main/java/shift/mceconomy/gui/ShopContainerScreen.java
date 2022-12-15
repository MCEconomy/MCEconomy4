package shift.mceconomy.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import shift.mceconomy.MCEconomy;
import shift.mceconomy.api.shop.IProduct;
import shift.mceconomy.packet.PacketHandler;
import shift.mceconomy.packet.ShopButtonPacket;

import java.util.List;


/**
 * ShopのGUI
 */
public class ShopContainerScreen extends AbstractContainerScreen<ShopMenu> {

    private static final ResourceLocation CONTAINER_LOCATION = new ResourceLocation(MCEconomy.MOD_ID, "textures/gui/shop.png");
    private final Inventory pPlayerInventory;

    private ShopButton nextProductButton;
    private ShopButton previousProductButton;

    private int currentRecipeIndex;

    public ShopContainerScreen(ShopMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.pPlayerInventory = pPlayerInventory;
    }

    protected void init() {
        super.init();

        this.titleLabelX = 28;
        this.titleLabelY = 8;

        this.currentRecipeIndex = 0;

        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;

        this.nextProductButton = this.addRenderableWidget(new ShopButton(i + 154, j + 3, 12, 19, true, Component.translatable(""), (button) -> {
            ++this.currentRecipeIndex;
            this.menu.setCurrentRecipeIndex(this.currentRecipeIndex);
            PacketHandler.INSTANCE.sendToServer(ShopButtonPacket.createShopButtonPacket(this.currentRecipeIndex));
        }));
        this.previousProductButton = this.addRenderableWidget(new ShopButton(i + 9, j + 3, 12, 19, false, Component.translatable(""), (button) -> {
            --this.currentRecipeIndex;
            this.menu.setCurrentRecipeIndex(this.currentRecipeIndex);
            PacketHandler.INSTANCE.sendToServer(ShopButtonPacket.createShopButtonPacket(this.currentRecipeIndex));
        }));

        this.nextProductButton.active = false;
        this.previousProductButton.active = false;

    }

    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        super.renderLabels(pPoseStack, pMouseX, pMouseY);

        Level level = this.pPlayerInventory.player.getLevel();
        Player player = this.pPlayerInventory.player;
        List<IProduct> productList = this.menu.getShop().getProductList(level, player);
        if (productList != null && !productList.isEmpty()) {
            IProduct ProductItem = productList.get(this.currentRecipeIndex);

            this.font.draw(pPoseStack, "Cost", 46, 25, 4210752);
            this.font.draw(pPoseStack, ProductItem.getCost(this.menu.getShop(), level, player) + " MP", 46, 34, 4210752);

        }

    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, CONTAINER_LOCATION);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(pPoseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

        Level level = this.pPlayerInventory.player.getLevel();
        Player player = this.pPlayerInventory.player;
        List<IProduct> productList = this.menu.getShop().getProductList(level, player);
        if (productList != null && !productList.isEmpty()) {
            IProduct ProductItem = productList.get(this.currentRecipeIndex);


            this.itemRenderer.blitOffset = 100.0F;
            this.itemRenderer.renderGuiItem(ProductItem.getItem(this.menu.getShop(), level, player), i + 17, j + 32);
            this.itemRenderer.blitOffset = 0.0F;
        }


    }

    protected void containerTick() {
        super.containerTick();

        Level level = this.pPlayerInventory.player.getLevel();
        Player player = this.pPlayerInventory.player;
        List<IProduct> productList = this.menu.getShop().getProductList(level, player);

        if (productList != null) {
            this.nextProductButton.active = this.currentRecipeIndex < productList.size() - 1;
            this.previousProductButton.active = this.currentRecipeIndex > 0;
        }
    }
}
