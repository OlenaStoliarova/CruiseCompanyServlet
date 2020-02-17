package ua.cruise.company.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

public class SessionLocaleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        String chosenLanguage = req.getParameter("lang");
        if (chosenLanguage != null) {
            req.getSession().setAttribute("lang", chosenLanguage);
            setDefaultLocaleByUserChoice(chosenLanguage);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}


    private void setDefaultLocaleByUserChoice(String chosenLanguage){

        if(chosenLanguage.equalsIgnoreCase("uk")) {
            Locale.setDefault(new Locale("uk")); // Ukrainian
            return;
        }

        Locale.setDefault(new Locale("en")); // default - English
    }
}

