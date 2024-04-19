package org.example.gradpathway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.gradpathway.constants.SecurityConstants;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = request.getHeader("authorization");//Axios sends token in authorization header
        if(jwt != null) {
            try{
                //GET SECRET KEY
                SecretKey secretKey = Keys.hmacShaKeyFor(SecurityConstants
                        .JWT_KEY.getBytes(StandardCharsets.UTF_8));
                //VERIFY JWT
                Claims claims = Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(jwt)
                        .getPayload();
                //GET USERNAME AND AUTHORITIES
                String username = claims.get("username", String.class);
                List<Map<String,String>> authorities = claims.get("authorities", List.class);
                Map<String,String> authorityMap = authorities.getFirst();
                String authority = authorityMap.get("authority");
                //CREATE AUTHENTICATION OBJECT
                List<GrantedAuthority> auths = new ArrayList<>();
                auths.add(new SimpleGrantedAuthority(authority));
                //SET AUTHENTICATION OBJECT
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, auths);
                //SET AUTHENTICATION OBJECT IN SECURITY CONTEXT
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (Exception e){
                System.out.println("Invalid JWT Token");
                System.out.println(request.getHeader("authorization"));
                System.out.println(request);
                throw new BadCredentialsException("Invalid JWT Token");
            }
        }
        //CONTINUE FILTER CHAIN
        filterChain.doFilter(request, response);
    }

    //CHECK IF FILTER SHOULD BE APPLIED
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        //IF LOGIN REQUEST, DO NOT APPLY FILTER
        return request.getServletPath().equals("/api/auth/login");
    }
}
