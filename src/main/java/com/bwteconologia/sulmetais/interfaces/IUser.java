package com.bwteconologia.sulmetais.interfaces;



import com.bwteconologia.sulmetais.models.UserModel;

import java.util.List;
import java.util.Optional;

public interface IUser {
    List<UserModel> getAllUsers();
    Optional<UserModel> findById(int id);
    Optional<UserModel> findByLogin(String login);

    Optional<UserModel> findByLoginAndPassword(String login, String password);
    Optional<UserModel> findByRoleAndId(String role, int id);
    UserModel save(UserModel user);
    void deleteById(int id);
}
