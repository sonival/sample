package demo.interview.ekan.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import demo.interview.ekan.dto.BeneficiarioDTO;
import demo.interview.ekan.model.Beneficiario;
import demo.interview.ekan.service.impl.BeneficiarioServiceImpl;

//@SpringBootTest
@AutoConfigureMockMvc
public class BeneficiarioControllerTest {

    private String url = "/api/v1/beneficiario";
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BeneficiarioServiceImpl service;

    private ObjectMapper objectMapper;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void AddBeneficiarioControllerTest() throws Exception {
        Beneficiario beneficiario =  Beneficiario.builder().nome("demo").telefone("0800-303030").dataNascimento(LocalDate.of(2020, 10, 20)).build();


        BeneficiarioDTO dto = new BeneficiarioDTO();
        dto.setDtaNascimento(LocalDate.of(2000, 01, 01));
        dto.setNome("Jose");
        dto.setTelefone("1111");

        when(service.add(dto.parseToBenefiario())).thenReturn(beneficiario);

        String bodyJson = this.asJsonString(dto);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                 .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .content(bodyJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

}
