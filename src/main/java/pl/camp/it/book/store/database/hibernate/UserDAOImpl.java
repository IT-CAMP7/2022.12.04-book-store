package pl.camp.it.book.store.database.hibernate;

import jakarta.persistence.NoResultException;
import jdk.jshell.spi.ExecutionControl;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.book.store.database.IUserDAO;
import pl.camp.it.book.store.exceptions.UserLoginExistException;
import pl.camp.it.book.store.exceptions.UserNotExistException;
import pl.camp.it.book.store.model.User;
import pl.camp.it.book.store.model.dto.UserDTO;

import java.sql.SQLIntegrityConstraintViolationException;
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

    @Override
    public Optional<User> getUserById(int id) throws ExecutionControl.NotImplementedException {
        Session session = this.sessionFactory.openSession();

        Query<User> query = session.createQuery(
                "FROM pl.camp.it.book.store.model.User WHERE id = :id",
                User.class
        );
        query.setParameter("id", id);
        Optional<User> result = Optional.empty();
        try {
            result = Optional.of(query.getSingleResult());
        } catch (NoResultException e) {}
        session.close();
        return result;
    }

    @Override
    public void updateUser(User user) throws ExecutionControl.NotImplementedException {
        super.update(user);
    }

    @Override
    public User updateUser(UserDTO userDTO, int id) throws ExecutionControl.NotImplementedException {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery(
                "FROM pl.camp.it.book.store.model.User WHERE id = :id",
                User.class
        );
        query.setParameter("id", id);
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (NoResultException e) {
            session.close();
            throw new UserNotExistException();
        }
        user.setRole(userDTO.getRole());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setLogin(userDTO.getLogin());
        user.setPassword(DigestUtils.md5Hex(userDTO.getPassword()));

        session.beginTransaction();
        session.merge(user);
        session.getTransaction().commit();
        session.close();

        user.getOrders().forEach(order -> order.setUser(null));

        return user;
    }
}
