/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zerhusen.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author Ferenc
 */
public class CORSFilter extends GenericFilterBean implements Filter {

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws ServletException, IOException {
            
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpResponse.setHeader("Access-Control-Allow-Methods", "*");
            httpResponse.setHeader("Access-Control-Allow-Headers", "*");
            httpResponse.setHeader("Access-Control-Allow-Credentials", "false");
            httpResponse.setHeader("Access-Control-Max-Age", "3600");
            
            //response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
//            if ("OPTIONS".equals(request.getMethod())) {
//                response.setStatus(HttpServletResponse.SC_OK);
//            } else {
//                filterChain.doFilter(request, response);
//            }
            chain.doFilter(request, response);
        }    
}
