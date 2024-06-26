package demo.interview.ekan.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
@Configuration
@OpenAPIDefinition(info = @Info(title = "Ekan API", version = "v1"))
@SecurityScheme(
    name = "bearerAuth",
    scheme = "bearer",
    bearerFormat = "JWT", 
    type = SecuritySchemeType.HTTP
)
public class SpringDocConfig {
    
}
