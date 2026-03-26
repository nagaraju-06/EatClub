package com.alpha.Eatclub.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.alpha.Eatclub.special.ResponseStructure;
import com.alpha.Eatclub.dto.RestaurantDTO;
import com.alpha.Eatclub.entity.Item;
import com.alpha.Eatclub.entity.Restaurant;
import com.alpha.Eatclub.service.DeliveryPartnerService;
import com.alpha.Eatclub.service.RestaurantService;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
	@Autowired
	private RestaurantService restaurantService;
@PostMapping("/register")
public void register (@RequestBody RestaurantDTO restaurantdto){
	restaurantService.adding(restaurantdto);
}

@GetMapping("/findrestaurant/{phoneno}")
public ResponseEntity<ResponseStructure<Restaurant>> findrestaurant(@RequestParam long phone){
	return restaurantService.findRestaurant(phone);
	
}

@DeleteMapping("/deleterestaurant/{mobno}")
public void deleteRestaurant(@PathVariable long mobno) {
    restaurantService.deleteRestaurant(mobno);
}
@PatchMapping("/updatestatus")
public void updateStatus(@RequestParam long mobno){
	restaurantService.updateStatus(mobno);
	}
@PatchMapping("/updateItemAvailability/{phoneno}/{itemid}")
public void updateItemAvailability(@RequestParam long mobno, @RequestParam int itemid){
	restaurantService.updateItemAvailability(mobno, itemid);
}
@PatchMapping("/additemtomenu/{phone}")
public Restaurant addItemToMenu(
        @RequestBody Item item,
        @PathVariable long phone) {

    return restaurantService.addtomenu(item, phone);
}

@PostMapping("/restaurant/cancelOrder")
public ResponseEntity<ResponseStructure<String>> cancelOrder(
        @RequestParam long restaurantMobNo,
        @RequestParam int orderId) {

    return restaurantService.cancelOrder(restaurantMobNo, orderId);
}

 @PostMapping("/request/restuartant")
public void RequestRestuartant(@RequestBody RestaurantDTO restaurtant) {
	RestaurantService.RequestRestuartant(restaurtant);
}
}