package com.bwteconologia.sulmetais.repositories;

import com.bwteconologia.sulmetais.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByLogin(String login);
    Optional<UserModel> findUserRoleById(int id);
    Optional<UserModel> findByLoginAndPassword(String login, String password);
}
