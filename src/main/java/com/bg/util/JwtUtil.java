package com.bg.util;

import com.bg.config.KeysProperties;
import com.bg.entity.User;
import io.jsonwebtoken.*;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author ctp
 * @date 2022/11/22 17:20
 */
public class JwtUtil {

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
                .claim(KeysProperties.TOKEN_USER_ID_KEY, user.getId())
                .claim(KeysProperties.TOKEN_USERNAME_KEY, user.getUsername())
                .claim(KeysProperties.TOKEN_ROLE_ID_KEY, user.getRoleId())
                .setExpiration(new Date(System.currentTimeMillis() + expire))// 过期时间
                .setId(UUID.randomUUID().toString())
                // signature
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();

        return token;
    }

    /**
     * 从 headers 获取用户 token，token 转换成 Claims 以便获取用户 id 和 role id
     * @param headers 请求的头部信息
     * @return Claims
     */
    public static Claims parse(Map<String,String> headers) {
        String token = headers.get(KeysProperties.TOKEN_KEY);
        JwtParser jwtParser = Jwts.parser();
        return jwtParser.setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    /**
     * 将 token 转换成 Claims 以便获取用户 id 和 role
     * @param token 登录之后派发的令牌
     * @return Claims
     */
    public static Claims parse(String token) {
        JwtParser jwtParser = Jwts.parser();
        return jwtParser.setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    /**
     * 将token转换成 Claims 从中获取存放的对应令牌信息 如 用户id
     * @param token 登录之后派发的令牌
     * @return Claims
     */
    public static String claims(String token, String key) {
        return parse(token).get(key).toString();
    }

    /**
     * 将token转换成 Claims 从中获取存放的对应令牌信息 如 用户id
     * @param headers 请求的头部信息
     * @return Claims
     */
    public static String claims(Map<String,String > headers, String key) {
        return parse(headers).get(key).toString();
    }
}
