package com.alpha.Eatclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alpha.Eatclub.dto.DeliveryPartnerDTO;

import com.alpha.Eatclub.entity.Address;
import com.alpha.Eatclub.entity.DeliveryPartner;
import com.alpha.Eatclub.entity.Order;
import com.alpha.Eatclub.repository.AddressRepository;
import com.alpha.Eatclub.repository.DeliveryPartnerRepository;
import com.alpha.Eatclub.repository.OrderRepository;
import javax.print.DocFlavor;
import java.util.Map;
@Service
public class DeliveryPartnerService {
	@Autowired
    private DeliveryPartnerRepository deliveryPartnerRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AddressRepository addressRepository;


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

    public boolean acceptorder(long orderid, Integer partnerid) {
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
}