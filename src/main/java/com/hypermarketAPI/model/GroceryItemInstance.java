package com.hypermarketAPI.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


public class GroceryItemInstance extends GroceryItem {

  private @Valid
  Long id;
  private @Valid
  Long hypermarketId;

  /**
   *
   **/
  public GroceryItemInstance id(Long id) {
    this.id = id;
    return this;
  }


  @ApiModelProperty(required = true, value = "")
  @JsonProperty("id")
  @NotNull
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
   *
   **/
  public GroceryItemInstance hypermarketId(Long hypermarketId) {
    this.hypermarketId = hypermarketId;
    return this;
  }


  @ApiModelProperty(required = true, value = "")
  @JsonProperty("hypermarketId")
  @NotNull
  public Long getHypermarketId() {
    return hypermarketId;
  }

  public void setHypermarketId(Long hypermarketId) {
    this.hypermarketId = hypermarketId;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GroceryItemInstance groceryItemInstance = (GroceryItemInstance) o;
    return Objects.equals(this.id, groceryItemInstance.id) &&
        Objects.equals(this.hypermarketId, groceryItemInstance.hypermarketId) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, hypermarketId, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GroceryItemInstance {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    hypermarketId: ").append(toIndentedString(hypermarketId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first
   * line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


}

