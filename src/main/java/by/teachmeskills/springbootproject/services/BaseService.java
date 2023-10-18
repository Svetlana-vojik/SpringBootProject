package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.BaseEntity;

import java.util.List;

public interface BaseService<T extends BaseEntity> {
    T create(T entity);

    List<T> read();

    T update(T entity);

    void delete(int id);
}