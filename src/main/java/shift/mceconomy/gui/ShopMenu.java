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
import shift.mceconomy.MCEGuis;

/**
 * ショップGUIのデータを管理するクラス
 * <p>
 * GUIとContainerの橋渡し
 */
public class ShopMenu extends AbstractContainerMenu {

    //メモ IInventory -> Container
    
    private final Container container;
    private final ContainerData containerData;

    /**
     * クライアント側
     */
    public ShopMenu(int containerId, Inventory playerInventory, FriendlyByteBuf data) {
        this(containerId, playerInventory);
    }

    /**
     * サーバー側
     */
    public ShopMenu(int containerId, Inventory playerInventory) {
        super(MCEGuis.SHOP_MENU.get(), containerId);

        this.container = new SimpleContainer(1);
        this.containerData = new SimpleContainerData(2);


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
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }
}
