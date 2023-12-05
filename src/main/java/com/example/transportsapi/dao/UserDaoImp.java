package com.example.transportsapi.dao;

import com.example.transportsapi.models.UserModel;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDaoImp implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<UserModel> getUsers() {
        entityManager.clear();
        String query = "FROM UserModel";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void deleteUser(Long id) {
        UserModel user = entityManager.find(UserModel.class, id);
        entityManager.remove(user);
    }

    @Override
    public void addUser(UserModel user) {
        entityManager.merge(user);
    }

    @Override
    public UserModel getUserByCredentials(UserModel user) {
        String query = "FROM UserModel WHERE email= :email";
        List<UserModel> foundUsers = entityManager.createQuery(query)
                .setParameter("email", user.getEmail())
                .getResultList();

        if(foundUsers.isEmpty()){
            return null;
        }

        String hashedPass = foundUsers.get(0).getPassword();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if(argon2.verify(hashedPass, user.getPassword())){
            return foundUsers.get(0);
        }

        return null;
    }
}
