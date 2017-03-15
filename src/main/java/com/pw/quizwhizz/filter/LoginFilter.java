package com.pw.quizwhizz.filter;

import com.pw.quizwhizz.dao.UserDao;
import com.pw.quizwhizz.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("*")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        System.out.println("F: user: " + httpReq.getSession().getAttribute("user"));
        System.out.println("F: userPrincipal: " + httpReq.getUserPrincipal() + " remoteUser " + httpReq.getRemoteUser());
        if(httpReq.getUserPrincipal() != null && httpReq.getSession().getAttribute("user") == null) {
            saveUserInSession(httpReq);
        }
        chain.doFilter(request, response);
    }

    private void saveUserInSession(HttpServletRequest request) {
        UserDao userDao = new UserDao(User.class);
        String userLogin = request.getUserPrincipal().getName();
        User userByLogin = userDao.findUserByLogin(userLogin);
        System.out.println("Sesion: add user: " + userByLogin);
        request.getSession(true).setAttribute("user", userByLogin);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}