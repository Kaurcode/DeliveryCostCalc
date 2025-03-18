package com.fujitsu.deliverycostcalc.entity;

import com.fujitsu.deliverycostcalc.exception.InvalidMoneyException;
import jakarta.persistence.Embeddable;

@Embeddable
public class Money implements Comparable<Money> {
    private int cents;

    public Money() {}

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

    public int compareTo(Money otherMoney) {
        return Integer.compare(getCents(), otherMoney.getCents());
    }
}
