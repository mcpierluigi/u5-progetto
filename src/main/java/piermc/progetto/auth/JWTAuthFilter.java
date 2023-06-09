package piermc.progetto.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import piermc.progetto.exceptions.UnauthorizedException;
import piermc.progetto.users.User;
import piermc.progetto.users.UserService;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
	@Autowired
	UserService userService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
	throws ServletException, IOException {
		//1 Estraggo il Toker dall'Authorization Header
		String authHeader = request.getHeader("Authorization");
		if(authHeader == null || !authHeader .startsWith("Bearer "))
			throw new UnauthorizedException("Please add Toker to the authorization header");
		String accessToken = authHeader.substring(7);
		
		//2 Verifico che il Token vada bene
		JWTTools.isTokenValid(accessToken);
		
		//3.0 Estraggo la mail e ottengo lo user
		String email= JWTTools.extractSubjet(accessToken);
		User user = userService.findUserByEmail(email);
		
		//3.1 Se ok aggiungo lo user al SecurityContextHolder
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authToken);
		
		//3.2 Passiamo al prossimo blocco della filterChain
		filterChain.doFilter(request, response);	
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return new AntPathMatcher().match("/auth/**", request.getServletPath());
	}
}
