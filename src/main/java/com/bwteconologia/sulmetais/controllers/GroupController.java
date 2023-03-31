package com.bwteconologia.sulmetais.controllers;

import com.bwteconologia.sulmetais.exceptions.GroupAlreadyExistsException;
import com.bwteconologia.sulmetais.exceptions.GroupNotFoundException;
import com.bwteconologia.sulmetais.models.GroupModel;
import com.bwteconologia.sulmetais.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GroupController {
    @Autowired
    GroupService groupService;


    @GetMapping(value = "/groups")
    public List<GroupModel> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping(value = "/groups/{id}")
    public GroupModel findById(@PathVariable("id") int id) {
        GroupModel group = groupService.findById(id)
                .orElseThrow(() -> new GroupNotFoundException("Group with " + id + " is Not Found!"));
        return group;
    }

    @PostMapping(value = "/groups")
    public GroupModel addGroup(@RequestBody GroupModel group) {
        Optional<GroupModel> existsGroup = groupService.findByGroupDescription(group.getGroupDescription());
        if(existsGroup != null){
            throw new GroupAlreadyExistsException("Group already exists");
        }
        return groupService.save(group);
    }

    @PutMapping(value = "/groups/{id}")
    public GroupModel updateGroup(@PathVariable("id") int id, @RequestBody GroupModel groupUpdate) {
        GroupModel group = groupService.findById(id)
                .orElseThrow(() -> new GroupNotFoundException("Group with " + id + " is Not Found!"));
        group.setGroupDescription(groupUpdate.getGroupDescription());
        group.setUpdatedAt(new Date());

        return groupService.save(group);
    }
    @DeleteMapping(value = "/groups/{id}")
    public String deleteGroup(@PathVariable("id") int id){
        GroupModel group = groupService.findById(id)
                .orElseThrow(() -> new GroupNotFoundException("Group with " + id + " is Not Found!"));
        groupService.deleteById(group.getId());
        return "Group with ID :"+id+" is deleted";
    }
}
