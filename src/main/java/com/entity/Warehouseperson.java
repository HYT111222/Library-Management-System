package com.entity;


public class Warehouseperson {

  private long warehousePersonId;
  private String username;
  private long customerId;
  private String name;
  private String contactNumber;
  private String address;
  private String email;
  private String remark;


  public long getWarehousePersonId() {
    return warehousePersonId;
  }

  public void setWarehousePersonId(long warehousePersonId) {
    this.warehousePersonId = warehousePersonId;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(long customerId) {
    this.customerId = customerId;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getContactNumber() {
    return contactNumber;
  }

  public void setContactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
  }


  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

}
