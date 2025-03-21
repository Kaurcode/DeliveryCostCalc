package com.fujitsu.deliverycostcalc.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class CrudServiceImpl<T, R extends JpaRepository<T, Long>> implements CrudService<T, R> {
    protected final R repository;

    protected CrudServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public long count() {
        return repository.count();
    }
}
