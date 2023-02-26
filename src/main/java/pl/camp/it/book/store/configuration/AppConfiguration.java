package pl.camp.it.book.store.configuration;

import com.google.protobuf.Api;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
@ComponentScan("pl.camp.it.book.store")
@EnableScheduling
public class AppConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }

    /*@Bean
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
    }*/
    /*@Bean
    public IBookDAO bookDAO(IBookIdSequence bookIdSequence) {
        return new BookDB(bookIdSequence);
    }

    @Bean
    public IBookIdSequence bookIdSequence() {
        return new BookIdSequence();
    }*/

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/v1/**"))
                //.apis(RequestHandlerSelectors.basePackage("pl.camp.it.book.store.controllers.rest"))
                .build()
                .apiInfo(createApiInfo());
    }

    private ApiInfo createApiInfo() {
        return new ApiInfo("Bookstore API",
                "Jakies fajne api bo ksiegarni",
                "1.0",
                "http://google.pl",
                new Contact("Mateusz", "http://mateusz.pl", "mail@mail.com"),
                "Comarch Licence",
                "http://google.pl",
                Collections.emptyList());
    }
}
