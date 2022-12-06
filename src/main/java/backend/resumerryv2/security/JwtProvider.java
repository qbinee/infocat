/* Licensed under InfoCat */
package backend.resumerryv2.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class JwtProvider {

  private final UserDetailsService userDetailsService;

  @Value("${jwt.secret}")
  private String secretKey;

  private Algorithm algorithm;

  @PostConstruct
  public void initializeAlgorithm() {
    algorithm = Algorithm.HMAC256(secretKey);
  }

  public String generateToken(String email, TokenType tokenType) {
    Integer expireTime;
    if (tokenType.equals(TokenType.ACCESS_TOKEN)) {
      expireTime = tokenType.ACCESS_TOKEN.getValue();
    } else {
      expireTime = tokenType.VALIDATION_TOKEN.getValue();
    }
    return JWT.create()
        .withSubject(email)
        .withClaim("expire", Instant.now().getEpochSecond() + Long.valueOf(expireTime))
        .sign(algorithm);
  }

  public boolean verify(String token) {
    try {
      JWT.require(algorithm).build().verify(token);
    } catch (JWTVerificationException e) {
      throw new JWTVerificationException("토큰 유효성 에러 발생");
    }
    return true;
  }

  public String decordToken(String token) {
    try {
      return JWT.decode(token).getSubject();
    } catch (JWTDecodeException e) {
      throw new IllegalArgumentException("토큰 디코딩에 실패하였습니다.");
    }
  }

  public Authentication getAuthentication(String token) {
    String email = decordToken(token);
    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }
}
