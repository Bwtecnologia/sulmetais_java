package com.bwteconologia.sulmetais.repositories;

import com.bwteconologia.sulmetais.models.ColorModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<ColorModel, Integer > {
    Optional<ColorModel> findColorByDescription(String description);
}
