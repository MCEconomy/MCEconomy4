package shift.mceconomy.gui;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import shift.mceconomy.api.shop.IProduct;
import shift.mceconomy.api.shop.IShop;
import shift.mceconomy.player.MPManager;

public class ShopContainer implements Container {

    private final IShop shop;
    private final NonNullList<ItemStack> itemStacks = NonNullList.withSize(1, ItemStack.EMPTY);
    private final Player player;
    private IProduct currentRecipe;
    private int currentRecipeIndex = 0;

    public ShopContainer(Player player, IShop shop) {

        this.shop = shop;
        this.player = player;
    }

    public void setCurrentRecipeIndex(int index) {
        this.currentRecipeIndex = 0;
        this.clearContent();
    }

    public int getCurrentRecipeIndex() {
        return currentRecipeIndex;
    }

    @Override
    public int getContainerSize() {
        return this.itemStacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.itemStacks) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    @NotNull
    public ItemStack getItem(int pSlot) {
        return this.itemStacks.get(pSlot);
    }

    @Override
    @NotNull
    public ItemStack removeItem(int pSlot, int pAmount) {
        return ContainerHelper.removeItem(this.itemStacks, pSlot, pAmount);
    }

    @Override
    @NotNull
    public ItemStack removeItemNoUpdate(int pSlot) {
        return ContainerHelper.takeItem(this.itemStacks, pSlot);
    }

    @Override
    @NotNull
    public void setItem(int pSlot, @NotNull ItemStack pStack) {
        this.itemStacks.set(pSlot, pStack);
        if (!pStack.isEmpty() && pStack.getCount() > this.getMaxStackSize()) {
            pStack.setCount(this.getMaxStackSize());
        }
    }

    @Override
    public void setChanged() {
        this.resetSlots();
    }

    public void resetSlots() {

        IProduct item = shop.getProductList(player.getLevel(), player).get(currentRecipeIndex);

        int money = MPManager.getMPEntityProperty(player).getMp();//MCEconomyAPI.getPlayerMP(player);

        /*if(player!=null){
        	NBTTagCompound nbt = player.getEntityData();
        	money = nbt.getInteger("money");
        	//System.out.println("resetSlots0"+money);
        }/*else if(player2!=null){
        	NBTTagCompound nbt = player2.getEntityData();
        	money = nbt.getInteger("money");
        	System.out.println("resetSlots0"+money);
        }*/

        if (money >= item.getCost(shop, player.getLevel(), player)
                && item.canBuy(shop, player.getLevel(), player)) {
            this.setItem(0, item.getItem(shop, player.getLevel(), player).copy());
        } else {
            this.setItem(0, ItemStack.EMPTY);

        }
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return true;
    }

    @Override
    public void clearContent() {

    }
}
