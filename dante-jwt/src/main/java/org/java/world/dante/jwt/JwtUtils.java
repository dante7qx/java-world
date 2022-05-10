package org.java.world.dante.jwt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.Key;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

public class JwtUtils {
	private static final String PRIVATE_KEY = "/Users/dante/Documents/Project/java-world/javaworld/dante-jwt/src/main/resources/private_key";
	
	public static void main(String[] args) {
//		Key key = MacProvider.generateKey();
		Key key;
		try {
			key = loadKey();
			String token = generateToken(key);
			parseToken(key, token);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	public static String generateToken(Key key) {
		Date IssuedA = Date.from(Instant.now());
		Date expDate = new Date(IssuedA.getTime() + 120000L);
		Map<String, Object> map = new HashMap<>();
		map.put("email", "dantefreedom@gmail.com");
		map.put("infos", Arrays.asList("AAA", "BBB"));
		Claims claim = new DefaultClaims(map);
		claim.setId("123456");
		claim.setSubject("dante");
		claim.setIssuedAt(IssuedA);
		claim.setExpiration(expDate);

		String compactJws = Jwts.builder()
				.setHeaderParam("typ", "jwt")
				.setSubject("Joe")
				.setClaims(claim)
				.signWith(key, SignatureAlgorithm.RS256).compact();
		System.out.println(compactJws);
		return compactJws;
	}
	
	public static void parseToken(Key key, String token) {
		try {
		    Jws<Claims> parseClaimsJws = Jwts.parserBuilder()
		    		.setSigningKey(key)
		    		.build()
		    		.parseClaimsJws(token);
		    //OK, we can trust this JWT
		    JwsHeader<?> header = parseClaimsJws.getHeader();
		    Claims body = parseClaimsJws.getBody();
		    String sign = parseClaimsJws.getSignature();
		    
		    System.out.println(header.getType() + ' ' + header.getAlgorithm());
		    System.out.println(body);
		    System.out.println(sign);
		} catch (SecurityException | MalformedJwtException e) {
            // don't trust the JWT!
            // jwt 解析错误
        } catch (ExpiredJwtException e) {
            // TODO: handle exception
            // jwt 已经过期，在设置jwt的时候如果设置了过期时间，这里会自动判断jwt是否已经过期，如果过期则会抛出这个异常，我们可以抓住这个异常并作相关处理。
        		System.out.println(e.getMessage());
        }
	}
	
	/**
     * 从硬盘中加载私钥
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     */
    private static Key loadKey() throws IOException, FileNotFoundException,
            ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(
                new FileInputStream(new File(PRIVATE_KEY)));
        Key privateKey = (Key) inputStream.readObject();
        return privateKey;
    }
}
