package com.minimalistdev.reclameaqui.repository;

import com.minimalistdev.reclameaqui.model.Complain;
import com.minimalistdev.reclameaqui.model.Locale;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ComplainRepository extends MongoRepository<Complain, String> {
    List<Complain> findByLocale(Locale locale);
}
