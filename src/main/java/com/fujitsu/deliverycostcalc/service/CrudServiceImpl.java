package com.fujitsu.deliverycostcalc.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public abstract class CrudServiceImpl<T, R extends JpaRepository<T, Long>> implements CrudService<T, R> {
    protected final R repository;

    protected CrudServiceImpl(R repository) {
        this.repository = repository;
    }

    /**
     * Saves (persists) an entity into database
     * @param entity Entity to be persisted into database
     * @return The now persisted entity
     */
    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    /**
     * Saves all the entities from the list into database
     * @param entities List of entities to be saved into database
     * @return All the now persisted entities
     */
    @Override
    public List<T> saveAll(List<T> entities) {
        ArrayList<T> savedEntities = new ArrayList<T>();

        for (T entity : entities) {
            savedEntities.add(repository.save(entity));
        }

        return savedEntities;
    }

    /**
     * Returns a list of all entities in a database table
     * @return A list of all entities in a database table
     */
    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    /**
     * Returns the count of all entities in the database table
     * @return The count of entities
     */
    @Override
    public long count() {
        return repository.count();
    }
}
