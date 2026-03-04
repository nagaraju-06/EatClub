package com.alpha.Eatclub.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
	 @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private int id;
	    private String status;

	    @ManyToOne
	    @JoinColumn(name = "restaurant_id")
	    private Restaurant restaurant;
	    @OneToOne
	    private Customer customer;
//	        @ManyToMany
//	    private List<Order> orders;
	    private double cost;
	    @OneToMany
	    private List<Item> items;

	    @OneToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name = "pickup_address_id")
	    private Address pickupAddress;
	    @OneToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name = "delivery_address_id")
	    private Address deliveryAddress;
	    private int otp;
	    @ManyToOne
	    @JoinColumn(name = "delivery_partner_id")
	    private DeliveryPartner deliveryPartner;

	    @OneToOne(mappedBy = "order" ,cascade = CascadeType.ALL)
	    private Payment payment;
	    private String estimatedTime;

	    private Double distance;
	    private double discount;
	    private String coupon;
	    private String specialRequest;
	    private String deliveryInstructions;
	    private LocalDateTime date;
	    
	    private double orderCost;
	    private double delivery_charges;
	    private double packagingFees;
	    private double tax;
	    private double platformFees;
	    private double totalCost;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
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
		public double getCost() {
			return cost;
		}
		public void setCost(double cost) {
			this.cost = cost;
		}
		public List<Item> getItems() {
			return items;
		}
		public void setItems(List<Item> items) {
			this.items = items;
		}
		public Address getPickupAddress() {
			return pickupAddress;
		}
		public void setPickupAddress(Address pickupAddress) {
			this.pickupAddress = pickupAddress;
		}
		public Address getDeliveryAddress() {
			return deliveryAddress;
		}
		public void setDeliveryAddress(Address deliveryAddress) {
			this.deliveryAddress = deliveryAddress;
		}
		public int getOtp() {
			return otp;
		}
		public void setOtp(int otp) {
			this.otp = otp;
		}
		public DeliveryPartner getDeliveryPartner() {
			return deliveryPartner;
		}
		public void setDeliveryPartner(DeliveryPartner deliveryPartner) {
			this.deliveryPartner = deliveryPartner;
		}
		public Payment getPayment() {
			return payment;
		}
		public void setPayment(Payment payment) {
			this.payment = payment;
		}
		public String getEstimatedTime() {
			return estimatedTime;
		}
		public void setEstimatedTime(String estimatedTime) {
			this.estimatedTime = estimatedTime;
		}
		public Double getDistance() {
			return distance;
		}
		public void setDistance(Double distance) {
			this.distance = distance;
		}
		public double getDiscount() {
			return discount;
		}
		public void setDiscount(double discount) {
			this.discount = discount;
		}
		public String getCoupon() {
			return coupon;
		}
		public void setCoupon(String coupon) {
			this.coupon = coupon;
		}
		public String getSpecialRequest() {
			return specialRequest;
		}
		public void setSpecialRequest(String specialRequest) {
			this.specialRequest = specialRequest;
		}
		public String getDeliveryInstructions() {
			return deliveryInstructions;
		}
		public void setDeliveryInstructions(String deliveryInstructions) {
			this.deliveryInstructions = deliveryInstructions;
		}
		public LocalDateTime getDate() {
			return date;
		}
		public void setDate(LocalDateTime date) {
			this.date = date;
		}
		public double getOrderCost() {
			return orderCost;
		}
		public void setOrderCost(double orderCost) {
			this.orderCost = orderCost;
		}
		public double getDelivery_charges() {
			return delivery_charges;
		}
		public void setDelivery_charges(double delivery_charges) {
			this.delivery_charges = delivery_charges;
		}
		public double getPackagingFees() {
			return packagingFees;
		}
		public void setPackagingFees(double packagingFees) {
			this.packagingFees = packagingFees;
		}
		public double getTax() {
			return tax;
		}
		public void setTax(double tax) {
			this.tax = tax;
		}
		public double getPlatformFees() {
			return platformFees;
		}
		public void setPlatformFees(double platformFees) {
			this.platformFees = platformFees;
		}
		public double getTotalCost() {
			return totalCost;
		}
		public void setTotalCost(double totalCost) {
			this.totalCost = totalCost;
		}
		public Order(int id, String status, Restaurant restaurant, Customer customer, double cost, List<Item> items,
				Address pickupAddress, Address deliveryAddress, int otp, DeliveryPartner deliveryPartner,
				Payment payment, String estimatedTime, Double distance, double discount, String coupon,
				String specialRequest, String deliveryInstructions, LocalDateTime date, double orderCost,
				double delivery_charges, double packagingFees, double tax, double platformFees, double totalCost) {
			super();
			this.id = id;
			this.status = status;
			this.restaurant = restaurant;
			this.customer = customer;
			this.cost = cost;
			this.items = items;
			this.pickupAddress = pickupAddress;
			this.deliveryAddress = deliveryAddress;
			this.otp = otp;
			this.deliveryPartner = deliveryPartner;
			this.payment = payment;
			this.estimatedTime = estimatedTime;
			this.distance = distance;
			this.discount = discount;
			this.coupon = coupon;
			this.specialRequest = specialRequest;
			this.deliveryInstructions = deliveryInstructions;
			this.date = date;
			this.orderCost = orderCost;
			this.delivery_charges = delivery_charges;
			this.packagingFees = packagingFees;
			this.tax = tax;
			this.platformFees = platformFees;
			this.totalCost = totalCost;
		}
		public Order() {
			super();
		}
		@Override
		public String toString() {
			return "Order [id=" + id + ", status=" + status + ", restaurant=" + restaurant + ", customer=" + customer
					+ ", cost=" + cost + ", items=" + items + ", pickupAddress=" + pickupAddress + ", deliveryAddress="
					+ deliveryAddress + ", otp=" + otp + ", deliveryPartner=" + deliveryPartner + ", payment=" + payment
					+ ", estimatedTime=" + estimatedTime + ", distance=" + distance + ", discount=" + discount
					+ ", coupon=" + coupon + ", specialRequest=" + specialRequest + ", deliveryInstructions="
					+ deliveryInstructions + ", date=" + date + ", orderCost=" + orderCost + ", delivery_charges="
					+ delivery_charges + ", packagingFees=" + packagingFees + ", tax=" + tax + ", platformFees="
					+ platformFees + ", totalCost=" + totalCost + "]";
		}


	    

	

}