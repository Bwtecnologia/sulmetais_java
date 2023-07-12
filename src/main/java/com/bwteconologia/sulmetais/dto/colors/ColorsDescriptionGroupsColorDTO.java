package com.bwteconologia.sulmetais.dto.colors;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ColorsDescriptionGroupsColorDTO {
    private Long idColor;
    private Long idGroup;
    private String groupName;
}
