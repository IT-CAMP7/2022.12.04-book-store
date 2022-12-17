package pl.camp.it.book.store.controllers;

import jakarta.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.book.store.database.sequence.IIdSequence;
import pl.camp.it.book.store.database.sequence.UserIdSequence;
import pl.camp.it.book.store.database.IUserDAO;
import pl.camp.it.book.store.exceptions.UserLoginExistException;
import pl.camp.it.book.store.exceptions.UserValidationException;
import pl.camp.it.book.store.model.User;
import pl.camp.it.book.store.services.IAuthenticationService;
import pl.camp.it.book.store.session.SessionObject;
import pl.camp.it.book.store.validators.UserValidator;

import java.util.Optional;

@Controller
public class AuthenticationController {
    @Autowired
    IAuthenticationService authenticationService;

    @Resource
    SessionObject sessionObject;

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("logged", this.sessionObject.isLogged());
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String login, @RequestParam String password) {
        try {
            UserValidator.validateLogin(login);
            UserValidator.validatePassword(password);
            if(!this.authenticationService.authenticate(login, password)) {
                return "redirect:/login";
            }
        } catch (UserValidationException e) {
            e.printStackTrace();
            return "redirect:/login";
        }

        return "redirect:/main";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout() {
        this.authenticationService.logout();
        return "redirect:/login";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("logged", this.sessionObject.isLogged());
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute User user, @RequestParam String password2) {
        try {
            UserValidator.validateRegisterUser(user, password2);
            this.authenticationService.registerUser(user);
        } catch (UserValidationException | UserLoginExistException e) {
            e.printStackTrace();
            return "redirect:/register";
        }

        return "redirect:/login";
    }
}
