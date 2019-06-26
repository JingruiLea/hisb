package edu.neu.his.interceptor;
import edu.neu.his.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CrossInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(CrossInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (request.getHeader(HttpHeaders.ORIGIN) != null) {
            String origin = request.getHeader("Origin");
            response.setHeader("Access-Control-Allow-Origin", origin);
        } else {
            response.setHeader("Access-Control-Allow-Origin", "*");
        }
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, PATCH, HEAD");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with");
        response.setHeader("Access-Control-Max-Age", "3600");

        return true;
    }
}