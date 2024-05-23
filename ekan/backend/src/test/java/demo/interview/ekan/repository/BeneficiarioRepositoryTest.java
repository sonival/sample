package demo.interview.ekan.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import demo.interview.ekan.model.Beneficiario;
import demo.interview.ekan.service.impl.BeneficiarioServiceImpl;

@DataJpaTest(includeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BeneficiarioRepository.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BeneficiarioServiceImpl.class)
})
@ActiveProfiles("test")
public class BeneficiarioRepositoryTest {

    @Autowired
    private BeneficiarioRepository repository;

    @Autowired
    private BeneficiarioServiceImpl service;

    private Beneficiario beneficiarioTest;
    private String nameTest = "sistemTest" + LocalDate.now().toString();
    private LocalDate dtaNascimento = LocalDate.of(2000, 1, 1);
    private final Long uniqueId = 99L;

    @BeforeEach
    void SetUp() {
        beneficiarioTest = new Beneficiario("jose", "999", dtaNascimento);
        repository.save(beneficiarioTest);

        beneficiarioTest = new Beneficiario("Wanessa", "88899000", dtaNascimento);
        repository.save(beneficiarioTest);

        beneficiarioTest = new Beneficiario("Soni", "11973010049", dtaNascimento);
        repository.save(beneficiarioTest);
    }

    @AfterEach
    public void tearDown() {
        // service.removeAll();
    }

    @Test
    @Order(1)
    @DisplayName(value = "Add new beneficiario from test")
    public void beneficarioAddTest() {
        String name = "Soni0001";
        beneficiarioTest = new Beneficiario(name, "999", dtaNascimento);
        beneficiarioTest.setDataNascimento(LocalDate.now());
        beneficiarioTest = repository.saveAndFlush(beneficiarioTest);
        Beneficiario beneficiarioFInd = service.getByName(name);
        assertAll("Novo Beneficiario",
                () -> assertTrue(beneficiarioTest.getNome().equals(beneficiarioFInd.getNome())),
                () -> assertEquals(beneficiarioTest, beneficiarioFInd));
        // assertNotEquals(beneficiarioTest, beneficiarioFInd.get());

    }

    @Test
    @Order(2)
    @DisplayName(value = "Update Beneficiario test")
    public void beneficarioUpdateTest() {
        String newName = "Soni";
        Beneficiario beneficiarioFInd = service.getByName(newName);
        beneficiarioFInd.setNome(newName);
        assertEquals(newName, beneficiarioFInd.getNome());

    }

    @Test
    @Order(3)
    @DisplayName(value = "Find benficiario por nome")
    public void beneficarioFindByNameTest() {
        String Nome = "Soni";
        Beneficiario beneficiarioFInd = service.getByName(Nome);
        assertTrue(beneficiarioFInd.getNome().equals(Nome));

    }

}
