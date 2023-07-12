package com.bwteconologia.sulmetais.repositories;

import com.bwteconologia.sulmetais.models.GroupColorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupColorRepository extends JpaRepository<GroupColorModel, Long> {

}
