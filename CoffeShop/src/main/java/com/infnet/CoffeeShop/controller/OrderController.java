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
            service.createOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body((new ResponsePayload("Pedido criado, logo será feito e entregue")));
        }
        catch (InvalidOrderException ex){
            ResponsePayload responsePayload= new ResponsePayload(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsePayload);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload> update(@PathVariable int id,@RequestBody Order newOrder){
        try{
            service.updateOrder(id, newOrder);

            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ResponsePayload("Pedido Alterado com sucesso!"));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponsePayload(ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponsePayload> delete(@PathVariable int id){
        try{
            service.deleteOrder(id);
            return ResponseEntity.status((HttpStatus.ACCEPTED))
                    .body((new ResponsePayload("Pedido removido com sucesso!")));

        }catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponsePayload(ex.getMessage()));
        }
    }
}
