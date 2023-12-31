package com.infnet.CoffeeShop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infnet.CoffeeShop.domain.Coffee;
import com.infnet.CoffeeShop.domain.Order;
import com.infnet.CoffeeShop.domain.exceptions.InvalidOrderException;
import com.infnet.CoffeeShop.domain.exceptions.ResourceNotFoundException;
import com.infnet.CoffeeShop.domain.payloads.ResponsePayload;
import com.infnet.CoffeeShop.service.CoffeeShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/coffee-shop")
public class OrderController {
    @Autowired
    CoffeeShopService service;

    private static final Logger LOGGER =
            LoggerFactory.getLogger(OrderController.class);

    @GetMapping
    public ResponseEntity getOrders(@RequestParam(required = false,defaultValue = "0") int id,
                                    @RequestParam(required = false,defaultValue = "None") Coffee coffeeType) throws JsonProcessingException {
        try {
            List<Order> orders = service.getOrders(id,coffeeType);
            return ResponseEntity.ok(orders);
        }catch (ResourceNotFoundException ex){
            ResponsePayload responsePayload = new ResponsePayload(ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsePayload);
        }
    }

    @PostMapping
    public ResponseEntity<ResponsePayload> creatOrder(@RequestBody Order order) throws JsonProcessingException {
        try{
            service.createOrder(order);

            RestTemplate restTemplate = new RestTemplate();
            HttpResponse<String> send = callYeyApi();
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body((new ResponsePayload("Pedido criado, logo será feito e entregue", send.body())));
        }
        catch (InvalidOrderException ex){
            ResponsePayload responsePayload= new ResponsePayload(ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsePayload);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload> update(@PathVariable int id,@RequestBody Order newOrder) throws JsonProcessingException {
        try{
            service.updateOrder(id, newOrder);
            HttpResponse<String> send = callYeyApi();
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ResponsePayload("Pedido Alterado com sucesso!", send.body()));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponsePayload(ex.getMessage(), null));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponsePayload> delete(@PathVariable int id) throws JsonProcessingException {
        try{
            service.deleteOrder(id);
            HttpResponse<String> send = callYeyApi();
            return ResponseEntity.status((HttpStatus.ACCEPTED))
                    .body((new ResponsePayload("Pedido removido com sucesso!", send.body())));

        }catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponsePayload(ex.getMessage(), null));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpResponse<String> callYeyApi() throws URISyntaxException, IOException, InterruptedException {
        LOGGER.info("Calling Kanye API");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.kanye.rest/"))
                .GET()
                .version(HttpClient.Version.HTTP_2)
                .build();
        HttpClient client = HttpClient.newBuilder().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
