package shift.mceconomy.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * バニラのアイテムの価格を設定するクラス
 */
public class MCEVanillaPurchaseProvider extends RecipeProvider {
    public MCEVanillaPurchaseProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {

        //PurchaseRecipeBuilder.notForSale(Ingredient.of(Blocks.AIR)).save(consumer);
        PurchaseRecipeBuilder.purchase(Ingredient.of(Blocks.STONE), 1).save(consumer);

    }


}
