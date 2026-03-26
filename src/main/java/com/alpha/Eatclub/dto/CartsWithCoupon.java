package com.alpha.Eatclub.dto;

import java.util.List;

import com.alpha.Eatclub.entity.CartItem;
import com.alpha.Eatclub.entity.Coupon;

import jakarta.validation.constraints.NotEmpty;

public class CartsWithCoupon {
	@NotEmpty(message= "Enter The Valid CartItems")
	private List<CartItem> cartitems;
	@NotEmpty(message= "Enter The Valid Cartotal")
	private double cartTotal;
	@NotEmpty(message= "Enter The Valid Coupon")
	private List<Coupon> coupon;
	public List<CartItem> getCartitems() {
		return cartitems;
	}
	public void setCartitems(List<CartItem> cartitems) {
		this.cartitems = cartitems;
	}
	public double getCartTotal() {
		return cartTotal;
	}
	public void setCartTotal(double cartTotal) {
		this.cartTotal = cartTotal;
	}
	public List<Coupon> getCoupon() {
		return coupon;
	}
	public void setCoupon(List<Coupon> coupon) {
		this.coupon = coupon;
	}
	public CartsWithCoupon(List<CartItem> cartitems, double cartTotal, List<Coupon> coupon) {
		super();
		this.cartitems = cartitems;
		this.cartTotal = cartTotal;
		this.coupon = coupon;
	}
	public CartsWithCoupon() {
		super();
	}
	

}
