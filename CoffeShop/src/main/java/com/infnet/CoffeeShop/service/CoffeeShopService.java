package com.infnet.CoffeeShop.service;

import com.infnet.CoffeeShop.domain.Coffee;
import com.infnet.CoffeeShop.domain.Order;
import com.infnet.CoffeeShop.domain.exceptions.ResourceNotFoundException;
import com.infnet.CoffeeShop.repository.CoffeeShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoffeeShopService {
    @Autowired
    CoffeeShopRepository repository;

    public Order getOrder(int id){
        return repository.getById(id);
    }

    public List<Order> getOrders(int id, Coffee coffee){
        List<Order> orders = repository.getAll(id,coffee);
        if(orders.isEmpty()){
            throw new ResourceNotFoundException("Nem um pedido foi encontrado");
        }
        return orders;
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
