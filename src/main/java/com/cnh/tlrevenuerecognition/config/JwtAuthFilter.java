package com.cnh.tlrevenuerecognition.config;

import com.cnh.tlrevenuerecognition.security.JWTTokenHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Autowired
    private PublicURLs publicURLs;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        String requestToken = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if (requestToken != null && requestToken.startsWith("Bearer")) {

            token = requestToken.substring(7);

            try {
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            } catch (ExpiredJwtException e) {
                handleException(response, HttpStatus.UNAUTHORIZED, "Token has expired");
                return;
            } catch (MalformedJwtException e) {
                handleException(response, HttpStatus.BAD_REQUEST, "Invalid JWT Token");
                return;
            } catch (Exception e) {
                handleException(response, HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
                return;
            }
        } else {
            // Skip authentication for public APIs
            String requestURI = request.getRequestURI();
            if (!isPublicApi(requestURI)) {
                handleException(response, HttpStatus.BAD_REQUEST, "JWT token doesn't begin with bearer");
                return;
            }
        }

        //once we get the token, now validate
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (this.jwtTokenHelper.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                handleException(response, HttpStatus.UNAUTHORIZED, "Invalid JWT token");
                return;
            }
        } else {
//            handleException(response, HttpStatus.BAD_REQUEST, "Username is null or context is not null");
//            return;
        }

        filterChain.doFilter(request, response);

    }

    private boolean isPublicApi(String requestURI) {
        List<String> publicUrls = publicURLs.getUrls();
        for (String publicUrl : publicUrls) {
            if (isMatchWithPublicUrl(requestURI, publicUrl)) {
                return true;
            }
        }
        return false;
    }

    private boolean isMatchWithPublicUrl(String requestURI, String publicUrl) {
        if (publicUrl.endsWith("**")) {
            publicUrl = publicUrl.substring(0, publicUrl.length() - 2);
            return requestURI.startsWith(publicUrl);
        } else {
            return requestURI.equals(publicUrl);
        }
    }

    private void handleException(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write("{\"status\":" + status.value() + ", \"message\":\"" + message + "\"}");
    }

}
