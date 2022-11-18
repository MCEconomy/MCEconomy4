package shift.mceconomy.api.money;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import shift.mceconomy.config.MCEConfig;

/**
 * Capabilityを持つことが出来るほぼ全てのインスタンスに実装できる。
 *
 * @author Shift02
 */
public class MoneyStorageProvider implements ICapabilitySerializable<CompoundTag> {

    public static Capability<IMoneyStorage> MONEY = CapabilityMoneyStorage.MONEY;

    private final LazyOptional<IMoneyStorage> holder;

    IMoneyStorage money;

    public MoneyStorageProvider(int amount) {
        money = new MoneyStorage(MCEConfig.maxMp);
        money.setMoneyStored(amount);
        holder = LazyOptional.of(() -> money);
    }


    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, Direction facing) {

        if (MONEY == capability && facing == null) {
            return holder.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {

        CompoundTag nbt = new CompoundTag();
        nbt.putInt("money", (money.getMoneyStored()));
        nbt.putInt("capacity", (((MoneyStorage) money).capacity));
        return nbt;

    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

        ((MoneyStorage) money).money = ((CompoundTag) nbt).getInt("money");
        ((MoneyStorage) money).capacity = ((CompoundTag) nbt).getInt("capacity");

    }

}
