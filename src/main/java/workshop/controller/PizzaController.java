package workshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import workshop.model.Delivery;
import workshop.model.Order;
import workshop.model.Pizza;
import workshop.service.PizzaService;

@Controller
public class PizzaController {

  @Autowired
  private PizzaService pizzaService;

  @GetMapping(path = { "/", "/index.html" })
  public String getIndex(Model m, HttpSession session) {
    session.invalidate();
    m.addAttribute("pizza", new Pizza());
    return "index";
  }

  @PostMapping(path = "/pizza")
  public String postPizza(Model m, HttpSession session, @Valid Pizza pizza, BindingResult bindings) {
    if (bindings.hasErrors()) {
      return "index";
    }

    List<ObjectError> errors = pizzaService.validatePizzaOrder(pizza);
    if (!errors.isEmpty()) {
      for (ObjectError e : errors) {
        bindings.addError(e);
      }
      return "index";
    }
    session.setAttribute("pizza", pizza);
    m.addAttribute("delivery", new Delivery());

    return "delivery";
  }

  @PostMapping(path = "")
  public String postDelivery(Model m, HttpSession session, @Valid Delivery delivery, BindingResult bindings) {
    if (bindings.hasErrors()) {
      return "delivery";
    }

    Pizza p = (Pizza) session.getAttribute("pizza");
    Order o = pizzaService.savePizzaOrder(p, delivery);
    m.addAttribute("order", o);
    return "order";
  }
}
