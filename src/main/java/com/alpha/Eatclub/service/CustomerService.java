package com.alpha.Eatclub.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.alpha.Eatclub.dto.CustomerDTO;
import com.alpha.Eatclub.dto.CustomerReq;
import com.alpha.Eatclub.entity.Address;
import com.alpha.Eatclub.entity.CartItem;
import com.alpha.Eatclub.entity.Customer;
import com.alpha.Eatclub.entity.DeliveryPartner;
import com.alpha.Eatclub.entity.Item;
import com.alpha.Eatclub.entity.Order;
import com.alpha.Eatclub.entity.Payment;
import com.alpha.Eatclub.entity.Restaurant;
import com.alpha.Eatclub.exceptions.CustomerNotFound;
import com.alpha.Eatclub.exceptions.DifferentResturtantItem;
import com.alpha.Eatclub.exceptions.OrderNotfoundException;
import com.alpha.Eatclub.repository.CartItemRepository;
import com.alpha.Eatclub.repository.CustomerRepository;
import com.alpha.Eatclub.repository.DeliveryPartnerRepository;
import com.alpha.Eatclub.repository.ItemRepository;
import com.alpha.Eatclub.repository.OrderRepository;
import com.alpha.Eatclub.repository.PaymentRepository;
import com.alpha.Eatclub.repository.RestaurantRepository;
import com.alpha.Eatclub.special.DistanceUtil;
import com.alpha.Eatclub.special.ResponseStructure;

@Service
public class CustomerService {
  @Autowired
  private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerrepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private DeliveryPartnerRepository deliverypartnerrepoo;
    @Autowired
    private RestaurantRepository restaurantRepository;

    public void adding(CustomerReq customerReqDto) {
        Customer customer = new Customer();
         customer.setName(customerReqDto.getName());
         customer.setMobno(customerReqDto.getMobno());
         customer.setMailid(customerReqDto.getMailid());
         customer.setGender(customerReqDto.getGender());
         customerrepository.save(customer);
    }

    public void deleteCustomer(long mobno) {
       Customer c= customerrepository.findByMobno(mobno).orElseThrow(()->new RuntimeException("Customer not found"));
       customerrepository.delete(c);

    }

    public Customer findCustomer(long mobno) {
        return  customerrepository.findByMobno(mobno).orElseThrow(()->new RuntimeException("Customer not found"));



    }

    //

    public void addtocart(long mobno, long itemid, int quantity) {
        Customer customer=customerrepository.findByMobno(mobno).orElseThrow(()->new RuntimeException("Customer not found"));
       Item item =itemRepository.findById(itemid).orElseThrow(()->new RuntimeException("Item not found"));

        CartItem c1=new CartItem();
        c1.setQuantity(quantity);
        c1.setItem(item);
        c1.setCustomer(customer);
        c1.setRestaurant(item.getRestaurant());
        customer.getCartItems().add(c1);
        cartItemRepository.save(c1);


    }

    public Customer saveCustomer(CustomerReq dto) {

        Customer customer=new Customer();
        customer.setName(dto.getName());
        customer.setMobno(dto.getMobno());
        customer.setMailid(dto.getMailid());
        customer.setGender(dto.getGender());


        //new
//        Address address=new Address();
//
//        Map response=restTemplate.getForObject("https://us1.locationiq.com/v1/reverse?key=pk.5038d98b114a8653a2d8716f69a70c50"
//                + "&lat="+dto.getAddresses().getLocationCordinate().getLatitude() +
//                "&lon="+dto.getAddresses().getLocationCordinate().getLongitute()+ "&format=json", Map.class
//        );
//
//        Map add=(Map) response.get("address");
//        address.setPincode((String) add.get("postcode"));
//        address.setCity((String) add.get("city"));
//        address.setCountry((String) add.get("country"));
//        address.setState((String) add.get("state"));
//        address.setStreet((String) add.get("neighbourhood"));
//        address.setFlatNumber(dto.getAddresses().getFlatNumber());
//        address.setBuildingName(dto.getAddresses().getBuildingName());
//        address.setAddressType(dto.ge);

        List<Address> addressList =new ArrayList<>();
        for(CustomerDTO adto: dto.getAddresses()){
            Address address=new Address();
            address.setFlatNumber(adto.getFlatNumber());
            address.setBuildingName(adto.getBuildingName());
            address.setStreet(adto.getStreet());
            address.setCity(adto.getCity());
            address.setState(adto.getState());
            address.setPincode(adto.getPincode());
            address.setAddressType(adto.getAddressType());
            address.setIsDefault(adto.getIsDefault());
            addressList.add(address);

        }
        customer.setAddress(addressList);
       return customerrepository.save(customer);
    }


    public CartItem addtocartt(long mobno, long itemid, int quantity) {
         Customer customer=customerrepository.findByMobno(mobno).orElseThrow(()->new RuntimeException("Customer not found"));
        Item item = itemRepository.findById(itemid).orElseThrow(() -> new RuntimeException("Item not found"));

         List<CartItem> cart=customer.getCartItems();
        if(cart.isEmpty()){
            CartItem cartItem=new CartItem(item,quantity);
             cartItem.setCustomer(customer);
             cartItem.setRestaurant(item.getRestaurant());
             cart.add(cartItem);
             customerrepository.save(customer);
             return cartItem;


        }else{

            Restaurant existingRestaurant = cart.get(0).getRestaurant();
            Restaurant newRestaurant=item.getRestaurant();
            if(! ((existingRestaurant.getId()) == (newRestaurant.getId()) ) ) {
                throw new DifferentResturtantItem("Cannot add item from different restaurant");
            }
               //if item already present
               Optional<CartItem> existingItem=cart.stream().filter(ci->ci.getItem().getId()==itemid)
                           .findFirst();
               if(existingItem.isPresent()){
                   CartItem cartItem=existingItem.get();
                   cartItem.setQuantity(cartItem.getQuantity()+ quantity);
                   customerrepository.save(customer);
                   return cartItem;
               }
               //new Item from same restaurant
                   CartItem cartItem=new CartItem(item,quantity);
                   cartItem.setCustomer(customer);
                   cartItem.setRestaurant(item.getRestaurant());
                   cart.add(cartItem);
            customerrepository.save(customer);
                   return cartItem;

           }


        }

    public List<CartItem> getAllCart(long mobno) {
        Customer customer = customerrepository.findByMobno(mobno).orElseThrow(() -> new RuntimeException("Customer not found"));
        List<CartItem> cartItems = customer.getCartItems();
        return  cartItems;


    }

    public ResponseEntity<ResponseStructure<Order>> placingOrder(long mobno, String paymentType, String addressType, String specialRequest) {
        Customer customer = customerrepository.findByMobno(mobno).orElseThrow(() -> new CustomerNotFound("Cust not found"));

        if(customer.getCartItems().isEmpty()){
            throw new RuntimeException("Cart is empty");
        }
        Order order=new Order();
        order.setCustomer(customer);
        order.setStatus("Placed");

        Restaurant restaurant = customer.getCartItems().get(0).getItem().getRestaurant();
         order.setRestaurant(restaurant);
           Address pickupAddress=restaurant.getAddress();
         order.setPickupAddress(pickupAddress);
         Address delivAddress=null;
         for(Address a:customer.getAddress()){
             if(a.getAddressType().equals(addressType)){
                 delivAddress=a;
             }
         }
        order.setDeliveryAddress(delivAddress);

         order.setSpecialRequest(specialRequest);
         order.setDeliveryInstructions("Make it Spicy");
         order.setDiscount(0);
         order.setCoupon(null);
         order.setDeliveryPartner(null);
         order.setDate(LocalDateTime.now());

         //Distance
        double distance= DistanceUtil.calculateDistance(pickupAddress.getLatitude(),pickupAddress.getLongitude()
        ,delivAddress.getLatitude(),delivAddress.getLongitude());

         order.setDistance(distance);
         double delivery_charge=0;
         if(distance>2){
             delivery_charge= (distance-2)*10;
         }
         delivery_charge=Math.round(delivery_charge);
         double cost=0;

         for( CartItem c:customer.getCartItems()){

             cost=cost+(c.getItem().getPrice()*c.getQuantity());
                     }
        double FinalCost= cost + delivery_charge + restaurant.getPackagingFees();

         order.setCost(FinalCost);

          Payment payment=new Payment();
          payment.setAmount(FinalCost);
          payment.setType(paymentType);
          if(paymentType.equalsIgnoreCase("COD")){
              payment.setStatus("Pending");
          }else{
              payment.setStatus("Paid");
          }
          order.setPayment(payment);
           payment.setOrder(order);


        SecureRandom random=new SecureRandom();
        int otp= 1000 + random.nextInt(9000);
        order.setOtp(otp);


        Order orderinitiated=  orderRepository.save(order);
        ResponseStructure<Order> rs = new ResponseStructure<>();
        rs.setData(orderinitiated);
        rs.setMessage("Order Initiated,Do you wish to Confirm Order");
        rs.setStatuscode(200);
        return new ResponseEntity<ResponseStructure<Order>>(rs, HttpStatus.OK);
    }


    public ResponseEntity<ResponseStructure<String>> denyPlacingOrder(int orderid) {

        Order order = orderRepository.findById(orderid)
                .orElseThrow(() -> new OrderNotfoundException("Order not found with this id"));

        order.setStatus("Cancelled");
        orderRepository.save(order);

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setData("Denied");
        rs.setMessage("Order has been Cancelled Successfully");

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<String>> confirmPlacingOrder(int orderid) {

        Order order = orderRepository.findById(Integer.valueOf(orderid))
                .orElseThrow(() -> new OrderNotfoundException("Order not found with this id"));

        Customer customer = order.getCustomer();
        Restaurant restaurant = customer.getCartItems().get(0).getItem().getRestaurant();

        order.setRestaurant(restaurant);
        order.setStatus("Placed");

        orderRepository.save(order);

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setData("Success");
        rs.setMessage("Order Placed Successfully");

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }
    public ResponseEntity<ResponseStructure<Order>> confirmOrder(long customerMobNo, int orderId) {

        // find customer
        Customer cust = customerrepository.findByMobno(customerMobNo).orElse(null);

        // find order
        Order order = orderRepository.findById((int) orderId).orElse(null);

        // get restaurant
        Restaurant rest = order.getRestaurant();

        // check delivery partner
        if (order.getDeliveryPartner() == null) {
            order.setStatus("CONFIRMED");
        }

        // payment type check
        if (order.getPayment().getType().equalsIgnoreCase("ONLINE")) {

            // deduct from customer wallet
            cust.setWallet(cust.getWallet() - order.getTotalCost());

            // add money to restaurant wallet
            rest.setWallet(rest.getWallet() + order.getTotalCost());

        } 
        else if (order.getPayment().getType().equalsIgnoreCase("COD")) {

            // find delivery partner (example logic
        	DeliveryPartner dp = deliverypartnerrepoo.findTopByStatus("AVAILABLE");

            order.setDeliveryPartner(dp);
        }

        // save updates
        customerrepository.save(cust);
        restaurantRepository.save(rest);
        orderRepository.save(order);

        ResponseStructure<Order> rs = new ResponseStructure<>();
        rs.setStatuscode(HttpStatus.OK.value());
        rs.setMessage("Order Confirmed Successfully");
        rs.setData(order);

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }


    public ResponseEntity<ResponseStructure<String>> payfororder(int customerid, int orderid) {

        ResponseStructure<String> rs = new ResponseStructure<>();

        Customer cust = customerrepository.findById(customerid).orElse(null);
        Order order = orderRepository.findById(orderid).orElse(null);

        if (cust == null || order == null) {
            rs.setStatuscode(HttpStatus.NOT_FOUND.value());
            rs.setMessage("Customer or Order not found");
            rs.setData(null);

            return new ResponseEntity<>(rs, HttpStatus.NOT_FOUND);
        }

        // get order amount
        double amount = order.getTotalCost();

        // check payment type
        String paymentType = order.getPayment().getType();

        if (paymentType.equalsIgnoreCase("ONLINE")) {

            // deduct wallet amount
            cust.setWallet(cust.getWallet() - amount);

            order.setStatus("CONFIRMED");
        }

        else if (paymentType.equalsIgnoreCase("COD")) {

            DeliveryPartner dp = deliverypartnerrepoo.findTopByStatus("AVAILABLE");

            if (dp != null) {
                order.setDeliveryPartner(dp);
                dp.setStatus("BUSY");
            }

            order.setStatus("CONFIRMED");
        }

        customerrepository.save(cust);
        orderRepository.save(order);

        rs.setStatuscode(HttpStatus.OK.value());
        rs.setMessage("Order placed successfully");
        rs.setData("Payment completed for order id " + orderid);

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

	public void reqCustomerToValidData(CustomerReq custReq) {
		System.out.println(custReq );
		System.out.println("Data Is Valid");
		
	}
}



	
