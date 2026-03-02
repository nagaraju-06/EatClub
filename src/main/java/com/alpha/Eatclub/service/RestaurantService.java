package com.alpha.Eatclub.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alpha.Eatclub.dto.ResponseStructure;
import com.alpha.Eatclub.dto.RestaurantDTO;
import com.alpha.Eatclub.entity.Address;
import com.alpha.Eatclub.entity.Customer;
import com.alpha.Eatclub.entity.Item;
import com.alpha.Eatclub.entity.Restaurant;
import com.alpha.Eatclub.exceptions.ResourceNotFoundException;
import com.alpha.Eatclub.repository.CustomerRepository;
import com.alpha.Eatclub.repository.RestaurantRepository;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private RestTemplate restTemplate;

    // ================= REGISTER =================

    public ResponseEntity<ResponseStructure<Restaurant>> register(RestaurantDTO restaurantdto) {

        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantdto.getName());
        restaurant.setPhone(restaurantdto.getPhone());
        restaurant.setEmail(restaurantdto.getEmail());
        restaurant.setDescription(restaurantdto.getDescription());
        restaurant.setPackagefees(restaurantdto.getPackagefees());
        restaurant.setType(restaurantdto.getType());
        restaurant.setStatus("Open");   // Default status

        String url = "https://us1.locationiq.com/v1/reverse?key=pk.e13376a26985e3fd5361223a1ed9aabb&lat="
                + restaurantdto.getCordinates().getLatitude()
                + "&lon=" + restaurantdto.getCordinates().getLongitude()
                + "&format=json&";

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        Map<String, Object> addressMap = (Map<String, Object>) response.get("address");

        Address address = new Address();
        address.setLatitude(restaurantdto.getCordinates().getLatitude());
        address.setLongitude(restaurantdto.getCordinates().getLongitude());

        address.setArea((String) addressMap.get("suburb"));
        address.setCity((String) addressMap.get("city"));
        address.setDistrict((String) addressMap.get("county"));
        address.setState((String) addressMap.get("state"));
        address.setCountry((String) addressMap.get("country"));
        address.setPincode((String) addressMap.get("postcode"));

        restaurant.setAddress(address);

        restaurantRepo.save(restaurant);

        ResponseStructure<Restaurant> rs = new ResponseStructure<>();
        rs.setStatuscode(HttpStatus.CREATED.value());
        rs.setMessage("Restaurant saved successfully");
        rs.setData(restaurant);

        return new ResponseEntity<>(rs, HttpStatus.CREATED);
    }

    // ================= FIND =================

    public ResponseEntity<ResponseStructure<Restaurant>> findRestaurant(String phone) {

        Restaurant restaurant = restaurantRepo.findByPhone(phone);

        if (restaurant == null) {
            throw new ResourceNotFoundException("Restaurant not found with phone: " + phone);
        }

        ResponseStructure<Restaurant> rs = new ResponseStructure<>();
        rs.setStatuscode(HttpStatus.OK.value());
        rs.setMessage("Restaurant fetched successfully");
        rs.setData(restaurant);

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    // ================= DELETE =================

    public ResponseEntity<ResponseStructure<String>> deleteRestaurant(String phone) {

        Restaurant restaurant = restaurantRepo.findByPhone(phone);

        if (restaurant == null) {
            throw new ResourceNotFoundException("Restaurant not found");
        }

        restaurantRepo.delete(restaurant);

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatuscode(HttpStatus.OK.value());
        rs.setMessage("Restaurant deleted successfully");
        rs.setData("Deleted");

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    // ================= UPDATE STATUS =================

    public ResponseEntity<ResponseStructure<String>> updateStatus(String phone) {

        Restaurant restaurant = restaurantRepo.findByPhone(phone);

        if (restaurant == null) {
            throw new ResourceNotFoundException("Restaurant not found");
        }

        if ("Open".equalsIgnoreCase(restaurant.getStatus())) {
            restaurant.setStatus("Close");
        } else {
            restaurant.setStatus("Open");
        }

        restaurantRepo.save(restaurant);

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatuscode(HttpStatus.OK.value());
        rs.setMessage("Restaurant status updated successfully");
        rs.setData(restaurant.getStatus());

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    // ================= UPDATE ITEM AVAILABILITY =================

    public ResponseEntity<ResponseStructure<String>> updateItemAvailability(String phone, Long itemid) {

        Restaurant restaurant = restaurantRepo.findByPhone(phone);

        if (restaurant == null) {
            throw new ResourceNotFoundException("Restaurant not found");
        }

        List<Item> listItem = restaurant.getMenuItems();

        if (listItem == null || listItem.isEmpty()) {
            throw new ResourceNotFoundException("No items found in menu");
        }

        boolean found = false;

        for (Item item : listItem) {
            if (item.getId().equals(itemid)) {
                item.setAvailability("Available");
                found = true;
                break;
            }
        }

        if (!found) {
            throw new ResourceNotFoundException("Item not found with id: " + itemid);
        }

        restaurantRepo.save(restaurant);

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatuscode(HttpStatus.OK.value());
        rs.setMessage("Item availability updated successfully");
        rs.setData("Updated item id: " + itemid);

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    // ================= ADD ITEM =================

    public ResponseEntity<ResponseStructure<Item>> addItemToMenu(Item item, String phone) {

        Restaurant restaurant = restaurantRepo.findByPhone(phone);

        if (restaurant == null) {
            throw new ResourceNotFoundException("Restaurant not found");
        }

        Item saveItem = new Item();
        saveItem.setName(item.getName());
        saveItem.setAvailability(item.getAvailability());
        saveItem.setDescription(item.getDescription());
        saveItem.setPrice(item.getPrice());
        saveItem.setType(item.getType());
        saveItem.setRating(item.getRating());
        saveItem.setNumberOfReviews(item.getNumberOfReviews());
        saveItem.setRestaurant(restaurant);

        if (restaurant.getMenuItems() == null) {
            restaurant.setMenuItems(new ArrayList<>());
        }

        restaurant.getMenuItems().add(saveItem);

        restaurantRepo.save(restaurant);

        ResponseStructure<Item> rs = new ResponseStructure<>();
        rs.setStatuscode(HttpStatus.OK.value());
        rs.setMessage("Item added successfully");
        rs.setData(saveItem);

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    // ================= SEARCH =================

    public List<Restaurant> searchItemOrRestaurant(String phone, String searchKey) {

        Customer customer = customerRepo.findByPhone(phone);

        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found");
        }

        if (customer.getAddresses() == null || customer.getAddresses().isEmpty()) {
            throw new ResourceNotFoundException("Customer address not found");
        }

        String city = customer.getAddresses().get(0).getCity();

        List<Restaurant> restaurants = restaurantRepo.findByAddress_City(city);

        return restaurants.stream()
                .filter(r -> r.getMenuItems() != null &&
                        r.getMenuItems().stream()
                                .anyMatch(menu ->
                                        menu.getName().toLowerCase()
                                                .contains(searchKey.toLowerCase())))
                .toList();
    }
}