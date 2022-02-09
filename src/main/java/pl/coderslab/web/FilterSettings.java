package pl.coderslab.web;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;


@WebFilter("/*")
public class FilterSettings implements javax.servlet.Filter {

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        filterChain.doFilter(request, response);
    }

    public void destroy() {
    }

    public void init(FilterConfig config) throws ServletException {

    }
}
