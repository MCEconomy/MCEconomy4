package shift.mceconomy.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
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

        PurchaseRecipeBuilder.purchase(Ingredient.of(Items.COAL), 50).save(consumer);
        
    }


}
