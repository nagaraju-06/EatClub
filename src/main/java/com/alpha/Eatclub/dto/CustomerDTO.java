package com.alpha.Eatclub.dto;

import jakarta.validation.constraints.NotEmpty;

public class CustomerDTO {
	@NotEmpty(message= "Enter The Valid flatNumber")
    private String flatNumber;
	@NotEmpty(message= "Enter The Valid Buidling name")
    private String buildingName;
	@NotEmpty(message= "Enter The Valid Street")
    private String street;
	@NotEmpty(message= "Enter The Valid City")
    private String city;
	@NotEmpty(message= "Enter The Valid State")
    private String state;
	@NotEmpty(message= "Enter The Valid Pincodel")
    private String pincode;
	@NotEmpty(message= "Enter The Valid AddressType")
    private String addressType;
    private Boolean isDefault;

    public CustomerDTO(String addressType, String buildingName, String city, String flatNumber, Boolean isDefault, String pincode, String state, String street) {
        this.addressType = addressType;
        this.buildingName = buildingName;
        this.city = city;
        this.flatNumber = flatNumber;
        this.isDefault = isDefault;
        this.pincode = pincode;
        this.state = state;
        this.street = street;
    }

    public CustomerDTO() {
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}