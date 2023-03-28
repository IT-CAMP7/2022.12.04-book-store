package pl.camp.it.book.store.controllers.rest;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.camp.it.book.store.exceptions.NotEnoughBookException;
import pl.camp.it.book.store.services.ICartService;
import pl.camp.it.book.store.session.SessionObject;

@RestController
@RequestMapping(path = "/api/v1/cart")
public class RestApiCartController {

    @Autowired
    ICartService cartService;
    @Resource
    SessionObject sessionObject;

    @RequestMapping(path = "/{bookId}", method = RequestMethod.GET)
    public ResponseEntity addToCart(@PathVariable int bookId) {
        if(!this.sessionObject.isLogged()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            this.cartService.addBookToCart(bookId);
        } catch (NotEnoughBookException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Nie ma już więcej egzemplarzy !!!");
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
