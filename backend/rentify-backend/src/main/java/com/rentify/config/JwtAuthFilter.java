package com.rentify.config;

import com.rentify.service.MyUserDetailsService;
import com.rentify.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil;

  private final ApplicationContext context;

  public JwtAuthFilter(JwtUtil jwtUtil, ApplicationContext context) {
    this.jwtUtil = jwtUtil;
    this.context = context;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    final String header = request.getHeader("Authorization");
    String userEmail = null;
    String jwtToken = null;

    if (header != null && header.startsWith("Bearer ")) {
      jwtToken = header.substring(7);

      try {
        userEmail = jwtUtil.getUserEmailFromToken(jwtToken);


      } catch (IllegalArgumentException e) {
        System.out.println("Unable to get jwt token");
      } catch (Throwable e) {
        System.out.println("Jwt token is expired");
      }

    } else {
      System.out.println("Jwt token is in-valid from JwtAuthFilter");
    }

    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails =
          context.getBean(MyUserDetailsService.class).loadUserByUsername(userEmail);

      if (jwtUtil.validateToken(jwtToken, userDetails)) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        usernamePasswordAuthenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }
    filterChain.doFilter(request, response);
  }
}
