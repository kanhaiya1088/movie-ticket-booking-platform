package com.bookmyshow.bms.controller;

import com.bookmyshow.bms.Utils.TestDataPreparation;
import com.bookmyshow.bms.dto.TheatreDto;
import com.bookmyshow.bms.service.TheatreUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TheatreController.class)
public class TheatreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TheatreUseCase theatreUseCase;

    @Test
    void createTheaterTest() throws Exception {
        TheatreDto theatreDto = TestDataPreparation.getTheatreDto();
        Mockito.when(theatreUseCase.createTheatre(theatreDto)).thenReturn(theatreDto);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/bms/theatre")
                        .content(new ObjectMapper().writeValueAsString(theatreDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(TestDataPreparation.getTheatreDto()),mvcResult.getResponse().getContentAsString(), false);

        Mockito.verify(theatreUseCase, Mockito.times(1)).createTheatre(theatreDto);
    }
}
