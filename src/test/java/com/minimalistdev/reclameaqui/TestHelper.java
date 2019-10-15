package com.minimalistdev.reclameaqui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minimalistdev.reclameaqui.model.Company;
import com.minimalistdev.reclameaqui.model.Locale;

class TestHelper {
    protected static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected static Locale getBuildLocale() {
        return Locale.builder().city("São Paulo").state("SP").country("Brasil").build();
    }

    protected static Locale getBuildLocale2() {
        return Locale.builder().city("Sorocaba").state("SP").country("Brasil").build();
    }

    protected static Company getBuildCompany() {
        return Company.builder().name("Coca").build();
    }

    protected static Company getBuildCompany2() {
        return Company.builder().name("Pepsi").build();
    }

}
