package com.bwteconologia.sulmetais.services;

import com.bwteconologia.sulmetais.interfaces.IProductGroup;
import com.bwteconologia.sulmetais.models.ProductGroupModel;
import com.bwteconologia.sulmetais.repositories.ProductGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService implements IProductGroup {
    ProductGroupRepository groupRepository;

    @Autowired
    public GroupService(ProductGroupRepository groupRepository){
        this.groupRepository = groupRepository;

    }
    @Override
    public List<ProductGroupModel> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Optional<ProductGroupModel> findById(int id) {
        return groupRepository.findById(id);
    }

    @Override
    public ProductGroupModel save(ProductGroupModel group) {
        return groupRepository.save(group);
    }

    @Override
    public Optional<ProductGroupModel> findByGroupDescription(String groupDescription) {
        return groupRepository.findByGroupDescription(groupDescription);
    }

    @Override
    public void deleteById(int id) {
        groupRepository.deleteById(id);
    }
}
