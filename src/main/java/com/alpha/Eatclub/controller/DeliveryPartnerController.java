

package com.alpha.Eatclub.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.Eatclub.dto.CartsWithCoupon;
import com.alpha.Eatclub.dto.DeliveryPartnerDTO;
import com.alpha.Eatclub.dto.RestaurantDTO;
import com.alpha.Eatclub.entity.DeliveryPartner;
import com.alpha.Eatclub.entity.Restaurant;
import com.alpha.Eatclub.service.Couponservice;
import com.alpha.Eatclub.service.DeliveryPartnerService;
import com.alpha.Eatclub.service.RedisService;
import com.alpha.Eatclub.service.RestaurantService;
import com.alpha.Eatclub.special.ResponseStructure;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
	@RequestMapping("/deliverypartner")
	public class DeliveryPartnerController {
	@Autowired
    private DeliveryPartnerService deliveryPartnerService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/register")
    public void adding(@RequestBody DeliveryPartnerDTO deliveryPartnerDto) {
        deliveryPartnerService.adding(deliveryPartnerDto);
    }

    @DeleteMapping("/delete/deliveryPartner")
    public void deletePartner(@RequestParam long mobno) {
        deliveryPartnerService.deletePartner(mobno);

    }

    @GetMapping("/find/deliveryPartner")
    public ResponseEntity<DeliveryPartner> findDeliveryPartner(@RequestParam long mobno) {
        DeliveryPartner d = deliveryPartnerService.findDeliveryPartner(mobno);
        return new ResponseEntity<>(d, HttpStatus.OK);
    }


    @PostMapping("/deliveryPartner/updateDpLoc")
    public ResponseEntity<String> updateDpLoc(@RequestParam Integer partnerid, @RequestParam double latitude, double longitude) {
        String s = redisService.updateDpLoc(partnerid, latitude, longitude);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @PostMapping("/deliveryPartner/acceptorder")
    public String acceptorder(@RequestParam Integer orderid, @RequestParam Integer partnerid) {
        boolean accepted = deliveryPartnerService.acceptorder(orderid, partnerid);

        return accepted ? "Order Assigned Successfully" : "Order Already Taken";
    }
    
    @GetMapping("/deliveryPartner/getDirectionToRest")
    public void getDirectionToRest(@RequestParam Integer partnerId,
                                   @RequestParam double restlat, @RequestParam double restlong,
                                   HttpServletResponse resp) throws IOException {
         deliveryPartnerService.getDirectionToRest(partnerId, restlat, restlong, resp);
         
    }
    

    @GetMapping("/deliveryPartner/getDirectionToCust")
    public void getDirectionToCust(@RequestParam double restlat,@RequestParam double restlon,@RequestParam double custlat
                                   ,@RequestParam double custlong,HttpServletResponse rest) throws IOException {
         deliveryPartnerService.getDirectionToCust(restlat,restlon,custlat,custlong,rest);
    }

    @PatchMapping("/deliveryPartner/markOrderAsDelivered")
    public ResponseEntity<String> markOrderAsDelivered(
            @RequestParam long dpMob,
            @RequestParam int orderId,
            @RequestParam int otp) {

        deliveryPartnerService.markOrderAsDelivered(dpMob, orderId, otp);

        return new ResponseEntity<>("Order Delivered Successfully", HttpStatus.OK);
    }
    

        @PostMapping("/payfororder")
        public ResponseEntity<ResponseStructure<String>> payForOrder(
                @RequestParam int customerid,
                @RequestParam long mobno) {

            return deliveryPartnerService.payForOrder(customerid, mobno);
        }
        
        @PatchMapping("/successfulDelivery")
        public ResponseEntity<ResponseStructure<String>> successfulDelivery(@RequestParam long deliverypartnerMobno, @RequestParam int orderid){
        	return deliveryPartnerService.successfulDelivery(deliverypartnerMobno,orderid);
        }
        
        
        @PostMapping("/request/deliveryPartner")
        public void RequestRestuartant(@RequestBody @Valid DeliveryPartnerDTO delPart) {
        	DeliveryPartnerService.RequestDeliveryPartner(delPart);
        }
    }

	
