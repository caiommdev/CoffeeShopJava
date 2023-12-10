package com.infnet.CoffeeShop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private int id;
    private String clienteName;
    private double price;
    private Coffee coffee;

    public boolean isValid() {
        if((this.clienteName == null) || (this.coffee == null) || (this.price == 0)){
            return false;
        }
        return true;
    }
}
