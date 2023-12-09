package com.infnet.CoffeeShop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private int id;
    private String clienteName;
    private Coffee coffee;

    public boolean isValid() {
        if((this.clienteName == null) || (this.coffee == null)){
            return false;
        }
        return true;
    }
}
