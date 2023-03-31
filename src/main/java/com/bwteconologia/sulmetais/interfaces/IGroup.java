package com.bwteconologia.sulmetais.interfaces;

import com.bwteconologia.sulmetais.models.GroupModel;

import java.util.List;
import java.util.Optional;

public interface IGroup {
    List<GroupModel> getAllGroups();
    Optional<GroupModel> findById(int id);
    GroupModel save(GroupModel group);
    Optional<GroupModel> findByGroupDescription(String groupDescription);
    void deleteById(int id);
}
