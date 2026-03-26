package com.alpha.Eatclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alpha.Eatclub.dto.DeliveryPartnerDTO;
import com.alpha.Eatclub.dto.RestaurantDTO;
import com.alpha.Eatclub.entity.Address;
import com.alpha.Eatclub.entity.Customer;
import com.alpha.Eatclub.entity.DeliveryPartner;
import com.alpha.Eatclub.entity.Order;
import com.alpha.Eatclub.entity.Restaurant;
import com.alpha.Eatclub.repository.AddressRepository;
import com.alpha.Eatclub.repository.CustomerRepository;
import com.alpha.Eatclub.repository.DeliveryPartnerRepository;
import com.alpha.Eatclub.repository.OrderRepository;
import com.alpha.Eatclub.repository.RestaurantRepository;
import com.alpha.Eatclub.special.ResponseStructure;

import jakarta.servlet.http.HttpServletResponse;

import javax.print.DocFlavor;
import org.springframework.data.geo.Point;

import java.io.IOException;
import java.util.List;
import java.util.Map;
@Service
public class DeliveryPartnerService {
	@Autowired
    private DeliveryPartnerRepository deliveryPartnerRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private CustomerRepository customerrepoo;
    
    @Autowired
    private RestaurantRepository resturtantsrepoo;


    public void adding(DeliveryPartnerDTO deliveryPartnerDto) {
        DeliveryPartner deliveryPartner = new DeliveryPartner();
        deliveryPartner.setName(deliveryPartnerDto.getName());
        deliveryPartner.setMobno(deliveryPartnerDto.getMobno());
        deliveryPartner.setEmail(deliveryPartnerDto.getEmail());
        deliveryPartner.setVehicleNo(deliveryPartnerDto.getVehicleNo());

        Address address = new Address();

        Map response = restTemplate.getForObject("https://us1.locationiq.com/v1/reverse?key=pk.5038d98b114a8653a2d8716f69a70c50"
                + "&lat=" + deliveryPartnerDto.getLocationCordinate().getLatitude() +
                "&lon=" + deliveryPartnerDto.getLocationCordinate().getLongitude() + "&format=json", Map.class
        );
        Map add = (Map) response.get("address");
        address.setPincode((String) add.get("postcode"));
        address.setCity((String) add.get("city"));
        address.setCountry((String) add.get("country"));
        address.setState((String) add.get("state"));
        address.setLatitude(deliveryPartnerDto.getLocationCordinate().getLatitude());
        address.setLongitude(deliveryPartnerDto.getLocationCordinate().getLongitude());
        deliveryPartner.setAddress(address);
        addressRepository.save(address);

        deliveryPartnerRepository.save(deliveryPartner);
    }
    

    public void getDirectionToRest(Integer partnerId, double restlat,
                                   double restlong, HttpServletResponse resp) throws IOException {

        String key="deliverypartner:location";
        List<Point> points = redisTemplate.opsForGeo().position(key, partnerId.toString());

        if(points== null || points.isEmpty()){
            throw new RuntimeException("Delivery Partner Location not found");
        }
              Point p =points.get(0);
        double  dplon= p.getX();
       double dplat= p.getY();


        String getdir="https://www.google.com/maps/dir/?api=1&origin="+dplat+","+dplon+"&destination="+restlat+
                ","+restlong+"&travelmode=driving";
        resp.sendRedirect(getdir);

    }
    
    

    public void deletePartner(long mobno) {
    	DeliveryPartner d = deliveryPartnerRepository.findByMobno(mobno)
                .orElseThrow(() -> new RuntimeException("Partner not found"));
        deliveryPartnerRepository.delete(d);

    }

    public DeliveryPartner findDeliveryPartner(long mobno) {
        return deliveryPartnerRepository.findByMobno(mobno)
                .orElseThrow(() -> new RuntimeException("Partner not found"));
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private OrderRepository orderRepository;

    public boolean acceptorder(int orderid, Integer partnerid) {
        Order order = orderRepository.findById(orderid).orElseThrow(() -> new RuntimeException("Order not found"));
        DeliveryPartner deliveryPartner = deliveryPartnerRepository.findById(partnerid).orElseThrow(()
                -> new RuntimeException("partner not found"));


        String lockKey = "order_lock" + orderid;
        Boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, String.valueOf(partnerid));
        if (Boolean.TRUE.equals(locked)) {
            order.setDeliveryPartner(deliveryPartner);
            orderRepository.save(order);
            redisTemplate.delete("order:" + orderid);

            return true;
        }
        return false;
    }

    public void getDirectionToCust(double restlat, double restlon, double custlat, double custlong, HttpServletResponse rest) throws IOException {

        String getdir="https://www.google.com/maps/dir/?api=1&origin="+restlat+","+restlon+"&destination="+custlat+
                ","+custlong+"&travelmode=driving";
        rest.sendRedirect(getdir);
    }
    


    public void markOrderAsDelivered(long dpMob, int orderId, int otp) {
        DeliveryPartner dp = deliveryPartnerRepository
                .findByMobno(dpMob)
                .orElseThrow(() -> new RuntimeException("Delivery Partner Not Found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order Not Found"));
        if(order.getDeliveryPartner().getId() != dp.getId()){
            throw new RuntimeException("Order not assigned to this delivery partner");
        }
    }




	public ResponseEntity<ResponseStructure<String>> payForOrder(int customerid, long restaurtantid) {
		Customer customer=customerrepoo.findById(customerid).orElse(null);
		Restaurant rest = resturtantsrepoo.findById(restaurtantid).orElse(null);
		ResponseStructure<String> rs=new ResponseStructure<>();
		if(customer==null || rest==null) {
			rs.setStatuscode(404);
			rs.setMessage("Customer or restaurtant not found");
			rs.setData(null);
			return new ResponseEntity<>(rs,HttpStatus.NOT_FOUND);
		}
		rs.setStatuscode(200);
		rs.setMessage("Payment Succesfull");
		rs.setData("Order Paid Succesfull");
		return new ResponseEntity<>(rs,HttpStatus.OK);
	}


	public ResponseEntity<ResponseStructure<String>> successfulDelivery(long deliverypartnerMobno, int orderid) {

		DeliveryPartner dp = deliveryPartnerRepository.findByMobno(deliverypartnerMobno).orElse(null);

	    if (dp == null) {
	        throw new RuntimeException("Delivery Partner not found");
	    }

	    Order order = orderRepository.findById(orderid).orElse(null);

	    if (order == null) {
	        throw new RuntimeException("Order not found");
	    }

	    Restaurant restaurant = order.getRestaurant();

	    // Update status
	    order.setStatus("Delivered");

	    double orderAmount = order.getTotalCost();
	    double deliveryCharge = order.getDelivery_charges();

	    double amount = orderAmount - deliveryCharge;

	    double restaurantShare = amount * 85 / 100;
	    double dpShare = (amount * 10 / 100) + deliveryCharge;

	    // Update wallets
	    restaurant.setWallet(restaurant.getWallet() + restaurantShare);
	    dp.setWallet(dp.getWallet() + dpShare);

	    // Save
	    orderRepository.save(order);
	    resturtantsrepoo.save(restaurant);
	    deliveryPartnerRepository.save(dp);

	    ResponseStructure<String> rs = new ResponseStructure<>();
	    rs.setStatuscode(HttpStatus.OK.value());
	    rs.setMessage("Order Delivered Successfully");
	    rs.setData("Payment distributed successfully");

	    return new ResponseEntity<>(rs, HttpStatus.OK);
	}


	public static void RequestDeliveryPartner(DeliveryPartnerDTO delPart) {
		System.out.println(delPart);
		System.out.println("Data Is Valid");
		
	}



}