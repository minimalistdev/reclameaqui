package com.minimalistdev.reclameaqui;

import com.minimalistdev.reclameaqui.model.Company;
import com.minimalistdev.reclameaqui.model.Complain;
import com.minimalistdev.reclameaqui.model.Locale;
import com.minimalistdev.reclameaqui.repository.CompanyRepository;
import com.minimalistdev.reclameaqui.repository.ComplainRepository;
import com.minimalistdev.reclameaqui.repository.LocaleRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.minimalistdev.reclameaqui.TestHelper.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ComplainTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ComplainRepository complainRepository;

    @Autowired
    private LocaleRepository localeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Before
    public void before() {
        complainRepository.deleteAll();
        localeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @Test
    public void createAComplain() throws Exception {

        Complain complain = getBuildComplain();

        mvc.perform(
                post("/complain")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(complain))
        )
                .andDo(print()).andExpect(status().isCreated());

        Complain complainFromDb = complainRepository.findAll().get(0);

        Assert.assertEquals(complain.getDescription(), complainFromDb.getDescription());
        Assert.assertEquals(complain.getTitle(), complainFromDb.getTitle());
        Assert.assertEquals(complain.getCompany(), complainFromDb.getCompany());
        Assert.assertEquals(complain.getLocale(), complainFromDb.getLocale());
    }

    @Test
    public void getAllComplain() throws Exception {

        complainRepository.save(getBuildComplain());
        complainRepository.save(getBuildComplain2());

        mvc.perform(
                get("/complains")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(jsonPath("$.[1]").exists())
                .andExpect(status().isOk());
    }

    @Test
    public void getComplainsByCity() throws Exception {
        complainRepository.save(getBuildComplain());
        Complain complainFromSorocaba = complainRepository.save(getBuildComplain2());

        mvc.perform(
                get("/complains")
                        .param("city", "Sorocaba")
                        .param("state", "SP")
                        .param("country", "Brasil")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(jsonPath("$.[0].locale.city").value(complainFromSorocaba.getLocale().getCity()))
                .andExpect(jsonPath("$.[1]").doesNotExist())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetAnExceptionComplainsByCity() throws Exception {

        mvc.perform(
                get("/complains")
                        .param("city", "Sorocaba")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    private Complain getBuildComplain() {
        Company company = companyRepository.save(getBuildCompany());
        Locale locale = localeRepository.save(getBuildLocale());

        return Complain
                .builder()
                .company(company)
                .locale(locale)
                .description("This is the Description")
                .title("Title here")
                .build();
    }

    private Complain getBuildComplain2() {
        Company company = companyRepository.save(getBuildCompany2());
        Locale locale = localeRepository.save(getBuildLocale2());

        return Complain
                .builder()
                .company(company)
                .locale(locale)
                .description("This is the Second Description")
                .title("Title 2 here")
                .build();
    }

}
