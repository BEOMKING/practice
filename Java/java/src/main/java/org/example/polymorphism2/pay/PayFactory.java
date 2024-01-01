package org.example.polymorphism2.pay;

public class PayFactory {
    public static Pay getPay(String option) {
        if (option.equals("kakao")) {
            return new KakaoPay();
        } else if (option.equals("naver")) {
            return new NaverPay();
        } else {
            return new DefaultPay();
        }
    }
}
