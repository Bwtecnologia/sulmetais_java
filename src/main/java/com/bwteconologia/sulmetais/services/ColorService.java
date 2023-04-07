package com.bwteconologia.sulmetais.services;

import com.bwteconologia.sulmetais.interfaces.IColor;
import com.bwteconologia.sulmetais.models.ColorModel;
import com.bwteconologia.sulmetais.repositories.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ColorService implements IColor {

    @Autowired
    ColorRepository colorRepository;
    @Override
    public List<ColorModel> getAllColors() {
        return colorRepository.findAll();
    }

    @Override
    public Optional<ColorModel> findById(int id) {
        return colorRepository.findById(id);
    }

    @Override
    public ColorModel save(ColorModel color) {
        return colorRepository.save(color);
    }

    @Override
    public Optional<ColorModel> findColorByDescription(String description) {
        return colorRepository.findColorByDescription(description);
    }


    @Override
    public void deleteById(int id) {
       colorRepository.deleteById(id);
    }
}
