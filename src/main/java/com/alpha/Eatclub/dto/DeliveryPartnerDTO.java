package com.alpha.Eatclub.dto;

import com.alpha.Eatclub.special.LocationCordinates;

public class DeliveryPartnerDTO {
	
	 private String name;
	    private long mobno;
	    private String email;
	    private String vehicleNo;
	    private LocationCordinates locationCordinate;
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
		public String getVehicleNo() {
			return vehicleNo;
		}
		public void setVehicleNo(String vehicleNo) {
			this.vehicleNo = vehicleNo;
		}
		public LocationCordinates getLocationCordinate() {
			return locationCordinate;
		}
		public void setLocationCordinate(LocationCordinates locationCordinate) {
			this.locationCordinate = locationCordinate;
		}
		public DeliveryPartnerDTO(String name, long mobno, String email, String vehicleNo,
				LocationCordinates locationCordinate) {
			super();
			this.name = name;
			this.mobno = mobno;
			this.email = email;
			this.vehicleNo = vehicleNo;
			this.locationCordinate = locationCordinate;
		}

	   
	}