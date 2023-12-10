package com.infnet.CoffeeShop;

import com.infnet.CoffeeShop.domain.Coffee;
import com.infnet.CoffeeShop.domain.Order;
import com.infnet.CoffeeShop.domain.exceptions.ResourceNotFoundException;
import com.infnet.CoffeeShop.service.CoffeeShopService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CoffeShopApplicationTests {
	@Autowired
	CoffeeShopService service;

	@Test
	@DisplayName("Should return all orders by coffee or id")
	void shouldReturnAllOrdersByCoffeeOrId() {
		List<Order> orders = service.getOrders(1, Coffee.Expresso);
		Order order = orders.get(0);
		assertEquals(order.getId(), 1);
		assertEquals(order.getPrice(), 10.00);
		assertEquals(order.getCoffee(), Coffee.Expresso);
		assertEquals(order.getClienteName(), "Caio");
		assertThrows(ResourceNotFoundException.class, ()->{
			service.getOrders(0, Coffee.None);
		});
	}

	@Test
	@DisplayName("Should return the updated order")
	void ShouldReturnTheUpdatedOrder() {
		Order newOrder  = new Order(1, "Andre", 12.00, Coffee.Americano);
		service.updateOrder(1, newOrder);
		Order atualizado =  service.getOrder(1);
		assertEquals("Andre", atualizado.getClienteName());
	}

	@Test
	@DisplayName("Should create a new order on database")
	void ShouldCreateANewOrderOnDatabase(){
		Order newOrder = Order.builder()
				.clienteName("Caio")
				.coffee(Coffee.FlattWhite)
				.price(13.99)
				.build();
		service.createOrder(newOrder);
		Order insertedOrder =  service.getOrder(2);
		assertEquals(newOrder.getClienteName(), insertedOrder.getClienteName());
		assertEquals(newOrder.getPrice(), insertedOrder.getPrice());
		assertEquals(newOrder.getCoffee(), insertedOrder.getCoffee());
	}
	@Test
	@DisplayName("Should delete an order on database")
	void ShouldDeleteAnOrderOnDatabase(){
		service.deleteOrder(1);
		assertThrows(ResourceNotFoundException.class, ()->{
			service.getOrders(1, Coffee.None);
		});
	}
}
