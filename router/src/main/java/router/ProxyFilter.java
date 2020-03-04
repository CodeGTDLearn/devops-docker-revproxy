package main.java.router;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import main.java.router.utils.ManageProperties;

//ANOTA ESTA CLASSE COM SENDO O FILTRO (filterProxy)
@WebFilter(filterName = "filterProxy")
public class ProxyFilter implements Filter {
    private String routerClass = null;

    public void init(FilterConfig arg0) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        request.setAttribute("uri", req.getRequestURI().substring(req.getContextPath().length()));

        routerClass = ManageProperties.getInstance().getProp("routerClass");

        //encaminha o request p/ o classe Router
        request.getRequestDispatcher(routerClass).forward(request, response);
    }

    public void destroy() {}
}
