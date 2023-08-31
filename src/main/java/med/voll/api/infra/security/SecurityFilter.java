package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.dominio.usuario.UsuarioRepository;
import med.voll.api.infra.errores.InvalidJWTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtener token del header (fault tolerant)
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            // Si llega header, validar token
            String token = authHeader.replace("Bearer ", "");
            String subject = tokenService.getSubject(token);

            if (subject != null) {
                // Si hay subject el token es válido
                // Buscamos el usuario
                UserDetails usuario = usuarioRepository.findByLogin(subject);
                // Forzamos un inicio de sesión para que Spring lo tome como autenticado
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
