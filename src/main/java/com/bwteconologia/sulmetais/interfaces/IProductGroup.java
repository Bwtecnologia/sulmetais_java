package com.bwteconologia.sulmetais.interfaces;

import com.bwteconologia.sulmetais.models.ProductGroupModel;

import java.util.List;
import java.util.Optional;

public interface IProductGroup {
    List<ProductGroupModel> getAllGroups();
    Optional<ProductGroupModel> findById(int id);
    ProductGroupModel save(ProductGroupModel group);
    Optional<ProductGroupModel> findByGroupDescription(String groupDescription);
    void deleteById(int id);
}
