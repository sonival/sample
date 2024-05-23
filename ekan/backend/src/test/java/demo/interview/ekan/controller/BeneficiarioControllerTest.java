package demo.interview.ekan.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import demo.interview.ekan.dto.BeneficiarioDTO;
import demo.interview.ekan.model.Beneficiario;
import demo.interview.ekan.repository.BeneficiarioRepository;
import demo.interview.ekan.service.impl.BeneficiarioServiceImpl;
import jakarta.annotation.security.RunAs;

@ActiveProfiles("test")
//@SpringBootTest
@ExtendWith(MockitoExtension.class )
@RunAs("SpringRunner.class")
@AutoConfigureMockMvc
 @TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BeneficiarioControllerTest {

    private String url = "/api/v1/";
    @Autowired
 private MockMvc mockMvc;


    @Mock
    private BeneficiarioRepository repository;

    @Mock
    private BeneficiarioServiceImpl service ;

    private final String tokenString="Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJFa2FuIiwic3ViIjoidGVzdGUiLCJleHAiOjE3MjUxMjE2MzF9._Y8w8cQCqQIP9PMIyqiVMoc8dm00J9VXO_HzVCtr44Y";

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
        this.mockMvc = MockMvcBuilders.standaloneSetup(new BeneficiarioController(service)).build();
        //this.service = new BeneficiarioServiceImpl(repository);
    }

    // @Test
    // public void TokenTest() throws Exception{
    // LoginDTO auth = new LoginDTO("demo", "demo");
    // String bodyJson = this.asJsonString(auth);
    // mockMvc.perform(post( "/token")
    // .contentType(MediaType.APPLICATION_JSON)
    // .content(bodyJson)
    // .accept(MediaType.APPLICATION_JSON))
    // .andExpect(status().isOk())
    // .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    // }

    @Test
    public void AddBeneficiarioControllerTest() throws Exception {
        Beneficiario beneficiario = Beneficiario.builder().nome("demo").telefone("0800-303030")
                .dataNascimento(LocalDate.of(2020, 10, 20)).build();

        BeneficiarioDTO dto = new BeneficiarioDTO();
        dto.setDtaNascimento(LocalDate.of(2000, 01, 01));
        dto.setNome("Jose");
        dto.setTelefone("1111");

        when(service.add(dto.parseToBenefiario())).thenReturn(beneficiario);

        String bodyJson = this.asJsonString(dto);

       this.mockMvc.perform(post(url + "beneficiario/cadastrar")
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .content(bodyJson)
                .header(HttpHeaders.AUTHORIZATION,
                        tokenString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

}
