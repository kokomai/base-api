package com.foresys.api.system.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.foresys.api.system.jwt.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtCheckFilter extends OncePerRequestFilter {
	@Value("${props.uri-prefix}")
	private String prefix;
	@Value("${props.ignore-path}")
	private String[] tokenIgnorePaths;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if(!isIgnoreUri(request.getRequestURI())) {
			String token = jwtUtil.resolveAToken(request);
			
			if(token != null && !"".equals(token)) {
				// check Access Token (aToken)
				if(!jwtUtil.isTokenExpired(token)) {
					Claims claims = jwtUtil.getClaims(token);
					String id = (String) claims.get("id");
					
					// returns New Access Token
					token = jwtUtil.createAccessToken(id, "");
					
					response.setHeader("X-AUTH-ATOKEN", token);
					filterChain.doFilter(request, response);
				} else {
					// when aToken is expired
					
					// check Refresh Token is correct (rToken)
					token = jwtUtil.resolveRToken(request);
					// TODO : should get saved rToken info from DB 
					String baseRToken = "";
					baseRToken = token;
					
					if(baseRToken.equals(token)) {
						// check Refresh Token is expired
						if(!jwtUtil.isTokenExpired(token)) {
							Claims claims = jwtUtil.getClaims(token);
							String id = (String) claims.get("id");
							
							// returns New Access Token
							token = jwtUtil.createAccessToken(id, "");
							
							response.setHeader("X-AUTH-ATOKEN", token);
							filterChain.doFilter(request, response);
						} else {
							// rToken is expired
							response.sendError(401);
						}
					} else {
						// rToken is not correct
						response.sendError(401);
					}
				}
				
			} else {
				log.info("there's no token");
				response.sendError(401);
			}
		} else {
			log.info("Ignore Path Contained..");
			filterChain.doFilter(request, response);
		}
	}
	
	private boolean isIgnoreUri(String uri) {
		String path = uri.replace(prefix, "");
		
		for(String ignorePath : tokenIgnorePaths) {
			log.info("replaced :::: " + ignorePath.replace("/*", ""));
			if(ignorePath.equals(path)) {
				return true;
			} else {
				log.info("path ::::: " + path);
				if(path.contains(ignorePath.replace("/*", ""))) {
					log.info("is astrisk matched :::: " + true);
					return true;
				}
			}
		}		
		
		return false;
	}
	
}
