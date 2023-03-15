package workshop.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import workshop.model.Delivery;
import workshop.model.Order;
import workshop.model.Pizza;
import workshop.repository.PizzaRepository;

@Service
public class PizzaService {
  @Autowired
  private PizzaRepository pizzaRepo;

  public static final String[] PIZZA_NAMES = {
      "bella", "margherita", "marinara", "spianatacalabrese", "trioformaggio"
  };

  public static final String[] PIZZA_SIZES = {
      "sm", "md", "lg"
  };

  private final Set<String> pizzaNames;
  private final Set<String> pizzaSizes;

  public PizzaService() {
    pizzaNames = new HashSet<String>(Arrays.asList(PIZZA_NAMES));
    pizzaSizes = new HashSet<String>(Arrays.asList(PIZZA_SIZES));
  }

  public Order savePizzaOrder(Pizza p, Delivery d) {
    Order order = createPizzaOrder(p, d);
    calculateCost(order);
    pizzaRepo.save(order);
    return order;
  }

  public Optional<Order> getOrderByOrderId(String orderId) {
    return pizzaRepo.get(orderId);
  }

  public Order createPizzaOrder(Pizza p, Delivery d) {
    String orderId = UUID.randomUUID().toString().substring(0, 8);
    Order order = new Order(p, d);
    order.setOrderId(orderId);

    return order;
  }

  public double calculateCost(Order order) {
    double total = 0d;
    switch (order.getPizzaName()) {
      case "margherita":
        total += 22;
        break;
      case "trioformaggio":
        total += 25;
        break;
      case "bella", "marinara", "spianatacalabrese":
        total += 30;
        break;
      default:
        break;
    }

    switch (order.getPizzaSize()) {
      case "md":
        total *= 1.2;
        break;
      case "lg":
        total *= 1.5;
        break;
      case "sm":
        break;
      default:
        break;

    }

    total *= order.getPizzaQuantity();
    if (order.isDeliveryRush()) {
      total += 2;
    }

    return total;
  }

  public List<ObjectError> validatePizzaOrder(Pizza p) {
    List<ObjectError> errors = new LinkedList<>();
    FieldError error;

    if (!pizzaNames.contains(p.getPizza().toLowerCase())) {
      error = new FieldError("pizza", "name", "We don't carry that type of pizza.");
      errors.add(error);
    }

    if (!pizzaSizes.contains(p.getSize().toLowerCase())) {
      error = new FieldError("pizza", "size", "We don't have that size of pizza.");
      errors.add(error);
    }

  }
}
