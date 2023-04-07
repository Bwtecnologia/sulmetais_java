package com.bwteconologia.sulmetais.controllers;

import com.bwteconologia.sulmetais.exceptions.GroupAlreadyExistsException;
import com.bwteconologia.sulmetais.exceptions.GroupNotFoundException;
import com.bwteconologia.sulmetais.models.GroupModel;
import com.bwteconologia.sulmetais.models.ResponseObjectModel;
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
    public ResponseEntity<GroupModel> findById(@PathVariable("id") Long id) {
        GroupModel group = groupService.findById(Math.toIntExact(id))
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
    public ResponseEntity<GroupModel> updateGroup(@PathVariable("id") Long id, @RequestBody GroupModel groupUpdate) {
        GroupModel group = groupService.findById(Math.toIntExact(id))
                .orElseThrow(() -> new GroupNotFoundException("Group with " + id + " is Not Found!"));
        group.setGroupDescription(groupUpdate.getGroupDescription());
        group.setUpdatedAt(new Date());

        GroupModel updatedGroup = groupService.save(group);
        return ResponseEntity.ok(updatedGroup);
    }
    @DeleteMapping(value = "/groups/{id}")
    public ResponseEntity<ResponseObjectModel> deleteGroup(@PathVariable("id") Long id){
        GroupModel group = groupService.findById(Math.toIntExact(id))
                .orElseThrow(() -> new GroupNotFoundException("Group with " + id + " is Not Found!"));
        groupService.deleteById(Math.toIntExact(group.getId()));
        ResponseObjectModel responseObjectModel = new ResponseObjectModel(id, "Group with ID :"+id+" is deleted");
        return ResponseEntity.ok(responseObjectModel);
    }
}
