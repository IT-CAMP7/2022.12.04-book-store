package pl.camp.it.book.store.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.camp.it.book.store.database.IBookDAO;
import pl.camp.it.book.store.database.memory.BookDB;
import pl.camp.it.book.store.database.sequence.BookIdSequence;
import pl.camp.it.book.store.database.sequence.IBookIdSequence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@ComponentScan("pl.camp.it.book.store")
public class AppConfiguration {

    @Bean
    public Connection connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bookstore16",
                    "root",
                    "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    /*@Bean
    public IBookDAO bookDAO(IBookIdSequence bookIdSequence) {
        return new BookDB(bookIdSequence);
    }

    @Bean
    public IBookIdSequence bookIdSequence() {
        return new BookIdSequence();
    }*/
}
