package com.minimalistdev.reclameaqui;

import com.minimalistdev.reclameaqui.model.Locale;
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

import static com.minimalistdev.reclameaqui.TestHelper.asJsonString;
import static com.minimalistdev.reclameaqui.TestHelper.getBuildLocale;
import static com.minimalistdev.reclameaqui.TestHelper.getBuildLocale2;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LocaleTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private LocaleRepository localeRepository;

    @Before
    public void before() {
        localeRepository.deleteAll();
    }

    @Test
    public void createALocation() throws Exception {

        Locale locale = getBuildLocale();

        mvc.perform(
                post("/locale")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(locale))
        )
                .andDo(print()).andExpect(status().isCreated());


        Locale localeFromDb = localeRepository.findAll().get(0);

        Assert.assertEquals(locale.getCity(), localeFromDb.getCity());
        Assert.assertEquals(locale.getState(), localeFromDb.getState());
        Assert.assertEquals(locale.getCountry(), localeFromDb.getCountry());
    }


    @Test
    public void createALocationErrorOnEmptyValue() throws Exception {

        Locale locale = getBuildLocale();
        locale.setCity("");

        mvc.perform(
                post("/locale")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(locale))
        )
                .andDo(print()).andExpect(status().is4xxClientError());

    }

    @Test
    public void getAllLocation() throws Exception {

        localeRepository.save(getBuildLocale());
        localeRepository.save(getBuildLocale2());

        mvc.perform(
                get("/locales")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(jsonPath("$.[1]").exists()).andExpect(status().isOk());

    }

    @Test
    public void deleteLocation() throws Exception {

        Locale locale = localeRepository.save(getBuildLocale());

        mvc.perform(
                delete("/locale/" + locale.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());

        Assert.assertTrue(localeRepository.findAll().isEmpty());
    }

    @Test
    public void shouldGetAnErrorOnCreateALocaleThatAlreadyExists() throws Exception {

        Locale locale = localeRepository.save(getBuildLocale());

        localeRepository.save(locale);
        locale.setId(null);

        mvc.perform(
                post("/locale")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(locale))
        )
                .andDo(print()).andExpect(status().is4xxClientError());

    }
}
