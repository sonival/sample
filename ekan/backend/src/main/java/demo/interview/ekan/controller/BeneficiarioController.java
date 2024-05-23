package demo.interview.ekan.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import demo.interview.ekan.dto.BeneficiarioDTO;
import demo.interview.ekan.model.Beneficiario;
import demo.interview.ekan.service.impl.BeneficiarioServiceImpl;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/beneficiario")
@SecurityRequirement(name = "bearerAuth")
public class BeneficiarioController {

    @Autowired
    private BeneficiarioServiceImpl beneficiarioService;

    public BeneficiarioController(BeneficiarioServiceImpl service) {
        super();
        this.beneficiarioService = service;
    }

    @RequestMapping(value = "/adicionar", method = RequestMethod.POST)
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Criado com sucesso.", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = BeneficiarioDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Não Autorizado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção", content = @Content), })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addBpomeneficiario(@RequestBody final BeneficiarioDTO beneficiario) {
        try {
            Beneficiario response = beneficiarioService.add(beneficiario.parseToBenefiario());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Não foi possivel realizar a inclusão", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/listar/todos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso na execução.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BeneficiarioDTO.class))) }),
            @ApiResponse(responseCode = "401", description = "Não Autorizado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção", content = @Content), })
    public ResponseEntity<List<BeneficiarioDTO>> getAllBeneficiarios() {
        List<Beneficiario> allBeneficiario = beneficiarioService.getAll();
        List<BeneficiarioDTO> response = allBeneficiario.stream().map(f -> f.parseBeneficiarioDTO())
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("{id}")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Sucesso na execução.",
			content = { @Content(mediaType = "application/json", schema = @Schema(implementation = BeneficiarioDTO.class)) }),
			@ApiResponse(responseCode = "401", description = "Não Autorizado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Foi gerada uma exceção", content = @Content), })
	public ResponseEntity<BeneficiarioDTO> buscarBeneficiario(@PathVariable final Long id) {
		var resp = beneficiarioService.getByID(id);
        return new ResponseEntity<>(resp.parseBeneficiarioDTO(), HttpStatus.OK);
	}


    @DeleteMapping("{id}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Registro excluído.", content = @Content),
			@ApiResponse(responseCode = "401", description = "Não Autorizado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Foi gerada uma exceção", content = @Content) })
	public ResponseEntity<?> deletarBeneficiario(@PathVariable final Long id) {
		try {
            beneficiarioService.removeById(id);
            return new ResponseEntity<>("Excuido com sucesso", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
       
	}


    @PutMapping("{id}")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Sucesso na execução.",
			content = { @Content(mediaType = "application/json", schema = @Schema(implementation = BeneficiarioDTO.class))}),
			@ApiResponse(responseCode = "401", description = "Não Autorizado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Foi gerada uma exceção", content = @Content), })
	public ResponseEntity<?> atualizar(@PathVariable final Long id, @RequestBody final BeneficiarioDTO dto) {
        Beneficiario benef = dto.parseToBenefiario();
        try {
            Beneficiario resp = beneficiarioService.updatBeneficiario(benef);
            return new ResponseEntity<>(resp.parseBeneficiarioDTO(), HttpStatus.OK);      
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
      
	}

}
