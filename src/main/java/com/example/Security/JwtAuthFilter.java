package com.example.Security;

import com.example.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (
            header != null &&
            header.startsWith("Bearer ")
            // SecurityContextHolder.getContext().getAuthentication() == null
        ) {
            String token = header.substring("Bearer ".length());

            if (jwtService.isValid(token)) {
                String username = jwtService.extractUsername(token);

                userRepository.findByUsername(username).ifPresent(user -> {
                    var authentication =
                        new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            Collections.emptyList()
                        );
                    authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(
                            request
                        )
                    );
                    SecurityContextHolder.getContext().setAuthentication(
                        authentication
                    );
                });
            }
        }
        filterChain.doFilter(request, response);
    }
}
