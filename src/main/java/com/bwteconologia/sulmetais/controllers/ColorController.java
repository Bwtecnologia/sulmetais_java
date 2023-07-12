package com.bwteconologia.sulmetais.controllers;


import com.bwteconologia.sulmetais.dto.colors.ColorsDescriptionGroupsColorDTO;
import com.bwteconologia.sulmetais.exceptions.ColorAlreadyExistsException;
import com.bwteconologia.sulmetais.exceptions.ColorNotFoundException;
import com.bwteconologia.sulmetais.models.ColorModel;
import com.bwteconologia.sulmetais.models.ResponseObjectModel;
import com.bwteconologia.sulmetais.services.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ColorController {

    @Autowired
    ColorService colorService;

    @GetMapping(value = "/colors")
    public ResponseEntity<List<ColorModel>> getAllColors() {
        List<ColorModel> color = colorService.getAllColors();
        return ResponseEntity.ok(color);
    }

    @GetMapping(value = "/colors/info")
    public List<ColorsDescriptionGroupsColorDTO> getAllInfo() {
        return colorService.getAllInfo();
    }

    @GetMapping(value = "/colors/info/{id}")
    public ColorsDescriptionGroupsColorDTO getOneInfo(@PathVariable("id") Long id) {
        Optional<ColorModel> colorModelOptional = colorService.findById(Math.toIntExact(id));
        if (colorModelOptional.isEmpty()) {
            throw new ColorNotFoundException("Esta cor não existe!");
        }

        Optional<ColorsDescriptionGroupsColorDTO> colorsDescriptionGroupsColorDTOOptional
                = Optional.ofNullable(colorService.getOneInfo(id));
        if (colorsDescriptionGroupsColorDTOOptional.isEmpty()) {
            throw new ColorNotFoundException("Esta cor não tem nenhum grupo associado!");
        }

        return colorService.getOneInfo(id);
    }

    @GetMapping(value = "/colors/{id}")
    public ResponseEntity<ColorModel> findById(@PathVariable("id") Long id) {
        ColorModel color = colorService.findById(Math.toIntExact(id))
                .orElseThrow(() -> new ColorNotFoundException("Color with " + id + " is Not Found!"));
        return ResponseEntity.ok().body(color);
    }

    @PostMapping("/colors")
    public ResponseEntity<ColorModel> addColor(@RequestBody ColorModel color) {
        Optional<ColorModel> existsColor = colorService.findColorByDescription(color.getDescription());
        if (existsColor.isPresent()) {
            throw new ColorAlreadyExistsException("Color already exists");
        }
        ColorModel savedColor = colorService.save(color);
        return ResponseEntity.ok(savedColor);
    }

    @PutMapping(value = "/colors/{id}")
    public ResponseEntity<ColorModel> updateColor(@PathVariable("id") Long id, @RequestBody ColorModel colorUpdated) {
        ColorModel color = colorService.findById(Math.toIntExact(id))
                .orElseThrow(() -> new ColorNotFoundException("Color with " + id + " is Not Found!"));

        color.setDescription(colorUpdated.getDescription());
        color.setValue(colorUpdated.getValue());
        color.setUpdatedAt(new Date());

        ColorModel updatedColor = colorService.save(color);
        return ResponseEntity.ok(updatedColor);
    }

    @DeleteMapping(value = "/colors/{id}")
    public ResponseEntity<ResponseObjectModel> deleteColor(@PathVariable("id") Long id) {
        ColorModel color = colorService.findById(Math.toIntExact(id))
                .orElseThrow(() -> new ColorNotFoundException("Color with " + id + " is Not Found!"));
        colorService.deleteById(Math.toIntExact(color.getId()));
        ResponseObjectModel responseObjectModel = new ResponseObjectModel(id, "Color with ID :" + id + " is deleted");
        return ResponseEntity.ok(responseObjectModel);
    }
}


