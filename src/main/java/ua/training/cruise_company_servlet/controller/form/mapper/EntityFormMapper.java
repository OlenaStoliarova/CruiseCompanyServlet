package ua.training.cruise_company_servlet.controller.form.mapper;

@FunctionalInterface
public interface EntityFormMapper<T, F> {
    F fillForm(T entity);
}