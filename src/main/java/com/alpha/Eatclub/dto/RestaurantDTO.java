package com.alpha.Eatclub.dto;

import com.alpha.Eatclub.special.LocationCordinates;

public class RestaurantDTO {
	
	private String name;
    private long mobno;
    private String mailid;
    private LocationCordinates locationCordinate;
    private String description;
    private Double packagingFees;
    private String type;
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
	public LocationCordinates getLocationCordinate() {
		return locationCordinate;
	}
	public void setLocationCordinate(LocationCordinates locationCordinate) {
		this.locationCordinate = locationCordinate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPackagingFees() {
		return packagingFees;
	}
	public void setPackagingFees(Double packagingFees) {
		this.packagingFees = packagingFees;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public RestaurantDTO(String name, long mobno, String mailid, LocationCordinates locationCordinate,
			String description, Double packagingFees, String type) {
		super();
		this.name = name;
		this.mobno = mobno;
		this.mailid = mailid;
		this.locationCordinate = locationCordinate;
		this.description = description;
		this.packagingFees = packagingFees;
		this.type = type;
	}
	public RestaurantDTO() {
		super();
	}


}