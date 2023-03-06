package shift.mceconomy.api.money;

/**
 * MPを保管するインターフェイス。
 * <p>
 * 財布や金庫で使用する
 * 実際の実装例は {@link MoneyStorage} を参考にする
 *
 * @author Shift02
 */
public interface IMoneyStorage {

    /**
     * MPをストレージに追加する
     *
     * @param maxReceive 追加する最大額
     * @param simulate   シミュレーションかどうか。Trueなら実際に値は変化しない
     * @return 実際に追加できた額
     */
    int receiveMoney(int maxReceive, boolean simulate);

    /**
     * MPをストレージから減らす
     *
     * @param maxExtract 減らす最大額
     * @param simulate   シミュレーションかどうか。Trueなら実際に値は変化しない
     * @return 実際に減らせれた額
     */
    int extractMoney(int maxExtract, boolean simulate);

    /**
     * 現在持っているMPの額
     */
    int getMoneyStored();

    /**
     * 貯めれるMPの最大
     */
    int getMaxMoneyStored();

    /**
     * MPをストレージに設定する
     *
     * @param money 設定するMPの値
     * @deprecated 出来れば使用してほしくない(将来の更新で消す可能性大)
     */
    void setMoneyStored(int money);


    /**
     * MPを追加することができるか。これがfalseの場合 receiveMoney() を呼んだ時に0を返す
     */
    boolean canReceive();

    /**
     * MPを減らすことができるか。これがfalseの場合 extractMoney() を呼んだ時に0を返す
     */
    boolean canExtract();

}
