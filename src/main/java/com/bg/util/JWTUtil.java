package com.bg.util;

import com.bg.entity.User;
import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

/**
 * @author ctp
 * @date 2022/11/22 17:20
 */
public class JWTUtil {

    private static final String SECRET = "project_bg_token_secret";// 密钥
    private static Long expire = 1000 * 60 * 60 * 24L; // 过期时间 1天

    public static String createToken(User user,boolean remember) {
        if (user == null || user.getId() == null) {
            return null;
        }
        JwtBuilder jwtBuilder = Jwts.builder();
        expire = remember ? expire * 3 : expire;
        String token = jwtBuilder
                // header
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                // payload
                .claim("userId", user.getId())
                .claim("username", user.getUsername())
                .claim("roleId", user.getRoleId())
                .setExpiration(new Date(System.currentTimeMillis() + expire))// 过期时间
                .setId(UUID.randomUUID().toString())
                // signature
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();

        return token;
    }

    /**
     * 将 token 转换成 Claims 以便获取用户 id 和 role
     * @param token token
     * @return Claims
     */
    public static Claims parse(String token) {
        if (StringUtils.isEmpty(token)) {// 没有token
            return null;
        }
        JwtParser jwtParser = Jwts.parser();
        return jwtParser.setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

}
