package eightSynchronizers.fx;

public enum Currency {
    ABC(1),
    DEF(2),
    XYZ(3);

    public final int rate;

    private Currency(int rate){
        this.rate = rate;
    }

    public static double computeRate(Currency from, Currency to){
        return to.rate / from.rate;
    }


}
