package shift.mceconomy.purchase;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import shift.mceconomy.MCERecipes;

public class PurchaseItemStackRecipe implements Recipe<ItemStackContainer> {

    private final ResourceLocation recipeId;
    private final int amount;
    private final Ingredient recipeItems;

    public PurchaseItemStackRecipe(ResourceLocation recipeId, int amount, Ingredient recipeItems) {
        this.recipeId = recipeId;
        this.amount = amount;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(ItemStackContainer pContainer, Level pLevel) {
        return recipeItems.test(pContainer.getItem(0));
    }

    public boolean matches(ItemStack itemStack) {
        return recipeItems.test(itemStack);
    }

    @Override
    @NotNull
    public ItemStack assemble(ItemStackContainer pContainer) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    @NotNull
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    @NotNull
    public RecipeSerializer<?> getSerializer() {
        return MCERecipes.PURCHASE_ITEM_STACK_SERIALIZERS.get();
    }

    @Override
    @NotNull
    public RecipeType<?> getType() {
        return MCERecipes.PURCHASE_ITEM_STACK.get();
    }

    public int getAmount() {
        return amount;
    }

    public static class Serializer implements RecipeSerializer<PurchaseItemStackRecipe> {

        @Override
        @NotNull
        public PurchaseItemStackRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {

            int price = GsonHelper.getAsInt(pSerializedRecipe, "price", 0);

            JsonElement jsonelement = (JsonElement) (GsonHelper.isArrayNode(pSerializedRecipe, "ingredient") ? GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredient") : GsonHelper.getAsJsonObject(pSerializedRecipe, "ingredient"));
            Ingredient ingredient = Ingredient.fromJson(jsonelement);

            return new PurchaseItemStackRecipe(pRecipeId, price, ingredient);
        }

        @Override
        @NotNull
        public PurchaseItemStackRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {

            int price = pBuffer.readVarInt();
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);

            return new PurchaseItemStackRecipe(pRecipeId, price, ingredient);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, PurchaseItemStackRecipe pRecipe) {

            pBuffer.writeVarInt(pRecipe.getAmount());
            pRecipe.recipeItems.toNetwork(pBuffer);

        }
    }
}
