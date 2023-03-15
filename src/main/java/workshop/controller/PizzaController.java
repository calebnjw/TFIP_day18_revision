package workshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import workshop.model.Pizza;

@Controller
public class PizzaController {

  @GetMapping(path = { "/", "/index.html" })
  public String getIndex(Model m, HttpSession session) {
    session.invalidate();
    m.addAttribute("pizza", new Pizza());
    return "index";
  }
}
