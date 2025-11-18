package seu.edu.bd.healthtracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {


    @GetMapping("/index")
    public String showIndex() {
        return "index";  // your main index page
    }

    // Redirect /signUp to /login. This handles the 'Sign In' button from index.html
    @GetMapping("/signUp")
    public String redirectSignUp() {
        return "redirect:/login";
    }


}
