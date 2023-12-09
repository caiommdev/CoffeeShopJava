package com.infnet.CoffeeShop.controller;

import com.infnet.CoffeeShop.domain.Order;
import com.infnet.CoffeeShop.domain.exceptions.InvalidOrderException;
import com.infnet.CoffeeShop.domain.exceptions.ResourceNotFoundException;
import com.infnet.CoffeeShop.domain.payloads.ResponsePayload;
import com.infnet.CoffeeShop.service.CoffeeShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coffee-shop")
public class OrderController {
    @Autowired
    CoffeeShopService service;

    @GetMapping
    public String test1(){
        return "ok";
    }
    @GetMapping("/{id}")
    public ResponseEntity getOrder(@PathVariable int id){
        try {
            Order order = service.getOrder(id);
            return ResponseEntity.ok(order);
        }catch (ResourceNotFoundException ex){
            ResponsePayload responsePayload= new ResponsePayload(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsePayload);
        }
    }

    @PostMapping
    public ResponseEntity<ResponsePayload> creatOrder(@RequestBody Order order){
        try{
            service.create(order);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body((new ResponsePayload("Pedido criado, logo será feito e entregue")));
        }
        catch (InvalidOrderException ex){
            ResponsePayload responsePayload= new ResponsePayload(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsePayload);
        }
    }
}
