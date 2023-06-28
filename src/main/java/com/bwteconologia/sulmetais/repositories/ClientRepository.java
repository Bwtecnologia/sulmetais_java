package com.bwteconologia.sulmetais.repositories;


import com.bwteconologia.sulmetais.models.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientModel, Long>{

}
