package com.bwteconologia.sulmetais.interfaces;

import com.bwteconologia.sulmetais.models.ColorModel;

import java.util.List;
import java.util.Optional;

public interface IColor {

    List<ColorModel> getAllColors();
    Optional<ColorModel> findById(int id);
    ColorModel save(ColorModel group);
    Optional<ColorModel> findColorByDescription(String description);
    void deleteById(int id);
}
