

package com.alpha.Eatclub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.Eatclub.dto.DeliveryPartnerDTO;
import com.alpha.Eatclub.dto.ResponseStructure;
import com.alpha.Eatclub.entity.DeliveryPartner;
import com.alpha.Eatclub.service.DeliveryPartnerService;

@RestController
	@RequestMapping("/deliverypartner")
	public class DeliveryPartnerController {
		@Autowired
		private DeliveryPartnerService deliveryPartnerService;
		@PostMapping("/register")
		public ResponseEntity<ResponseStructure<DeliveryPartner>> register(@RequestBody DeliveryPartnerDTO deliveryPartnerdto){
			return deliveryPartnerService.register(deliveryPartnerdto); 
		}
		@GetMapping("/finddeliverypartner/{phoneno}")
		public ResponseEntity<ResponseStructure<DeliveryPartner>> findDeliveryPartner(@RequestParam String phone){
			return deliveryPartnerService.findDeliveryPartner(phone);
		}
		@DeleteMapping("/deletedeliverypartner/{phoneno}")
		public ResponseEntity<ResponseStructure<DeliveryPartner>> deleteDeliveryPartner(@RequestParam String phone){
			return deliveryPartnerService.deleteDeliveryPartner(phone);	
		}
}

	
