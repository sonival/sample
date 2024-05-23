package demo.interview.ekan.service;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import demo.interview.ekan.model.AuthorizationUser;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class TokenService {

	@Value("${api.security.token.secret}")
	private String secret;

	@Value("${api.security.token.expires}")
	private Integer expires = 2;

	private static final String ISSUER = "Ekan";
	
	public String gerarToken(AuthorizationUser user) {
		try {
		    Algorithm algoritmo = Algorithm.HMAC256(secret);
		    
			return JWT.create()
		        .withIssuer(ISSUER)
		        .withSubject(user.getLogin())
		        .withExpiresAt(dataExpiracao())
		        .sign(algoritmo);
		    
		} catch (JWTCreationException e){
		    throw new RuntimeException("Erro geracao  JWT ", e);
		}
	}

	public String getSubject(String tokenJWT) {
		try {
			Algorithm algoritmo = Algorithm.HMAC256(secret);
		    return JWT.require(algoritmo)
		        .withIssuer(ISSUER)
		        .build()
		        .verify(tokenJWT)
		        .getSubject();
		        
		} catch (JWTVerificationException e) {
			throw new RuntimeException("Token JWT inválido ou expirado!", e);
		}
	}
	
	private Instant dataExpiracao() {
		return LocalDateTime.now().plusHours(expires).toInstant(ZoneOffset.of("-03:00"));
	}
	

	public String getSubject(HttpServletRequest request) {
		String tokenJWT = this.recuperarToken(request);
		if (tokenJWT != null) return this.getSubject(tokenJWT);
		return null;
	}
	

	public String recuperarToken(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null) {
			return authorizationHeader.replace("Bearer ", "");
		}
		return null;
	}

}
