package shift.mceconomy.api.shop;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public interface IProduct {

    /**
     * 商品自体
     *
     * @return ItemStack
     */
    public ItemStack getItem(IShop shop, @Nullable Level world, @Nullable Player player);

    /**
     * 商品の値段 <br>
     * プレイヤーによって値段を変更したり出来る
     *
     * @param player 購入しようとしているプレイヤー
     * @return 値段
     */
    public int getCost(IShop shop, @Nullable Level world, @Nullable Player player);

    /**
     * 商品を買えるかどうか
     *
     * @param player 購入しようとしているプレイヤー
     * @return trueの時は購入できる
     */
    public boolean canBuy(IShop shop, @Nullable Level world, @Nullable Player player);

}
