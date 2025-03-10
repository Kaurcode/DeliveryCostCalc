package com.fujitsu.deliverycostcalc;

public class Money implements Comparable<Money> {
    private int euros;
    private int cents;

    public Money(int euros, int cents) throws InvalidMoneyException {
        if (euros < 0) {
            throw new InvalidMoneyException("Invalid amount of euros (euros can't be negative): " + euros);
        } else if (cents < 0) {
            throw new InvalidMoneyException("Invalid amount of cents (cents can't be negative): " + cents);
        } else if (99 < cents) {
            throw new InvalidMoneyException("Invalid amount of cents (cents can't be greater than 99): " + cents);
        }

        this.euros = euros;
        this.cents = cents;
    }

    public int getInCents() {
        return 100 * euros + cents;
    }

    public int compareTo(Money otherMoney) {
        return Integer.compare(getInCents(), otherMoney.getInCents());
    }
}
