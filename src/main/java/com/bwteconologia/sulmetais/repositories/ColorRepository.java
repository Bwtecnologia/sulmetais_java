package com.bwteconologia.sulmetais.repositories;

import com.bwteconologia.sulmetais.dto.colors.ColorsDescriptionGroupsColorDTO;
import com.bwteconologia.sulmetais.models.ColorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ColorRepository extends JpaRepository<ColorModel, Integer > {
    Optional<ColorModel> findColorByDescription(String description);

    @Query("SELECT new com.bwteconologia.sulmetais.dto.colors.ColorsDescriptionGroupsColorDTO(c.id, g.id, g.name) from ColorModel c join GroupColorModel g")
    public List<ColorsDescriptionGroupsColorDTO> getAllInfo();

    @Query("SELECT new com.bwteconologia.sulmetais.dto.colors.ColorsDescriptionGroupsColorDTO(c.id, g.id, g.name) from ColorModel c join GroupColorModel g where c.id = ?1")
    public ColorsDescriptionGroupsColorDTO getOneInfo(Long id);

}
