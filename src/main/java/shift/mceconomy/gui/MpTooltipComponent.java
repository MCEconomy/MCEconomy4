package shift.mceconomy.gui;

import net.minecraft.world.inventory.tooltip.TooltipComponent;

/**
 * MPをツールチップで扱うクラス
 */
public class MpTooltipComponent implements TooltipComponent {

    private final boolean notForSale;
    private final int price;

    public MpTooltipComponent(boolean notForSale, int price) {
        this.notForSale = notForSale;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public boolean isNotForSale() {
        return notForSale;
    }
}
