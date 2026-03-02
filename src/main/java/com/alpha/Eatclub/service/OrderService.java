package com.alpha.Eatclub.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.Eatclub.dto.ResponseStructure;
import com.alpha.Eatclub.entity.Address;
import com.alpha.Eatclub.entity.CartItem;
import com.alpha.Eatclub.entity.Customer;
import com.alpha.Eatclub.entity.Order;
import com.alpha.Eatclub.repository.CustomerRepository;
import com.alpha.Eatclub.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private OrderRepository oderRepo;

	public ResponseEntity<ResponseStructure<Order>> placeOrder(String phone, String method, String addressType) {

	    // 1️⃣ Find Customer
	    Customer customer = customerRepo.findByPhone(phone);
	    if (customer == null) {
	        throw new RuntimeException("Customer not found");
	    }

	    // 2️⃣ Validate Cart
	    List<CartItem> cartItems = customer.getCart();
	    if (cartItems == null || cartItems.isEmpty()) {
	        throw new RuntimeException("Cart is empty");
	    }

	    // 3️⃣ Calculate Total Cost
	    double total = cartItems.stream()
	            .mapToDouble(item -> item.getItem().getPrice() * item.getQuantity())
	            .sum();

	    // 4️⃣ Create Order
	    Order order = new Order();
	    order.setStatus("PENDING");
	    order.setCost(total);

	    // 5️⃣ Payment Logic
	    if ("COD".equalsIgnoreCase(method)) {
	        order.setPaymentStatus("PENDING");
	    } else {
	        order.setPaymentStatus("COMPLETED");
	    }

	    order.setOrderTime(LocalDateTime.now());
	    order.setDeliveryStatus("PENDING");

	    // 6️⃣ Set Restaurant (take from first cart item)
	    order.setRestaurant(cartItems.get(0).getRestaurant());

	    // 7️⃣ Set Customer
	    order.setCustomer(customer);

	    // 8️⃣ Set Items
	    order.setItems(cartItems);

	    // 9️⃣ Set Pickup Address (Restaurant Address)
	    order.setPickupAddress(cartItems.get(0).getRestaurant().getAddress());

	    // 🔟 Find Delivery Address
	    Address deliveryAddress = null;

	    if (customer.getAddresses() != null) {
	        for (Address a : customer.getAddresses()) {
	            if (a.getAddressType().equalsIgnoreCase(addressType)) {
	                deliveryAddress = a;
	                break;
	            }
	        }
	    }

	    if (deliveryAddress == null) {
	        throw new RuntimeException("Delivery address not found");
	    }

	    order.setDeliveryAddress(deliveryAddress);

	    // 1️⃣1️⃣ SAVE ORDER
	    order = oderRepo.save(order);

	    // 1️⃣2️⃣ Clear Customer Cart (Very Important)
	    cartItems.clear();
	    customerRepo.save(customer);

	    // 1️⃣3️⃣ Prepare Response
	    ResponseStructure<Order> response = new ResponseStructure<>();
	    response.setStatuscode(HttpStatus.ACCEPTED.value());
	    response.setMessage("Order placed successfully");
	    response.setData(order);

	    return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
}
