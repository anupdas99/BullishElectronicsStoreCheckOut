package com.bullish.domain;

public enum DealType {
    TWENTY_PERCENT_OFF {
        @Override
        public double applyDiscount(double price) {
            return price * 0.2;
        }
    };

    public abstract double applyDiscount(double price);

}
