package org.example.polymorphism2.pay;

public class DefaultPay implements Pay {
    @Override
    public boolean pay(final int amount) {
        System.out.println("결제 수단이 없습니다.");
        return false;
    }
}
