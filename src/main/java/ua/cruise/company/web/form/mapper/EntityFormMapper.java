package ua.cruise.company.web.form.mapper;

@FunctionalInterface
public interface EntityFormMapper<T, F> {
    F fillForm(T entity);
}