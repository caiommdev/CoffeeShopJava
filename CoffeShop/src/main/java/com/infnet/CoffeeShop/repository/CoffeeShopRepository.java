package com.infnet.CoffeeShop.repository;

import com.infnet.CoffeeShop.domain.Coffee;
import com.infnet.CoffeeShop.domain.Order;
import com.infnet.CoffeeShop.domain.exceptions.InvalidOrderException;
import com.infnet.CoffeeShop.domain.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CoffeeShopRepository {
    Map<Integer, Order> orders = initDataBase();
    int lastId;

    //Criado para facilitar os testes
    private Map<Integer,Order> initDataBase(){
        Order order = new Order(1, "Caio", Coffee.Expresso);
        Map<Integer, Order> orders = new HashMap<>();
        orders.put(order.getId(), order);
        this.lastId = order.getId();
        return orders;
    }

    public Order getById(int id) throws ResourceNotFoundException{
        if(!orders.containsKey(id)){
            throw new ResourceNotFoundException("Order not found");
        }

        return orders.get(id);
    }

    public void add(Order order) throws InvalidOrderException {
        if(!order.isValid()){
            throw new InvalidOrderException("Invalid order");
        }
        int newId = ++this.lastId;
        order.setId(newId);
        orders.put(order.getId(),order);
    }

    public void update(int id, Order newOrder) throws ResourceNotFoundException {
        if(!orders.containsKey(id)){
            throw new ResourceNotFoundException("Order not found");
        }
        newOrder.setId(id);
        orders.replace(id, newOrder);
    }

    public void deleteById(int id) throws ResourceNotFoundException {
        if(!orders.containsKey(id)){
            throw new ResourceNotFoundException("Order not found");
        }
        orders.remove(id);
    }
}
