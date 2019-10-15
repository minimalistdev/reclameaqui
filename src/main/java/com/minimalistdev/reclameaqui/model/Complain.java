package com.minimalistdev.reclameaqui.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.NonFinal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Builder
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document
public class Complain {

    @Id
    @NonFinal
    private String id;

    @NonFinal
    private String title;

    @NonFinal
    private String description;

    @DBRef
    @NonFinal
    private Locale locale;

    @DBRef
    @NonFinal
    private Company company;
}
