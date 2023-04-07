package com.bwteconologia.sulmetais.repositories;

import com.bwteconologia.sulmetais.models.GroupModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface GroupRepository extends JpaRepository<GroupModel, Integer> {
    Optional<GroupModel> findByGroupDescription(String groupDescription);
}
