package demo.interview.ekan.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private TokenService tokenService;

	@PostMapping
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso na execução.", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = TokenJwtDTO.class)) }),
			@ApiResponse(responseCode = "401", description = "Não Autorizado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Foi gerada uma exceção", content = @Content), })
	public ResponseEntity<TokenJwtDTO> efetuarLogin(@RequestBody @Valid LoginDTO dto) {
		try {
			List<GrantedAuthority> grantedAuths = new ArrayList<>();
			grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
			 //authenticate(dto.login(), dto.senha());
			// UsernamePasswordAuthenticationToken authenticationToken = new
			// UsernamePasswordAuthenticationToken(dto.login(), dto.senha(),grantedAuths);
			// Authentication authentication = authenticationManager.authenticate(new
			// UsernamePasswordAuthenticationToken(dto.login(), dto.senha()));
			AuthorizationUser  usuario = new AuthorizationUser();
			usuario.setLogin(dto.login());
			usuario.setPassword(dto.senha());
			

			String tokenJWT = tokenService.gerarToken(usuario);

			return ResponseEntity.ok(new TokenJwtDTO(tokenJWT));
		} catch (Exception e) {
			// throw new Exception("Error auth");
			return null;
		}

	}

	@Transactional
	private Authentication authenticate(String username, String password) throws Exception {
		try {
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credenciales invalidas");
		}
	}

}
