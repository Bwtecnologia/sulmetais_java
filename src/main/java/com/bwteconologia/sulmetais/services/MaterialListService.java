package com.bwteconologia.sulmetais.services;

import com.bwteconologia.sulmetais.interfaces.IMaterialList;
import com.bwteconologia.sulmetais.models.MaterialListModel;
import com.bwteconologia.sulmetais.models.ProductModel;
import com.bwteconologia.sulmetais.repositories.MaterialListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialListService implements IMaterialList {

    @Autowired
    MaterialListRepository materialListRepository;

    @Override
    public List<MaterialListModel> findAll() {
        return materialListRepository.findAll();
    }

    @Override
    public Optional<MaterialListModel> findById(Long id) {
        return materialListRepository.findById(id);
    }

    @Override
    public MaterialListModel save(MaterialListModel product) {
        return materialListRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        materialListRepository.deleteById(id);
    }
}
