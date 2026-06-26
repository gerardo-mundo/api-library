package mundo.org.apilibrary.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwe;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import mundo.org.apilibrary.classes.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private static String SECRET_KEY;
    @Value("${security.jwt.expiration-time}")
    private static long EXPIRATION_DATE;    

    public String generateToken(User user) {
        Map<String, Object> userClaims = new HashMap<>();
        userClaims.put("role", user.getRole().toString());

        return Jwts.builder().claims(userClaims)
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }

    public String extractUserEmail(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractClaims(token, claims -> claims.get("role").toString());
    }

    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .key().and()
                .build()
                .parse(token)
                .accept(Jwe.CLAIMS)
                .getPayload();
        return claimsResolver.apply(claims);
    }
}
