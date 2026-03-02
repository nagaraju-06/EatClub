package com.alpha.Eatclub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.Eatclub.dto.CustomerDTO;
import com.alpha.Eatclub.dto.ResponseStructure;
import com.alpha.Eatclub.entity.CartItem;
import com.alpha.Eatclub.entity.Customer;
import com.alpha.Eatclub.entity.Order;
import com.alpha.Eatclub.entity.Restaurant;
import com.alpha.Eatclub.service.CustomerService;
import com.alpha.Eatclub.service.OrderService;
import com.alpha.Eatclub.service.RestaurantService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private OrderService orderService;
     
	@Autowired
	private RestaurantService restaurantService;
	
	@PostMapping("/register")
	public ResponseEntity<ResponseStructure<Customer>> register(@RequestBody CustomerDTO customerdto) {
		return customerService.register(customerdto);

	}

	@GetMapping("/findcustomer/{phoneno}")
	public ResponseEntity<ResponseStructure<Customer>> findCustomer(@PathVariable String phone) {
		return customerService.findCustomer(phone);

	}

	@DeleteMapping("/deletecustomer/{phoneno}")
	public ResponseEntity<ResponseStructure<Customer>> deleteCustomer(@PathVariable String phone) {
		return customerService.deleteCustomer(phone);

	}

	@PatchMapping("/addtocart/{phone}/{itemId}/{quantity}")
	public ResponseEntity<ResponseStructure<CartItem>> addtoCart(@PathVariable String phone, @PathVariable Long itemId,
			@PathVariable int quantity) {
		return customerService.addtocart(phone, itemId, quantity);

	}
	@GetMapping("/customer/search")
	public ResponseEntity<List<Restaurant>> searchItemOrRestaurant(
	        @RequestParam long phone,
	        @RequestParam String searchKey) {

	    List<Restaurant> result =
	            restaurantService.searchItemOrRestaurant(phone, searchKey);

	    return ResponseEntity.ok(result);
	}
	
	@GetMapping("/customer/getcart/{phone}")
	public ResponseEntity<ResponseStructure<List<CartItem>>> getCart(@PathVariable String phone) {
		return customerService.getCart(phone);
	}
	@PostMapping("/placeorder")
    public ResponseEntity<ResponseStructure<Order>> placeOrder(
            @RequestParam String phone,
            @RequestParam String method,
            @RequestParam String addressType) {

        return orderService.placeOrder(phone, method, addressType);
    }
	
	
	@GetMapping("/AddingtheItem")
	public ResponseEntity<List<Restaurant>> searchItemOrRestaurtant(@RequestParam long mobno,@RequestParam String SearchKey){
		List<Restaurant> result=restaurantService.searchItemOrRestaurant(mobno, SearchKey);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	

}
