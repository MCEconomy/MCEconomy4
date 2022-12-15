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
import shift.mceconomy.api.shop.IShop;
import shift.mceconomy.packet.PacketHandler;
import shift.mceconomy.packet.ShopButtonPacket;
import shift.mceconomy.player.MPManager;

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
        int tmpWidth = (this.width - this.imageWidth) / 2;
        int tmpHeight = (this.height - this.imageHeight) / 2;
        this.blit(pPoseStack, tmpWidth, tmpHeight, 0, 0, this.imageWidth, this.imageHeight);

        //商品
        Level level = this.pPlayerInventory.player.getLevel();
        Player player = this.pPlayerInventory.player;
        IShop shop = this.menu.getShop();
        List<IProduct> productList = shop.getProductList(level, player);
        if (productList != null && !productList.isEmpty()) {
            IProduct productItem = productList.get(this.currentRecipeIndex);


            this.itemRenderer.blitOffset = 100.0F;
            this.itemRenderer.renderGuiItem(productItem.getItem(this.menu.getShop(), level, player), tmpWidth + 17, tmpHeight + 32);
            this.itemRenderer.blitOffset = 0.0F;


            //購入不可
            RenderSystem.setShaderTexture(0, CONTAINER_LOCATION);
            int money = MPManager.getMPEntityProperty(player).getMp();

            if (money < productItem.getCost(shop, level, player) || !productItem.canBuy(shop, level, player)) {

                this.blit(pPoseStack, this.leftPos + 101, this.topPos + 29, 212, 0, 28, 21);
                this.blit(pPoseStack, this.leftPos + 134, this.topPos + 27, 176, 38, 30, 30);
            }


        }

        //所持金
        RenderSystem.setShaderTexture(0, MpHud.icons);
        //109
        int m2 = 93 - 86;
        this.blit(pPoseStack, tmpWidth + 83 - m2, tmpHeight + 64, 0, 27, 86 + m2, 15);

        this.blit(pPoseStack, tmpWidth + 86 - m2, tmpHeight + 67, 9, 0, 9, 9);

        int a = -3;

        this.blit(pPoseStack, tmpWidth + 86 + 65 + a, tmpHeight + 67, 0, 18, 9, 9);
        this.blit(pPoseStack, tmpWidth + 86 + 74 + a, tmpHeight + 67, 9, 18, 9, 9);

        int money = MPManager.getMPEntityProperty(player).getMp();

        int k = tmpWidth + 86 + 56 + a;

        for (int i = 1; i <= String.valueOf(money).length(); i += 1) {
            String s = String.valueOf(money).substring(String.valueOf(money).length() - i, String.valueOf(money).length() - i + 1);
            this.blit(pPoseStack, k, tmpHeight + 67, 9 * Integer.parseInt(s), 9, 9, 9);
            k -= 8;
        }


    }

    protected void renderTooltip(PoseStack pPoseStack, int pX, int pY) {

        super.renderTooltip(pPoseStack, pX, pY);
        
        if (this.isHovering(8, 23, 34, 34, pX, pY)) {

            Level level = this.pPlayerInventory.player.getLevel();
            Player player = this.pPlayerInventory.player;
            IShop shop = this.menu.getShop();
            List<IProduct> productList = shop.getProductList(level, player);
            if (productList != null && !productList.isEmpty()) {
                IProduct productItem = productList.get(this.currentRecipeIndex);

                this.renderTooltip(pPoseStack, productItem.getItem(shop, level, player), pX, pY);
            }

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
