package shift.mceconomy.api.shop;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ProductBase implements IProduct {

    protected ItemStack item;
    protected int cost;

    public ProductBase(ItemStack item, int cost) {
        this.item = item;
        this.cost = cost;
    }

    @Override
    public ItemStack getItem(IShop shop, Level world, Player player) {
        return this.item;
    }

    @Override
    public int getCost(IShop shop, Level world, Player player) {
        return this.cost;
    }

    @Override
    public boolean canBuy(IShop shop, Level world, Player player) {
        return true;
    }
}
