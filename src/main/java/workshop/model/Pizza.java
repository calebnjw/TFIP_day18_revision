package workshop.model;

import java.io.Serializable;

import jakarta.json.JsonObject;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Pizza implements Serializable {
  private static final long serialVersionUID = 1L;

  @NotNull(message = "Please select a pizza.")
  private String pizza;

  @NotNull(message = "Please select a size.")
  private String size;

  @Min(value = 1, message = "Must order at least 1 pizza.")
  @Max(value = 10, message = "You can only order a maximum of 10 pizzas.")
  private int quantity;

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public String getPizza() {
    return pizza;
  }

  public void setPizza(String pizza) {
    this.pizza = pizza;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  @Override
  public String toString() {
    return "Pizza [pizza=" + pizza + ", size=" + size + ", quantity=" + quantity + "]";
  }

  public static Pizza create(JsonObject o) {
    Pizza p = new Pizza();

    p.setPizza(o.getString("name"));
    p.setSize(o.getString("size"));
    p.setQuantity(o.getInt("quantity")); // or: getJsonNumber("quantity").intValue();

    return p;
  }
}
