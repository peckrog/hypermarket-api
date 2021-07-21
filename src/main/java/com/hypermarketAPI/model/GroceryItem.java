package com.hypermarketAPI.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import javax.validation.constraints.*;
import javax.validation.Valid;

import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;



public class GroceryItem   {
  
  private @Valid String itemName;
  private @Valid String brand;
  private @Valid LocalDate expirationDate;

  /**
   **/
  public GroceryItem itemName(String itemName) {
    this.itemName = itemName;
    return this;
  }

  

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("itemName")
  @NotNull
  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }/**
   **/
  public GroceryItem brand(String brand) {
    this.brand = brand;
    return this;
  }

  

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("brand")
  @NotNull
  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }/**
   **/
  public GroceryItem expirationDate(LocalDate expirationDate) {
    this.expirationDate = expirationDate;
    return this;
  }

  

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("expirationDate")
  @NotNull
  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(LocalDate expirationDate) {
    this.expirationDate = expirationDate;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GroceryItem groceryItem = (GroceryItem) o;
    return Objects.equals(this.itemName, groceryItem.itemName) &&
        Objects.equals(this.brand, groceryItem.brand) &&
        Objects.equals(this.expirationDate, groceryItem.expirationDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemName, brand, expirationDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GroceryItem {\n");
    
    sb.append("    itemName: ").append(toIndentedString(itemName)).append("\n");
    sb.append("    brand: ").append(toIndentedString(brand)).append("\n");
    sb.append("    expirationDate: ").append(toIndentedString(expirationDate)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


}

