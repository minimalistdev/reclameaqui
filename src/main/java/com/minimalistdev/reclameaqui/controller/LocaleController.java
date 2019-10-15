package com.minimalistdev.reclameaqui.controller;

import com.minimalistdev.reclameaqui.model.Locale;
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

import java.util.List;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class LocaleController {
    Logger logger = LoggerFactory.getLogger(LocaleController.class);

    @NonNull
    @Autowired
    private LocaleRepository localeRepository;

    @PostMapping("/locale")
    public Locale create(@Valid @RequestBody Locale locale, HttpServletResponse response) {
        logger.debug("Creating locale:  " + locale);

        response.setStatus(HttpStatus.CREATED.value());
        return localeRepository.save(locale);
    }

    @GetMapping("/locales")
    public List<Locale> list() {
        logger.debug("List all locale");

        return localeRepository.findAll();
    }

    @DeleteMapping("/locale/{id}")
    public void delete(@PathVariable("id") String localeId) {
        logger.debug("Calling delete locale with id " + localeId);
        localeRepository.deleteById(localeId);
    }
}
