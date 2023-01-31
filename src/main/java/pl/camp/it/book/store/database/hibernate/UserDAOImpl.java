package pl.camp.it.book.store.database.hibernate;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.book.store.database.IUserDAO;
import pl.camp.it.book.store.model.User;

import java.util.Optional;

@Repository
public class UserDAOImpl extends EntityManager implements IUserDAO {

    public UserDAOImpl(@Autowired SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery(
                "FROM pl.camp.it.book.store.model.User WHERE login = :login",
                User.class);
        query.setParameter("login", login);
        Optional<User> result = Optional.empty();
        try {
            result = Optional.of(query.getSingleResult());
        } catch (NoResultException e){}
        session.close();
        return result;
    }

    @Override
    public void persistUser(User user) {
        super.persist(user);
    }
}
