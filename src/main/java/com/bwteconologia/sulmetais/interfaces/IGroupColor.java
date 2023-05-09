package com.bwteconologia.sulmetais.interfaces;

import com.bwteconologia.sulmetais.models.ColorModel;
import com.bwteconologia.sulmetais.models.GroupColorModel;

import java.util.List;
import java.util.Optional;

public interface IGroupColor {
    List<GroupColorModel> getAllGroupColors();
    Optional<GroupColorModel> findById(long id);
    GroupColorModel save(GroupColorModel colorModel);
    GroupColorModel saveColorToGroup(GroupColorModel groupColorModel);
    void deleteColorById(long id);
    void deleteById(long id);
}
