package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dominio.usuario.DatosAutenticacionUsuario;
import med.voll.api.dominio.usuario.Usuario;
import med.voll.api.infra.security.DatosJWT;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager AuthManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datos) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datos.login(), datos.clave());
        Authentication usuarioAutenticado = AuthManager.authenticate(authToken);

        String JWT = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        return ResponseEntity.ok(new DatosJWT(JWT));
    }

}
