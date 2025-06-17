package main.natationtn.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "3ba228c2dd71a5692806fe2d7e68907be433155c3687a9b9701a0bb2e1c4f7d670e9031c6c3499b79c7b430e0b4b4cf9956090f4b6ea6b3c1ab1297229e90e8cfca08f59f5dc8043178a95f0c382214c66ba4e7973f6248a643ae9a168878bf54dfae3b350bf71373ee78a856d17aa396f3f3fcc0327624dbb8166322dde2ef3980ba1ed2192d180fd219dff42bea8a37d8322be4d25bedc90043e561218f74b80403cdc7684ac486d0bc2ca5e86a6fba2087caade13bf6f39b638ca88a0b4415e4f5f8add599212021aaa9cc298400969d4cb5b7967ee1ca0c61edaeb17b4fd497da9c7ce856021faa65044da33f046429c4ea61e114d0877a3a5a776935562";
    private static final long EXPIRATION_MS = 24 * 60 * 60 * 1000;

    public String extractUserEmail ( String token ) {
        return extractClaim ( token , Claims::getSubject );
    }

    public < T > T extractClaim ( String token , Function < Claims, T > claimsResolver ) {
        final Claims claims = extractAllClaims ( token );
        return claimsResolver.apply ( claims );
    }


    private Claims extractAllClaims ( String token ) {
        return Jwts.parserBuilder ( )
                .setSigningKey ( getSignInKey ( ) )
                .build ( )
                .parseClaimsJws ( token )
                .getBody ( );
    }

    public boolean isTokenValid ( String token , UserDetails userDetails ) {
        final String username = extractUserEmail ( token );
        return (username.equals ( userDetails.getUsername ( ) ) && !isTokenExpired ( token ));
    }

    public boolean isTokenExpired ( String token ) {
        return extractExpiration ( token ).before ( new Date ( ) );
    }

    private Date extractExpiration ( String token ) {
        return extractClaim ( token , Claims::getExpiration );
    }


    private Key getSignInKey () {
        byte[] keyBytes = Decoders.BASE64.decode ( SECRET_KEY );
        return Keys.hmacShaKeyFor ( keyBytes );
    }

    public String generateToken ( UserDetails userDetails ) {
        return generateToken ( new HashMap <> ( ) , userDetails );
    }

    public String generateToken (
            Map < String, Object > extraClaims ,
            UserDetails userDetails
    ) {
        Date now = new Date ( );
        Date exp = new Date ( now.getTime ( ) + EXPIRATION_MS );

        List < String > roles = userDetails.getAuthorities ( )
                .stream ( )
                .map ( GrantedAuthority::getAuthority )
                .toList ( );

        extraClaims = new HashMap <> ( extraClaims );
        extraClaims.put ( "roles" , roles );


        return Jwts.builder ( )
                .setClaims ( extraClaims )
                .setSubject ( userDetails.getUsername ( ) )
                .setIssuedAt ( now )
                .setExpiration ( exp )
                .signWith ( getSignInKey ( ) , SignatureAlgorithm.HS256 )
                .compact ( );
    }

}
