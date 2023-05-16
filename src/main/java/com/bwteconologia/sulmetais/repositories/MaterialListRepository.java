package com.bwteconologia.sulmetais.repositories;

import com.bwteconologia.sulmetais.models.MaterialListModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialListRepository extends JpaRepository<MaterialListModel, Long> {
}
