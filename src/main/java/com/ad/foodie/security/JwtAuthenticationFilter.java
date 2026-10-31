package com.ad.foodie.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component 
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtSecurityService jwtSecurityService;
    private UserDetailsService userDetailsService;

    @Autowired 
    @Qualifier ("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    //Since JwtAuthenticationFilter runs before Spring MVC's DispatcherServlet, exceptions thrown inside the filter chain will bypass @RestControllerAdvice by default.
    
    // To route exceptions from your filter to @RestControllerAdvice, delegate them using HandlerExceptionResolver:

    
    @Autowired 
    public JwtAuthenticationFilter(JwtSecurityService jwtSecurityService, UserDetailsService userDetailsService) {
        this.jwtSecurityService = jwtSecurityService;
        this.userDetailsService = userDetailsService;
    }

    


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        try {
            /// Authorization-->> Bearer token
            /// 
            String authorization= request.getHeader("Authorization");
            if(authorization != null && authorization.startsWith("Bearer")){
                String token=authorization.substring(7);
                if(jwtSecurityService.validateToken(token)){
                    //Set SecurityContext Authentication --->>>authentication-->
                    
                    String username=jwtSecurityService.getUsername(token);
                    UserDetails userDetails=userDetailsService.loadUserByUsername(username);
                    if(SecurityContextHolder.getContext().getAuthentication()==null){
                        UsernamePasswordAuthenticationToken authenticationToken=new 
                                                    UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                        // UsernamePasswordAuthenticationToken(principal, credentials, authorities)
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
            filterChain.doFilter(request, response);
                
        } catch (Exception e) {
            
            // Forward the exception to @RestControllerAdvice  ///GlobalExceptionHandler
            resolver.resolveException(request, response, null, e);
            return;
        
        }



                

            
            
            
    
    }



}
