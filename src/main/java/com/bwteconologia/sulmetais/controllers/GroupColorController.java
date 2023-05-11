package com.bwteconologia.sulmetais.controllers;

import com.bwteconologia.sulmetais.exceptions.ColorNotFoundException;
import com.bwteconologia.sulmetais.exceptions.group_color.GroupColorNotExistsException;
import com.bwteconologia.sulmetais.models.ColorModel;
import com.bwteconologia.sulmetais.models.GroupColorModel;
import com.bwteconologia.sulmetais.services.ColorService;
import com.bwteconologia.sulmetais.services.GroupColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GroupColorController {
    @Autowired
    GroupColorService groupColorService;

    @Autowired
    ColorService colorService;

    @GetMapping(value = "/groupcolors")
    public List<GroupColorModel> getAllGroupColors() {

        List<GroupColorModel> groupColorModelList = groupColorService.getAllGroupColors();
        return groupColorModelList;
    }

    @PostMapping(value = "/groupcolors")
    public GroupColorModel insertGroupModel(@RequestBody GroupColorModel groupColorModel) {

        double maxPrice = 0;

        List<ColorModel> colorModelList = new ArrayList<ColorModel>();
        //verify if color exist in colors table and calc maxPrice of Group
        for (var i = 0; i < groupColorModel.getColors().size(); i++) {
            Optional<ColorModel> colorModelOptional =
                    colorService.findById(Math.toIntExact(groupColorModel.getColors().get(i).getId()));

            if (colorModelOptional.isEmpty()) throw new ColorNotFoundException("Color not found");
            //groupColorModel.getColors().set(i, colorModelOptional.get()).;

            colorModelList.add(colorModelOptional.get());

            double value = groupColorModel.getColors().get(i).getValue();
//            String type = groupColorModel.getColors().set(i).setType();
//            String description = groupColorModel.getColors().get(i).getDescription();
//            String createdDate = groupColorModel.get

            if (maxPrice < value) maxPrice = value;

        }

        groupColorModel.setColors(colorModelList);
        groupColorModel.setHighestValue(maxPrice);

        return groupColorService.save(groupColorModel);
    }

    @GetMapping(value = "/groupcolors/{id}")
    public GroupColorModel findById(@PathVariable("id") Long id){
        Optional<GroupColorModel> groupColorModelOptional = groupColorService.findById(id);
        if(groupColorModelOptional.isEmpty()) throw new GroupColorNotExistsException("Group id:" + id + " not exist");

        return groupColorModelOptional.get();
    }

    @PutMapping(value = "/groupcolors/{id}")
    public GroupColorModel updateGroupModel(
            @RequestBody GroupColorModel groupColorModel,
            @PathVariable("id") Long id) throws Throwable {

        double maxPrice = 0;
        List<ColorModel> colorModelList = new ArrayList<ColorModel>();
        //verify if color exist in colors table and calc maxPrice of Group
        for (var i = 0; i < groupColorModel.getColors().size(); i++) {
            Optional<ColorModel> colorModelOptional =
                    colorService.findById(Math.toIntExact(groupColorModel.getColors().get(i).getId()));

            if (colorModelOptional.isEmpty()) throw new ColorNotFoundException("Color not found");
            //groupColorModel.getColors().set(i, colorModelOptional.get()).;

            colorModelList.add(colorModelOptional.get());

            double value = groupColorModel.getColors().get(i).getValue();
//            String type = groupColorModel.getColors().set(i).setType();
//            String description = groupColorModel.getColors().get(i).getDescription();
//            String createdDate = groupColorModel.get

            if (maxPrice < value) maxPrice = value;

        }

        Optional<GroupColorModel> groupColorModelOptional = groupColorService.findById(id);
        if (!groupColorModelOptional.isPresent())
            throw new GroupColorNotExistsException("Group color " + id + " not found");

        groupColorModel.setHighestValue(maxPrice);
        groupColorModel.setUpdatedAt(new Date());
        groupColorModel.setColors(colorModelList);

        return groupColorService.save(groupColorModel);
    }

    @DeleteMapping(value = "/groupcolors/{id}")
    public String deleteGroupModel(@PathVariable("id") Long id) {

        Optional<GroupColorModel> groupColorModelOptional = groupColorService.findById(id);
        if (groupColorModelOptional.isEmpty())
            throw new GroupColorNotExistsException("Group color " + id + " not found");

        groupColorService.deleteById(Math.toIntExact(id));

        return "Group with id: " + id +" has been deleted!";
    }
}
