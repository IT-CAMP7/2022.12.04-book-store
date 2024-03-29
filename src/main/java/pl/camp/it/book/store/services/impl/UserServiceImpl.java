package pl.camp.it.book.store.services.impl;

import jakarta.persistence.NoResultException;
import jdk.jshell.spi.ExecutionControl;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.book.store.database.IUserDAO;
import pl.camp.it.book.store.exceptions.UserNotExistException;
import pl.camp.it.book.store.model.User;
import pl.camp.it.book.store.model.dto.UserDTO;
import pl.camp.it.book.store.services.IUserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserDAO userDAO;

    @Override
    public Optional<User> getUserByLogin(String login) {
        return this.userDAO.getUserByLogin(login);
    }

    @Override
    public User persistUser(UserDTO userDTO) {
        if(userDTO == null) {
            return null;
        }
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setPassword(DigestUtils.md5Hex(userDTO.getPassword()));
        user.setSurname(userDTO.getSurname());
        user.setName(userDTO.getName());
        user.setRole(userDTO.getRole());
        this.userDAO.persistUser(user);
        return user;
    }

    @Override
    public Optional<User> getUserById(int id) throws ExecutionControl.NotImplementedException {
        return this.userDAO.getUserById(id);
    }

    @Override
    public User updateUser(UserDTO userDTO, int id) throws ExecutionControl.NotImplementedException {
        return this.userDAO.updateUser(userDTO, id);
    }
}
