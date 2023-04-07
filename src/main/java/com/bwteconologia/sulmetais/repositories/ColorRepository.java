package com.bwteconologia.sulmetais.repositories;

import com.bwteconologia.sulmetais.models.ColorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<ColorModel, Integer > {
    Optional<ColorModel> findColorByDescription(String description);
}
