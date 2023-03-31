package com.bwteconologia.sulmetais.services;

import com.bwteconologia.sulmetais.interfaces.IGroup;
import com.bwteconologia.sulmetais.models.GroupModel;
import com.bwteconologia.sulmetais.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService implements IGroup {
    GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository){
        this.groupRepository = groupRepository;

    }
    @Override
    public List<GroupModel> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Optional<GroupModel> findById(int id) {
        return groupRepository.findById(id);
    }

    @Override
    public GroupModel save(GroupModel group) {
        return groupRepository.save(group);
    }

    @Override
    public Optional<GroupModel> findByGroupDescription(String groupDescription) {
        return groupRepository.findByGroupDescription(groupDescription);
    }

    @Override
    public void deleteById(int id) {
        groupRepository.deleteById(id);
    }
}
