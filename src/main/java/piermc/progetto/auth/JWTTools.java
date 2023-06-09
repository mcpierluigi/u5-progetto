package piermc.progetto.auth;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import piermc.progetto.exceptions.UnauthorizedException;
import piermc.progetto.users.User;

public class JWTTools {
	private static String secret;
	private static int expiration;
	
	@Value("${spring.application.jwt.secret}")
	public void setSecret(String secretKey) {
		secret = secretKey;
	}
	
	@Value("${spring.application.jwt.expiration}")
	public void setExpiration(String expirationInDay) {
		expiration = Integer.parseInt(expirationInDay) * 24 * 60 * 60 * 1000;
	}
	
	static public String createToken(User u) {
		String token = Jwts.builder().setSubject(u.getEmail()).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();
		return token;
	}
	
	static public void isTokenValid(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);	
		} catch (MalformedJwtException e) {
			throw new UnauthorizedException("This toker is malformed");
		} catch (ExpiredJwtException e){
			throw new UnauthorizedException("This toker is expired");
		} catch (Exception e) {
			throw new UnauthorizedException("Problems with the token, please do the login again");
		}
	}
	
	static public String extractSubjet(String token) {
		return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
				.build().parseClaimsJws(token).getBody().getSubject();
	}
}
