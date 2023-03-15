package workshop.model;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Order {
  private static final long serialVersionUID = 1L;

  private String orderId;
  private Double totalCost = -1d;
  private Pizza pizza;
  private Delivery delivery;

  public Order(Pizza pizza, Delivery d) {
    this.pizza = pizza;
    this.delivery = d;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Double getTotalCost() {
    return totalCost;
  }

  public void setTotalCost(Double totalCost) {
    this.totalCost = totalCost;
  }

  public Pizza getPizza() {
    return pizza;
  }

  public void setPizza(Pizza pizza) {
    this.pizza = pizza;
  }

  public Delivery getDelivery() {
    return delivery;
  }

  public void setDelivery(Delivery delivery) {
    this.delivery = delivery;
  }

  public String getDeliveryName() {
    return this.delivery.getPizza();
  }

  public String getDeliveryAddress() {
    return this.delivery.getAddress();
  }

  public String getDeliveryPhone() {
    return this.delivery.getPhone();
  }

  public boolean isDeliveryRush() {
    return this.delivery.isRush();
  }

  public String getDeliveryComments() {
    return this.delivery.getComments();
  }

  public String getPizzaName() {
    return this.pizza.getPizza();
  }

  public String getPizzaSize() {
    return this.pizza.getSize();
  }

  public int getPizzaQuantity() {
    return this.pizza.getQuantity();
  }

  public static JsonObject toJson(String json) {
    JsonReader r = Json.createReader(new StringReader(json));
    JsonObject o = r.readObject();
    return o;
  }

  public static Order create(String json) {
    JsonObject o = toJson(json);
    Pizza p = Pizza.create(o);
    Delivery d = Delivery.create(o);

    Order order = new Order(p, d);
    order.setOrderId(o.getString("orderId"));
    order.setTotalCost(o.getJsonNumber("total").doubleValue());
    return order;
  }

  public JsonObject toJson() {
    return Json.createObjectBuilder()
        .add("orderId", orderId)
        .add("name", this.getDeliveryName())
        .add("address", this.getDeliveryAddress())
        .add("phone", this.getDeliveryPhone())
        .add("rush", this.isDeliveryRush())
        .add("comments", this.getDeliveryComments())
        .add("pizza", this.getPizzaName())
        .add("size", this.getPizzaSize())
        .add("quantity", this.getPizzaQuantity())
        .add("total", totalCost)
        .build();
  }

}
