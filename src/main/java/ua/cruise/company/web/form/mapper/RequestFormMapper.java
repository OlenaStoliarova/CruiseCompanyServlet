package ua.cruise.company.web.form.mapper;

import javax.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface RequestFormMapper<T> {
    T fillForm(HttpServletRequest request);
}
