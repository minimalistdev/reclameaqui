package com.minimalistdev.reclameaqui.repository;

import com.minimalistdev.reclameaqui.model.Locale;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LocaleRepository extends MongoRepository<Locale, String> {
    Locale findByCity(String city);
}
