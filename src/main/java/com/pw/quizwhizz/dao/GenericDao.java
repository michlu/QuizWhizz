package com.pw.quizwhizz.dao;

import java.util.List;

public interface GenericDao<T> {

    Boolean saveOrUpdate(T entity);

    Boolean delete(T entity);

    T find(Long id);

    List<T> findAll();
}