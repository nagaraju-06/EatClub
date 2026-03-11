package com.alpha.Eatclub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
     
    @Column(unique=true)
    private long mobno;
    @Column(unique=true)
    private String mailid;

    private String gender;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private List<Address> address;

    @OneToMany( cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> cart;

    @OneToMany(mappedBy = "customer",cascade=CascadeType.ALL,orphanRemoval = true)
    private List<CartItem> cartItems;

private double wallet;

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public long getMobno() {
	return mobno;
}

public void setMobno(long mobno) {
	this.mobno = mobno;
}

public String getMailid() {
	return mailid;
}

public void setMailid(String mailid) {
	this.mailid = mailid;
}

public String getGender() {
	return gender;
}

public void setGender(String gender) {
	this.gender = gender;
}

public List<Address> getAddress() {
	return address;
}

public void setAddress(List<Address> address) {
	this.address = address;
}

public List<Order> getOrders() {
	return orders;
}

public void setOrders(List<Order> orders) {
	this.orders = orders;
}

public List<Item> getCart() {
	return cart;
}

public void setCart(List<Item> cart) {
	this.cart = cart;
}

public List<CartItem> getCartItems() {
	return cartItems;
}

public void setCartItems(List<CartItem> cartItems) {
	this.cartItems = cartItems;
}

public double getWallet() {
	return wallet;
}

public void setWallet(double wallet) {
	this.wallet = wallet;
}

public Customer(int id, String name, long mobno, String mailid, String gender, List<Address> address,
		List<Order> orders, List<Item> cart, List<CartItem> cartItems, double wallet) {
	super();
	this.id = id;
	this.name = name;
	this.mobno = mobno;
	this.mailid = mailid;
	this.gender = gender;
	this.address = address;
	this.orders = orders;
	this.cart = cart;
	this.cartItems = cartItems;
	this.wallet = wallet;
}

public Customer() {
	super();
}

@Override
public String toString() {
	return "Customer [id=" + id + ", name=" + name + ", mobno=" + mobno + ", mailid=" + mailid + ", gender=" + gender
			+ ", address=" + address + ", orders=" + orders + ", cart=" + cart + ", cartItems=" + cartItems
			+ ", wallet=" + wallet + "]";
}



    
}