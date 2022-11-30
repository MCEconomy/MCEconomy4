package shift.mceconomy.gui;

import com.mojang.datafixers.util.Either;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import shift.mceconomy.MCERecipes;
import shift.mceconomy.MCEconomy;
import shift.mceconomy.purchase.PurchaseItemStackRecipe;

import java.util.List;
import java.util.Optional;

public class MpTooltip {

    private static final ResourceLocation icons = new ResourceLocation(MCEconomy.MOD_ID, "textures/gui/icons.png");

    /**
     * 価格設定されているアイテムのツールチップにMPの情報を追加する
     *
     * @param event イベント
     */
    @SubscribeEvent
    public void onRenderTooltipEvent(RenderTooltipEvent.GatherComponents event) {

        Player player = MCEconomy.proxy.getPlayer();
        if (player == null) {
            return;
        }

        ItemStack itemStack = event.getItemStack();
        List<Either<FormattedText, TooltipComponent>> toolTip = event.getTooltipElements();

        Optional<PurchaseItemStackRecipe> purchaseItemStackRecipeOptional = player
                .getLevel()
                .getRecipeManager()
                .getAllRecipesFor(MCERecipes.PURCHASE_ITEM_STACK.get()).stream().filter(recipe -> recipe.matches(itemStack))
                .findFirst();

        if (purchaseItemStackRecipeOptional.isEmpty()) {
            return;
        }

        PurchaseItemStackRecipe purchaseItemStackRecipe = purchaseItemStackRecipeOptional.get();
        if (purchaseItemStackRecipe.isNotForSale()) {
            toolTip.add(Either.right(new MpTooltipComponent(true, 0)));
            return;
        }

        if (!purchaseItemStackRecipe.isNotForSale()) {
            toolTip.add(Either.right(new MpTooltipComponent(false, purchaseItemStackRecipe.getAmount())));
        }

    }

}
