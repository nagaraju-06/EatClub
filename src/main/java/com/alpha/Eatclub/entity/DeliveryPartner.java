package com.alpha.Eatclub.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity

public class DeliveryPartner {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @Column(unique=true)
    private long mobno;
    @Column(unique=true)
    private String email;
    private double rating;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "deliveryPartner")
    private List<Order> orders;
    @Column(unique=true)
    private String vehicleNo;
    private String status;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getWallet() {
		return wallet;
	}
	public void setWallet(double wallet) {
		this.wallet = wallet;
	}
	public DeliveryPartner(int id, String name, long mobno, String email, double rating, Address address,
			List<Order> orders, String vehicleNo, String status, double wallet) {
		super();
		this.id = id;
		this.name = name;
		this.mobno = mobno;
		this.email = email;
		this.rating = rating;
		this.address = address;
		this.orders = orders;
		this.vehicleNo = vehicleNo;
		this.status = status;
		this.wallet = wallet;
	}
	public DeliveryPartner() {
		super();
	}
	@Override
	public String toString() {
		return "DeliveryPartner [id=" + id + ", name=" + name + ", mobno=" + mobno + ", email=" + email + ", rating="
				+ rating + ", address=" + address + ", orders=" + orders + ", vehicleNo=" + vehicleNo + ", status="
				+ status + ", wallet=" + wallet + "]";
	}

    
}