package com.infnet.CoffeeShop.repository;

import com.infnet.CoffeeShop.domain.Order;
import com.infnet.CoffeeShop.domain.exceptions.InvalidOrderException;
import com.infnet.CoffeeShop.domain.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CoffeeShopRepository {
    Map<Integer, Order> orders = new HashMap<>();
    int lastId = 0;

    public Order getById(int id) throws ResourceNotFoundException{
        if(!orders.containsKey(id)){
            throw new ResourceNotFoundException("Order id not found");
        }

        return orders.get(id);
    }

    public void add(Order order)throws InvalidOrderException {
        if(!order.isValid()){
            throw new InvalidOrderException("Invalid order");
        }

        order.setId(++this.lastId);
        orders.put(order.getId(),order);
    }
}
