package com.minimalistdev.reclameaqui.controller;

import com.minimalistdev.reclameaqui.exception.LocaleNotFoundException;
import com.minimalistdev.reclameaqui.model.Complain;
import com.minimalistdev.reclameaqui.model.Locale;
import com.minimalistdev.reclameaqui.repository.ComplainRepository;
import com.minimalistdev.reclameaqui.repository.LocaleRepository;
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

import java.security.InvalidParameterException;
import java.util.List;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class ComplainController {
    Logger logger = LoggerFactory.getLogger(ComplainController.class);

    @NonNull
    @Autowired
    private ComplainRepository complainRepository;

    @NonNull
    @Autowired
    private LocaleRepository localeRepository;

    @PostMapping("/complain")
    public Complain create(@Valid @RequestBody Complain complain, HttpServletResponse response) {
        logger.debug("Creating complain:  " + complain);

        response.setStatus(HttpStatus.CREATED.value());
        return complainRepository.save(complain);
    }

    @GetMapping("/complains")
    public List<Complain> list(@RequestParam(value = "city", required = false) String city,
                               @RequestParam(value = "state", required = false) String state,
                               @RequestParam(value = "country", required = false) String country) throws Exception {

        logger.debug("List all complains");

        if (city == null && state == null && country == null) {
            return complainRepository.findAll();
        }

        if (city != null && state != null && country != null ) {
            Locale localeByCity = localeRepository.findByCity(city);
            if(localeByCity == null){
                throw new LocaleNotFoundException(city + " not found");
            }

            return complainRepository.findByLocale(localeByCity);
        }

        throw new InvalidParameterException("Invalid params");
    }

}
