package demo.interview.ekan.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import demo.interview.ekan.dto.BeneficiarioDTO;
import demo.interview.ekan.model.Beneficiario;
import demo.interview.ekan.service.impl.BeneficiarioServiceImpl;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/beneficiario")
public class BeneficiarioController {

    @Autowired
    private BeneficiarioServiceImpl beneficiarioService;

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Beneficiario> addBpomeneficiario(@RequestBody BeneficiarioDTO beneficiario) {
        Beneficiario response = beneficiarioService.add(beneficiario.parseToBenefiario());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/listar/todos")
    public ResponseEntity<List<BeneficiarioDTO>>  getAllBeneficiarios() {
        List<Beneficiario> allBeneficiario = beneficiarioService.getAll();
         List<BeneficiarioDTO> response = allBeneficiario.stream().map(f-> f.parseBeneficiarioDTO()).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    

}
