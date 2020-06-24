package cn.edu.jmu.security.util;

import cn.edu.jmu.security.config.UserToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author sgh
 * @date 2019/8/14 下午4:47
 */
public class JwtTokenUtil implements Serializable {

    public static final long serialVersionUID = 2750365505709604647L;

    public static final String LOGIN_TYPE = "loginType";

    public static final String SECRET = "MySecret";

    // Token过期时间，单位：秒
    public static final Long EXPIRATION = 7 * 24 * 60 * 60L;

    public static final String HEADER = "Authorization";

    public static final String BEARER = "Bearer ";

    public static String generateToken(UserToken userToken) {
        // 写入自定义数据
        Map<String, Object> claims = new HashMap<>(10);
        claims.put(LOGIN_TYPE, userToken.getLoginType());
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + EXPIRATION * 1000);
        System.out.println(expirationDate);
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userToken.getUsername())
            .setIssuedAt(createdDate)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();
    }

    /**
     * 验证token是否合法, 自定义验证方式
     */
    public static boolean validateToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return !isTokenExpired(token);
    }

    /**
     * token是否过期
     */
    public static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public static Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token)
            .getBody();
    }

    public static String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public static String getLoginTypeFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return (String) claims.get(LOGIN_TYPE);
    }

    public static Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public static Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public static Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    public static Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
            && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public static String refreshToken(String token) {
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + EXPIRATION * 1000);
        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);
        return Jwts.builder()
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();
    }

    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader(JwtTokenUtil.HEADER);
        // 判断请求头是否带上了token
        if (!StringUtils.isBlank(token) && token.startsWith(JwtTokenUtil.BEARER)) {
            token = token.substring(JwtTokenUtil.BEARER.length());
        }
        return token;
    }
}
