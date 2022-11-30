package shift.mceconomy.data;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import shift.mceconomy.MCERecipes;

import java.util.function.Consumer;

public class PurchaseRecipeBuilder implements RecipeBuilder {

    private final int amount;
    private final boolean notForSale;
    private final Ingredient recipeItems;

    public PurchaseRecipeBuilder(int amount, boolean notForSale, Ingredient recipeItems) {
        this.amount = amount;
        this.notForSale = notForSale;
        this.recipeItems = recipeItems;
    }

    public static PurchaseRecipeBuilder purchase(Ingredient recipeItems, int amount) {
        return new PurchaseRecipeBuilder(amount, false, recipeItems);
    }

    @Override
    @NotNull
    public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        return this;
    }

    @Override
    @NotNull
    public RecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    @NotNull
    public Item getResult() {
        return recipeItems.getItems()[0].getItem();
    }

    public void save(@NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        ResourceLocation defaultRecipeId = getDefaultRecipeId(this.getResult());
        this.save(pFinishedRecipeConsumer, new ResourceLocation(defaultRecipeId.getNamespace(), defaultRecipeId.getPath() + "_from_mce4_purchase"));
    }

    public ResourceLocation getDefaultRecipeId(ItemLike pItemLike) {
        return ForgeRegistries.ITEMS.getKey(pItemLike.asItem());
    }

    @Override
    public void save(@NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer, @NotNull ResourceLocation pRecipeId) {

        pFinishedRecipeConsumer.accept(new Result(pRecipeId, amount, notForSale, recipeItems));

    }

    public static class Result implements FinishedRecipe {

        private final ResourceLocation id;
        private final int amount;
        private final boolean notForSale;
        private final Ingredient recipeItems;

        public Result(ResourceLocation id, int amount, boolean notForSale, Ingredient recipeItems) {
            this.id = id;
            this.amount = amount;
            this.notForSale = notForSale;
            this.recipeItems = recipeItems;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject pJson) {

            pJson.addProperty("price", this.amount);
            pJson.addProperty("not_for_sale", this.notForSale);
            pJson.add("ingredient", this.recipeItems.toJson());

        }

        @Override
        @NotNull
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        @NotNull
        public RecipeSerializer<?> getType() {
            return MCERecipes.PURCHASE_ITEM_STACK_SERIALIZERS.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }

    }
}
