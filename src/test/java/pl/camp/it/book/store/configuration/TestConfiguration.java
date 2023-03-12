package pl.camp.it.book.store.configuration;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.camp.it.book.store.database.IBookDAO;
import pl.camp.it.book.store.database.IOrderDAO;
import pl.camp.it.book.store.database.IOrderPositionDAO;
import pl.camp.it.book.store.database.IUserDAO;
import pl.camp.it.book.store.database.hibernate.BookDAOImplStub;
import pl.camp.it.book.store.database.hibernate.OrderDAOImplStub;
import pl.camp.it.book.store.database.hibernate.OrderPositionDAOImplStub;
import pl.camp.it.book.store.database.hibernate.UserDAOImplStub;

@Configuration
@ComponentScan(basePackages = {
        "pl.camp.it.book.store.controllers",
        "pl.camp.it.book.store.services",
        "pl.camp.it.book.store.session"
})
@EnableScheduling
public class TestConfiguration {

    /*@Bean
    public IUserDAO userDAO() {
        return Mockito.mock(IUserDAO.class);
    }

    @Bean
    public IBookDAO bookDAO() {
        return Mockito.mock(IBookDAO.class);
    }

    @Bean
    public IOrderDAO orderDAO() {
        return Mockito.mock(IOrderDAO.class);
    }

    @Bean
    public IOrderPositionDAO orderPositionDAO() {
        return Mockito.mock(IOrderPositionDAO.class);
    }*/
}
