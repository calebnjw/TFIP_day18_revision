package workshop.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import jakarta.json.JsonObject;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class Delivery implements Serializable {
  private static final long serialVersionUID = 1L;

  @NotNull(message = "Please enter a name.")
  @Length(min = 3, message = "Name should be at least 3 characters long.")
  private String name;

  @NotNull(message = "Please enter an address.")
  @NotEmpty(message = "Please enter an address.")
  private String address;

  @NotNull(message = "Please provide your phone number.")
  // TODO: check the regexp
  @Pattern(regexp = "^(0-9)", message = "Phone number should be at 8 characters long.")
  private String phone;

  private boolean rush = false;

  private String comments;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public boolean isRush() {
    return rush;
  }

  public void setRush(boolean rush) {
    this.rush = rush;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  @Override
  public String toString() {
    return "Delivery [name=" + name + ", address=" + address + ", phone=" + phone + ", rush=" + rush + ", comments="
        + comments + "]";
  }

  public static Delivery create(JsonObject o) {
    Delivery d = new Delivery();

    d.setName(o.getString("name"));
    d.setAddress(o.getString("address"));
    d.setPhone(o.getString("phone"));
    d.setRush(o.getBoolean("rush"));
    d.setComments(o.getString("comments"));

    return d;
  }

}
