package com.alpha.Eatclub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class CartItem {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    @ManyToOne
	    @JoinColumn(name = "customer_id")
	    @JsonIgnore
	    private Customer customer;

	    @ManyToOne
	    @JoinColumn(name = "Item_id")
	    private Item item;

	    private int quantity;

	    @ManyToOne
	    @JoinColumn(name = "restaurant_id")
	    private Restaurant restaurant;

	    public CartItem(Customer customer, int id, Item item, int quantity) {
	        this.customer = customer;
	        this.id = id;
	        this.item = item;
	        this.quantity = quantity;
	    }

	    public CartItem() {
	    }

	    public CartItem(Item item, int quantity) {
	        this.item=item;
	        this.quantity=quantity;
	    }



	    public Customer getCustomer() {
	        return customer;
	    }

	    public void setCustomer(Customer customer) {
	        this.customer = customer;
	    }

	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public Item getItem() {
	        return item;
	    }

	    public void setItem(Item item) {
	        this.item = item;
	    }

	    public int getQuantity() {
	        return quantity;
	    }

	    public void setQuantity(int quantity) {
	        this.quantity = quantity;
	    }

	    public CartItem(Restaurant restaurant) {
	        this.restaurant = restaurant;
	    }

	    public Restaurant getRestaurant() {
	        return restaurant;
	    }

	    public void setRestaurant(Restaurant restaurant) {
	        this.restaurant = restaurant;
	    } 
}
