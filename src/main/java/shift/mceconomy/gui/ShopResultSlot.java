package shift.mceconomy.gui;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import shift.mceconomy.api.shop.IShop;
import shift.mceconomy.player.MPManager;

public class ShopResultSlot extends Slot {

    private final IShop shop;

    public ShopResultSlot(IShop shop, Container pContainer, int pSlot, int pX, int pY) {
        super(pContainer, pSlot, pX, pY);
        this.shop = shop;
    }

    public boolean mayPlace(@NotNull ItemStack pStack) {
        return false;
    }


    public void onTake(@NotNull Player pPlayer, @NotNull ItemStack pStack) {
        this.setChanged();

        int cost = shop.getProductList(pPlayer.getLevel(), pPlayer)
                .get(((ShopContainer) this.container).getCurrentRecipeIndex())
                .getCost(shop, pPlayer.getLevel(), pPlayer);

        if (pPlayer.getLevel().isClientSide()) {
            MPManager.getInstance().reduceShopPlayerMP(pPlayer, cost, false);
        } else {
            MPManager.getInstance().reduceShopPlayerMP(pPlayer, cost, false);
            //MCEconomyAPI.reducePlayerMP(par1EntityPlayer, cost, false);
        }
        
        this.setChanged();

    }

}
