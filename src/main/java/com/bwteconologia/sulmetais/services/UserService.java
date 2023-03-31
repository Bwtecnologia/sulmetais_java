package com.bwteconologia.sulmetais.services;

import com.bwteconologia.sulmetais.interfaces.IUser;
import com.bwteconologia.sulmetais.models.UserModel;
import com.bwteconologia.sulmetais.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUser {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserModel> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UserModel> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public Optional<UserModel> findByRoleAndId(String role, int id){return userRepository.findByRoleAndId(role, id);}

    @Override
    public Optional<UserModel>  findByLoginAndPassword(String login, String password){
        return userRepository.findByLoginAndPassword(login, password);
    }

    @Override
    public UserModel save(UserModel user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }
}
