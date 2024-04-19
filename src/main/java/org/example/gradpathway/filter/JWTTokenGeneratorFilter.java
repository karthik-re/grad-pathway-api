package org.example.gradpathway.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.gradpathway.constants.SecurityConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //GET AUTHENTICATION OBJECT
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            //GENERATE KEY
            SecretKey secretKey = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
            //GENERATE JWT
            String jwt = Jwts.builder()
                    .issuer("GradPathway")//ISSUER
                    .subject("JWT Token")//SUBJECT
                    .claim("username", authentication.getName())//USERNAME
                    .claim("authorities", authentication.getAuthorities())//AUTHORITIES
                    .issuedAt(new java.util.Date())//ISSUED AT
                    .expiration(new java.util.Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))//EXPIRATION TIME, 12 HOURS
                    .signWith(secretKey)//SIGN WITH SECRET KEY
                    .compact();//BUILD
            //SET JWT IN HEADER
            response.setHeader(SecurityConstants.JWT_HEADER, jwt);
        }
        //CONTINUE FILTER CHAIN
        filterChain.doFilter(request, response);
    }

    //CHECK IF FILTER SHOULD NOT BE APPLIED
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        //CHECK IF REQUEST IS NOT LOGIN
        return !request.getServletPath().equals("/api/auth/login");
    }
}
