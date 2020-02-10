package ua.training.cruise_company_servlet.web.form.mapper;

import javax.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface RequestFormMapper<T> {
    T fillForm(HttpServletRequest request);
}
