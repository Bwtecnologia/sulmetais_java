package com.bwteconologia.sulmetais.repositories;

import com.bwteconologia.sulmetais.models.IcmsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IcmsRepository extends JpaRepository<IcmsModel, Long>{

}
