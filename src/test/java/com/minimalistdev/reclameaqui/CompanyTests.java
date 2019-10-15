package com.minimalistdev.reclameaqui;

import com.minimalistdev.reclameaqui.model.Company;
import com.minimalistdev.reclameaqui.repository.CompanyRepository;
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

import static com.minimalistdev.reclameaqui.TestHelper.asJsonString;
import static com.minimalistdev.reclameaqui.TestHelper.getBuildCompany;
import static com.minimalistdev.reclameaqui.TestHelper.getBuildCompany2;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CompanyTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CompanyRepository companyRepository;

    @Before
    public void before() {
        companyRepository.deleteAll();
    }

    @Test
    public void createACompany() throws Exception {

        Company company = getBuildCompany();

        mvc.perform(
                post("/company")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(company))
        )
                .andDo(print()).andExpect(status().isCreated());


        Company companyFromDb = companyRepository.findAll().get(0);

        Assert.assertEquals(company.getName(), companyFromDb.getName());
    }

    @Test
    public void getAllCompanies() throws Exception {

        companyRepository.save(getBuildCompany());
        companyRepository.save(getBuildCompany2());

        mvc.perform(
                get("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(jsonPath("$.[1]").exists()).andExpect(status().isOk());

    }

    @Test
    public void shouldGetAnErrorOnCreateACompanyThatAlreadyExists() throws Exception {

        Company company = companyRepository.save(getBuildCompany());

        companyRepository.save(company);
        company.setId(null);

        mvc.perform(
                post("/company")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(company))
        )
                .andDo(print()).andExpect(status().is4xxClientError());

    }

}
