package com.infnet.CoffeeShop.service;

import com.infnet.CoffeeShop.domain.Order;
import com.infnet.CoffeeShop.repository.CoffeeShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoffeeShopService {
    @Autowired
    CoffeeShopRepository repository;

    public Order getOrder(int id){
        return repository.getById(id);
    }

    public void createOrder(Order order) {
        repository.add(order);
    }

    public void updateOrder(int id, Order newOrder) {
        repository.update(id, newOrder);
    }

    public void deleteOrder(int id) {
        repository.deleteById(id);
    }
}
