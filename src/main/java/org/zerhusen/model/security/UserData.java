/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zerhusen.model.security;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RestController;
import org.zerhusen.security.JwtTokenUtil;
import org.zerhusen.security.JwtUser;

/**
 *
 * @author Ferenc
 */
@RestController
public class UserData {

    public JwtUser getAuthenticatedUser(String tokenHeader, JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService, HttpServletRequest requesta) {
        String token = requesta.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return user;
    }

}
