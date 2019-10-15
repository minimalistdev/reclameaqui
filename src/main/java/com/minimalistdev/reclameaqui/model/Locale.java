package com.minimalistdev.reclameaqui.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.NonFinal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Value
@Builder
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@CompoundIndex(unique = true, def = "{'country':1, 'state':1, 'city':1}")
@Document
public class Locale {

    @Id
    @NonFinal
    private String id;

    @NonFinal
    @NotEmpty
    private String country;

    @NonFinal
    @NotEmpty
    private String state;

    @NonFinal
    @NotEmpty
    private String city;

}
