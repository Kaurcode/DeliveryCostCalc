package com.fujitsu.deliverycostcalc.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrudService<T, R extends JpaRepository<T, Long>> {
    T save(T entity);
    List<T> saveAll(List<T> entities);
    List<T> findAll();
    long count();
}
