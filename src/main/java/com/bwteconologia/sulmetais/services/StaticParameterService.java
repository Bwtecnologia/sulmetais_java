package com.bwteconologia.sulmetais.services;

import com.bwteconologia.sulmetais.models.StaticParameterModel;
import com.bwteconologia.sulmetais.repositories.StaticParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class StaticParameterService {
    @Autowired
    private StaticParameterRepository staticParameterRepository;

//    public StaticParameterService() {
//
//        Optional<StaticParameterModel> parameter = staticParameterRepository.findById(1L);
//        if (parameter.isEmpty()) {
//
//            StaticParameterModel staticParameter = new StaticParameterModel();
//            staticParameter.setIcmsPercentage(0.0);
//            staticParameter.setIpiPercentage(0.0);
//            staticParameter.setPisPercentage(0.0);
//            staticParameter.setCofinsPercentage(0.0);
//            staticParameter.setComissionPercentage(0.0);
//            staticParameter.setDeliveryPercentage(0.0);
//
//            save(staticParameter);
//        }
//    }

    public StaticParameterModel save(StaticParameterModel staticParameter) {
        return staticParameterRepository.save(staticParameter);
    }

    public Optional<StaticParameterModel> findById(Long id) {
        return staticParameterRepository.findById(id);
    }
}
