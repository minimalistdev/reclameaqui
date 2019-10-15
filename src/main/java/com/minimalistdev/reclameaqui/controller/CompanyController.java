package com.minimalistdev.reclameaqui.controller;

import com.minimalistdev.reclameaqui.model.Company;
import com.minimalistdev.reclameaqui.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.List;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class CompanyController {
    Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @NonNull
    @Autowired
    private CompanyRepository companyRepository;

    @PostMapping("/company")
    public Company create(@Valid @RequestBody Company company, HttpServletResponse response) {
        logger.debug("Creating company:  " + company);

        response.setStatus(HttpStatus.CREATED.value());
        return companyRepository.save(company);
    }

    @GetMapping("/companies")
    public List<Company> list() {
        logger.debug("List all companies");

        return companyRepository.findAll();
    }

}
