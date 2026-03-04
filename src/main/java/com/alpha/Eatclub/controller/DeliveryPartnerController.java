

package com.alpha.Eatclub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.Eatclub.dto.DeliveryPartnerDTO;
import com.alpha.Eatclub.entity.DeliveryPartner;
import com.alpha.Eatclub.service.DeliveryPartnerService;
import com.alpha.Eatclub.service.RedisService;

@RestController
	@RequestMapping("/deliverypartner")
	public class DeliveryPartnerController {
	@Autowired
    private DeliveryPartnerService deliveryPartnerService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/deliveryPartner/register")
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

}
	
