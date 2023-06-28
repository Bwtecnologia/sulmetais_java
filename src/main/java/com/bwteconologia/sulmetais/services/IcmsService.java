package com.bwteconologia.sulmetais.services;


import com.bwteconologia.sulmetais.models.IcmsModel;
import com.bwteconologia.sulmetais.repositories.IcmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IcmsService {

    @Autowired
    private IcmsRepository stateRepository;

    public List<IcmsModel> findAll() {
        return stateRepository.findAll();
    }

    public Optional<IcmsModel> findById(Long id) {
        return stateRepository.findById(id);
    }

    public IcmsModel save(IcmsModel state) {
        return stateRepository.save(state);
    }

    public void deleteById(Long id) {
        stateRepository.deleteById(id);
    }
}
