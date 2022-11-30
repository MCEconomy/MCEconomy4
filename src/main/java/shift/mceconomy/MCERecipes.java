package shift.mceconomy;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.RegistryObject;
import shift.mceconomy.purchase.PurchaseItemStackRecipe;

public class MCERecipes {

    public static final RegistryObject<RecipeType<PurchaseItemStackRecipe>> PURCHASE_ITEM_STACK = MCEconomy.RECIPE_TYPES.register("purchase_item_stack", () -> RecipeType.simple(new ResourceLocation(MCEconomy.MOD_ID, "purchase_item_stack")));
    public static final RegistryObject<RecipeSerializer<?>> PURCHASE_ITEM_STACK_SERIALIZERS = MCEconomy.RECIPE_SERIALIZERS.register("purchase_item_stack", PurchaseItemStackRecipe.Serializer::new);


    static void register() {
    }

}
