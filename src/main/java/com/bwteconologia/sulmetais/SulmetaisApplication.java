package com.bwteconologia.sulmetais;

import com.bwteconologia.sulmetais.models.StaticParameterModel;
import com.bwteconologia.sulmetais.repositories.StaticParameterRepository;
import com.bwteconologia.sulmetais.services.StaticParameterService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@SpringBootApplication
public class SulmetaisApplication {

	public static void main(String[] args) {


		SpringApplication.run(SulmetaisApplication.class, args);

	}

//	@PostConstruct
//	public void onApplictaionStartup() {
//		StaticParameterService staticParameterService = new StaticParameterService();
//	}


}
