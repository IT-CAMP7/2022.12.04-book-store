package pl.camp.it.book.store.controllers;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.book.store.database.IBookDAO;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.services.IBookService;
import pl.camp.it.book.store.session.SessionObject;

import java.util.List;
import java.util.Optional;

@Controller
public class CommonController {

    @Autowired
    IBookService bookService;

    @Resource
    SessionObject sessionObject;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main() {
        return "redirect:/";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(Model model) {
        model.addAttribute("books", this.bookService.getBooks());
        model.addAttribute("logged", this.sessionObject.isLogged());
        return "main";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String main(@RequestParam String pattern) {
        this.sessionObject.setPattern(pattern);
        return "redirect:/";
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String contact(Model model) {
        model.addAttribute("logged", this.sessionObject.isLogged());
        return "contact";
    }
}
