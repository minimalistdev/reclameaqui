package com.minimalistdev.reclameaqui.repository;

import com.minimalistdev.reclameaqui.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<Company, String> {}
