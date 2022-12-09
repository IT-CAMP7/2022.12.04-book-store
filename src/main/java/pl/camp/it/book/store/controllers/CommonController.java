package pl.camp.it.book.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.book.store.database.IBookDAO;
import pl.camp.it.book.store.model.Book;

import java.util.List;

@Controller
public class CommonController {

    @Autowired
    IBookDAO bookDAO;

    private String pattern = "";

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main() {
        return "redirect:/";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(Model model) {
        if(pattern.isEmpty()) {
            model.addAttribute("books",
                    this.bookDAO.getBooks());
        } else {
            model.addAttribute("books",
                    this.bookDAO.getBooksByPattern(this.pattern));
        }
        return "main";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String main(@RequestParam String pattern) {
        this.pattern = pattern;
        return "redirect:/";
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String contact() {
        return "contact";
    }
}
