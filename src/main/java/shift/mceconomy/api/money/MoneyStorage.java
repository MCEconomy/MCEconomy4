package shift.mceconomy.api.money;

/**
 * IMoneyStorageの基礎実装
 *
 * @author Shift02
 */
public class MoneyStorage implements IMoneyStorage {
    protected int money;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public MoneyStorage(int capacity) {
        this(capacity, capacity, capacity);
    }

    public MoneyStorage(int capacity, int maxTransfer) {
        this(capacity, maxTransfer, maxTransfer);
    }

    public MoneyStorage(int capacity, int maxReceive, int maxExtract) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    @Override
    public int receiveMoney(int maxReceive, boolean simulate) {
        if (!canReceive()) return 0;

        int moneyReceived = Math.min(capacity - money, Math.min(this.maxReceive, maxReceive));
        if (!simulate) money += moneyReceived;

        return moneyReceived;
    }

    @Override
    public int extractMoney(int maxExtract, boolean simulate) {
        if (!canExtract()) return 0;

        int moneyExtracted = Math.min(money, Math.min(this.maxExtract, maxExtract));
        if (!simulate) money -= moneyExtracted;

        return moneyExtracted;
    }


    @Override
    public void setMoneyStored(int money) {
        this.money = money;
    }

    @Override
    public int getMoneyStored() {
        return money;
    }

    @Override
    public int getMaxMoneyStored() {
        return capacity;
    }

    @Override
    public boolean canReceive() {
        return this.maxReceive > 0;
    }

    @Override
    public boolean canExtract() {
        return this.maxExtract > 0;
    }


}
