package shift.mceconomy.gui;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import shift.mceconomy.MCEGuis;

/**
 * ショップGUIのデータを管理するクラス
 * <p>
 * GUIとContainerの橋渡し
 */
public class ShopMenu extends AbstractContainerMenu {

    public ShopMenu(int containerId, Inventory inv, FriendlyByteBuf data) {
        super(MCEGuis.SHOP_MENU.get(), containerId);
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
