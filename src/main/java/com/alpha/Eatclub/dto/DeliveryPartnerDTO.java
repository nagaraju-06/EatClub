package com.alpha.Eatclub.dto;

import com.alpha.Eatclub.special.LocationCordinates;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class DeliveryPartnerDTO {
	@NotBlank(message = "Enter The Name")
	 private String name;
	 @Min(value = 1000000000L, message="mobno should not Longer Than 1000000000L")
	 @Max(value = 9999999999L , message="mobno should not Longer Than 9999999999L")
	    private long mobno;
	 @Email( message = "Enter The Valid Email")
	    private String email;
	 @NotEmpty(message= "Enter The Valid VechileNo")
	    private String vehicleNo;
	 @NotEmpty(message= "Enter The Valid Locations")
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