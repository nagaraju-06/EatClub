package com.alpha.Eatclub.dto;

public class OrdersNeedsConcentdto {
	private Integer orderid;
	private String restaurtantname;
	private double itemcost;
	private double deliverycharges;
	private double packingfees;
	private double tax;
	private double platformfees;
	private double totalcost;
	private double distance;
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	public String getRestaurtantname() {
		return restaurtantname;
	}
	public void setRestaurtantname(String restaurtantname) {
		this.restaurtantname = restaurtantname;
	}
	public double getItemcost() {
		return itemcost;
	}
	public void setItemcost(double itemcost) {
		this.itemcost = itemcost;
	}
	public double getDeliverycharges() {
		return deliverycharges;
	}
	public void setDeliverycharges(double deliverycharges) {
		this.deliverycharges = deliverycharges;
	}
	public double getPackingfees() {
		return packingfees;
	}
	public void setPackingfees(double packingfees) {
		this.packingfees = packingfees;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public double getPlatformfees() {
		return platformfees;
	}
	public void setPlatformfees(double platformfees) {
		this.platformfees = platformfees;
	}
	public double getTotalcost() {
		return totalcost;
	}
	public void setTotalcost(double totalcost) {
		this.totalcost = totalcost;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public OrdersNeedsConcentdto(Integer orderid, String restaurtantname, double itemcost, double deliverycharges,
			double packingfees, double tax, double platformfees, double totalcost, double distance) {
		super();
		this.orderid = orderid;
		this.restaurtantname = restaurtantname;
		this.itemcost = itemcost;
		this.deliverycharges = deliverycharges;
		this.packingfees = packingfees;
		this.tax = tax;
		this.platformfees = platformfees;
		this.totalcost = totalcost;
		this.distance = distance;
	}
	public OrdersNeedsConcentdto() {
		super();
	}
	
}
