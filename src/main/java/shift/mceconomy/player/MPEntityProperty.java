package shift.mceconomy.player;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import shift.mceconomy.api.money.IMoneyStorage;
import shift.mceconomy.api.money.MoneyStorage;
import shift.mceconomy.config.MCEConfig;

/**
 * プライヤーのMPをCapability経由でアクセスするクラス
 */
public class MPEntityProperty implements IMoneyStorage, ICapabilitySerializable<CompoundTag> {

    static Capability<MPEntityProperty> MP_CAPABILITY = CapabilityMPHandler.MP_HANDLER_CAPABILITY;

    private final LazyOptional<MPEntityProperty> holder = LazyOptional.of(() -> this);

    //private int mp=0;
    private int mpDisplay;
    private int mpCount;
    //private boolean init= false;

    //IMoneyにブリッジ
    private final MoneyStorage moneyStorage = new MoneyStorage(MCEConfig.maxMp);
    private Player player;

    public final static String INIT = "init";

    public MPEntityProperty() {

    }

    public MPEntityProperty(Player player) {
        this.player = player;
    }

    @Override
    public CompoundTag serializeNBT() {

        CompoundTag nbt = new CompoundTag();
        nbt.putInt("mp", getMp());

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

        if (nbt.contains("mp")) {

            if (nbt.getBoolean(INIT)) {
                this.setMpDisplay(0);
                this.mpCount = 0;
            } else {

                int addMP = nbt.getInt("mp");
                mpDisplay = addMP - getMp() + mpDisplay;
                this.mpCount = 20;

            }

            setMp(nbt.getInt("mp"));

        }

    }


    public int getMp() {
        return moneyStorage.getMoneyStored();
    }

    public void setMp(int mp) {
        moneyStorage.setMoneyStored(mp);
    }

    public int getMpDisplay() {
        return mpDisplay;
    }

    public void setMpDisplay(int mpDisplay) {
        this.mpDisplay = mpDisplay;
    }

    public int getMpCount() {
        return mpCount;
    }

    public void setMpCount(int mpCount) {
        this.mpCount = mpCount;
    }

    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @NotNull Direction direction) {
        if (capability == MP_CAPABILITY) {
            return MP_CAPABILITY.orEmpty(capability, holder);
        }
        return LazyOptional.empty();
    }

    @Override
    public int receiveMoney(int maxReceive, boolean simulate) {

        return 0;//MPManager.getInstance().addPlayerMP(player, maxReceive, simulate);

    }

    @Override
    public int extractMoney(int maxExtract, boolean simulate) {

        return 0;//MPManager.getInstance().reducePlayerMP(player, maxExtract, simulate);

    }

    @Override
    public int getMoneyStored() {
        return moneyStorage.getMoneyStored();
    }

    @Override
    public int getMaxMoneyStored() {
        return moneyStorage.getMaxMoneyStored();
    }

    @Override
    public boolean canReceive() {
        return moneyStorage.canReceive();
    }

    @Override
    public boolean canExtract() {
        return moneyStorage.canExtract();
    }

    @Override
    public void setMoneyStored(int money) {
        moneyStorage.setMoneyStored(money);

    }


}
