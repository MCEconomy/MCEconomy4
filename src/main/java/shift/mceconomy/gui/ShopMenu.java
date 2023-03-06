package shift.mceconomy.gui;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import shift.mceconomy.MCEGuis;
import shift.mceconomy.MCEUtil;
import shift.mceconomy.api.shop.IShop;

/**
 * ショップGUIのデータを管理するクラス
 * <p>
 * GUIとContainerの橋渡し
 */
public class ShopMenu extends AbstractContainerMenu {

    //メモ IInventory -> Container

    private final Container container;
    private final ContainerData containerData;

    private final IShop shop;

    private final ShopContainer shopContainer;

    /**
     * クライアント側
     */
    public ShopMenu(int containerId, Inventory playerInventory, FriendlyByteBuf data) {
        this(containerId, playerInventory, MCEUtil.getShop(data.readUtf(100)));
    }

    /**
     * サーバー側
     */
    public ShopMenu(int containerId, Inventory playerInventory, IShop shop) {
        super(MCEGuis.SHOP_MENU.get(), containerId);

        this.container = new SimpleContainer(1);
        this.containerData = new SimpleContainerData(2);

        this.shop = shop;

        this.shopContainer = new ShopContainer(playerInventory.player, shop);

        this.addSlot(new ShopResultSlot(shop, this.shopContainer, 0, 138, 31));

        //プレイヤーインベントリー
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        //プレイヤーインベントリー(ホットバー)
        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

        this.container.setChanged();
    }

    public IShop getShop() {
        return shop;
    }

    @Override
    @NotNull
    public ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return this.shopContainer.stillValid(pPlayer);
    }

    public void setCurrentRecipeIndex(int i) {

        this.shopContainer.setCurrentRecipeIndex(i);


    }
}
