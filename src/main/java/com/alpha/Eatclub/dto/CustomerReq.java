package com.alpha.Eatclub.dto;

import java.util.List;

public class CustomerReq {

	 private String name;
	    private long mobno;
	    private String mailid;
	    private  String gender;

	    private List<CustomerDTO> addresses;



	    public List<CustomerDTO> getAddresses() {
	        return addresses;
	    }

	    public void setAddresses(List<CustomerDTO> addresses) {
	        this.addresses = addresses;
	    }

	    public String getGender() {
	        return gender;
	    }

	    public void setGender(String gender) {
	        this.gender = gender;
	    }

	    public String getMailid() {
	        return mailid;
	    }

	    public void setMailid(String mailid) {
	        this.mailid = mailid;
	    }

	    public long getMobno() {
	        return mobno;
	    }

	    public void setMobno(long mobno) {
	        this.mobno = mobno;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public CustomerReq(String gender, String mailid, long mobno, String name) {
	        this.gender = gender;
	        this.mailid = mailid;
	        this.mobno = mobno;
	        this.name = name;
	    }




	    public CustomerReq() {
	    }
	}