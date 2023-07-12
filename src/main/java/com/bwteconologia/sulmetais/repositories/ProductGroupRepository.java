package com.bwteconologia.sulmetais.repositories;

import com.bwteconologia.sulmetais.models.ProductGroupModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProductGroupRepository extends JpaRepository<ProductGroupModel, Integer> {
    Optional<ProductGroupModel> findByGroupDescription(String groupDescription);
}
