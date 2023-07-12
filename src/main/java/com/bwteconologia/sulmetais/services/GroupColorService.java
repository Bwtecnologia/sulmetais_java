package com.bwteconologia.sulmetais.services;

import com.bwteconologia.sulmetais.interfaces.IGroupColor;
import com.bwteconologia.sulmetais.models.ColorModel;
import com.bwteconologia.sulmetais.models.GroupColorModel;
import com.bwteconologia.sulmetais.repositories.GroupColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupColorService implements IGroupColor {


    @Autowired
    GroupColorRepository groupColorRepository;

    @Override
    public List<GroupColorModel> getAllGroupColors(){
        return groupColorRepository.findAll();
    }

    @Override
    public Optional<GroupColorModel> findById(long id) {
        return groupColorRepository.findById(id);
    }

    @Override
    public GroupColorModel save(GroupColorModel colorModel) {
        return groupColorRepository.save(colorModel);
    }

    @Override
    public GroupColorModel saveColorToGroup(GroupColorModel groupColorModel) {
        return groupColorRepository.save(groupColorModel);
    }

    @Override
    public void deleteColorById(long id) {

    }

    @Override
    public void deleteById(long id) {
        groupColorRepository.deleteById(id);
    }

}
