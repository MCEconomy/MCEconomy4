package shift.mceconomy.api.shop;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;

public class ShopBase implements IShop {

    private final String name;
    private final ArrayList<IProduct> list;

    public ShopBase(String name) {
        this.name = name;
        this.list = new ArrayList<>();
    }


    @Override
    public Component getShopName(Level world, Player player) {
        return Component.translatable(name);
    }

    @Override
    public void addProduct(IProduct product) {
        list.add(product);
    }

    @Override
    public ArrayList<IProduct> getProductList(Level world, Player player) {
        return list;
    }

}
