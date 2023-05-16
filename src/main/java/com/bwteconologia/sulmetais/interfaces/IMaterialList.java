package com.bwteconologia.sulmetais.interfaces;

import com.bwteconologia.sulmetais.models.MaterialListModel;
import com.bwteconologia.sulmetais.models.ProductModel;

import java.util.List;
import java.util.Optional;


public interface IMaterialList {
    List<MaterialListModel> findAll();

    Optional<MaterialListModel> findById(Long id);

    MaterialListModel save(MaterialListModel product);

    void deleteById(Long id);
}
