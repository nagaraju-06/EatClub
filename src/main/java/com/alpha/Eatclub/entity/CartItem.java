package com.alpha.Eatclub.entity;

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
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private int id;
	 @OneToOne
	 private Item item;
	 private int quantity;
	 @ManyToOne
	 @JoinColumn(name = "restaurant_id")
	 private Restaurant restaurant;
	 @ManyToOne
	 @JoinColumn(name = "customer_id")
	 private Customer customer;
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
	 public Restaurant getRestaurant() {
		 return restaurant;
	 }
	 public void setRestaurant(Restaurant restaurant) {
		 this.restaurant = restaurant;
	 }
	 public Customer getCustomer() {
		 return customer;
	 }
	 public void setCustomer(Customer customer) {
		 this.customer = customer;
	 }
	 public CartItem(int id, Item item, int quantity, Restaurant restaurant, Customer customer) {
		super();
		this.id = id;
		this.item = item;
		this.quantity = quantity;
		this.restaurant = restaurant;
		this.customer = customer;
	 }
	 public CartItem() {
		super();
	 }
	 @Override
	 public String toString() {
		return "CartItem [id=" + id + ", item=" + item + ", quantity=" + quantity + ", restaurant=" + restaurant
				+ ", customer=" + customer + "]";
	 }
	
}
