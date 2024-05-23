package demo.interview.ekan.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.interview.ekan.dto.LoginDTO;
import demo.interview.ekan.dto.TokenJwtDTO;
import demo.interview.ekan.model.AuthorizationUser;
import demo.interview.ekan.service.TokenService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/token")
@CrossOrigin(origins = "*")
public class AuthorizationController {
    @Autowired private AuthenticationManager authenticationManager;
	@Autowired private TokenService tokenService;
	
	@PostMapping
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Sucesso na execução.",
			content = { @Content(mediaType = "application/json", schema = @Schema(implementation = TokenJwtDTO.class)) }),
			@ApiResponse(responseCode = "401", description = "Não Autorizado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Foi gerada uma exceção", content = @Content), })
	public ResponseEntity<TokenJwtDTO> efetuarLogin(@RequestBody @Valid LoginDTO dto) {
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.login(), dto.senha());
		Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
		
		AuthorizationUser usuario = (AuthorizationUser)authentication.getPrincipal();
		
		String tokenJWT = tokenService.gerarToken(usuario);
		
		return ResponseEntity.ok(new TokenJwtDTO(tokenJWT));
	}
	
}
