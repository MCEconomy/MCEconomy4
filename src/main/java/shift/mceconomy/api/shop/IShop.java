package shift.mceconomy.api.shop;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public interface IShop {

    /**
     * ショップの名前 <br>
     *
     * @return ショップの名前
     */
    public Component getShopName(@Nullable Level world, @Nullable Player player);

    /**
     * ショップに商品を追加する
     *
     * @param product 商品
     */
    public void addProduct(IProduct product);

    /**
     * ショップの商品一覧を取得
     *
     * @param world
     * @param player
     * @return
     */
    public List<IProduct> getProductList(@Nullable Level world, @Nullable Player player);


}
