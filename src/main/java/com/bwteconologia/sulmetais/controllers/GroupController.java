package com.bwteconologia.sulmetais.controllers;

import com.bwteconologia.sulmetais.exceptions.GroupAlreadyExistsException;
import com.bwteconologia.sulmetais.exceptions.GroupNotFoundException;
import com.bwteconologia.sulmetais.models.GroupModel;
import com.bwteconologia.sulmetais.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<GroupModel>> getAllGroups() {
        List<GroupModel> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    @GetMapping(value = "/groups/{id}")
    public ResponseEntity<GroupModel> findById(@PathVariable("id") int id) {
        GroupModel group = groupService.findById(id)
                .orElseThrow(() -> new GroupNotFoundException("Group with " + id + " is Not Found!"));
        return ResponseEntity.ok().body(group);
    }

    @PostMapping(value = "/groups")
    public ResponseEntity<GroupModel> addGroup(@RequestBody GroupModel group) {
        Optional<GroupModel> existsGroup = groupService.findByGroupDescription(group.getGroupDescription());
        if (existsGroup.isPresent()) {
            throw new GroupAlreadyExistsException("Group already exists");
        }
        GroupModel savedGroup = groupService.save(group);
        return ResponseEntity.ok(savedGroup);
    }

    @PutMapping(value = "/groups/{id}")
    public ResponseEntity<GroupModel> updateGroup(@PathVariable("id") int id, @RequestBody GroupModel groupUpdate) {
        GroupModel group = groupService.findById(id)
                .orElseThrow(() -> new GroupNotFoundException("Group with " + id + " is Not Found!"));
        group.setGroupDescription(groupUpdate.getGroupDescription());
        group.setUpdatedAt(new Date());

        GroupModel updatedGroup = groupService.save(group);
        return ResponseEntity.ok(updatedGroup);
    }
    @DeleteMapping(value = "/groups/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable("id") int id){
        GroupModel group = groupService.findById(id)
                .orElseThrow(() -> new GroupNotFoundException("Group with " + id + " is Not Found!"));
        groupService.deleteById(group.getId());
        return ResponseEntity.ok("Group with ID :"+id+" is deleted");
    }
}
