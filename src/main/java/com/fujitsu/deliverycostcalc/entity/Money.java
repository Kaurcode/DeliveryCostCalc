package com.fujitsu.deliverycostcalc.entity;

import com.fujitsu.deliverycostcalc.exception.InvalidMoneyException;
import jakarta.persistence.Embeddable;

@Embeddable
public class Money {
    private int cents;

    protected Money() {}

    public Money(String money) throws InvalidMoneyException {
        if (money == null || money.isEmpty()) {
            throw new InvalidMoneyException("Money string cannot be null or empty");
        }

        money = money.replaceAll("€", "");
        money = money.trim();

        if (!money.matches("^(((\\d{1,3}(,\\d{3})*|\\d+)(\\.\\d{1,2}))|" +
                "\\d+(,\\d{1,2})?)0*$")) {
            throw new InvalidMoneyException("Invalid money format");
        }

        String[] eurosAndCents;
        if (money.contains(".")) {
            money = money.replaceAll(",", "");
            eurosAndCents = money.split("\\.");
        } else {
            eurosAndCents = money.split(",");
        }

        this.cents = Integer.parseInt(eurosAndCents[0]) * 100;

        if (eurosAndCents.length == 1) {
            return;
        }

        String cents = eurosAndCents[1].replaceAll("0+$", "");

        if (cents.length() == 1) {
            this.cents += Integer.parseInt(cents) * 10;
        } else {
            this.cents += Integer.parseInt(cents);
        }
    }

    public int getCents() {
        return cents;
    }

    /**
     * Adds two money instances together (adding up the amount of cents)
     * @param money Other money instance
     */
    public void add(Money money) {
        this.cents += money.getCents();
    }

    @Override
    public String toString() {
        return String.format("%d,%d", cents / 100, cents % 100);
    }
}
