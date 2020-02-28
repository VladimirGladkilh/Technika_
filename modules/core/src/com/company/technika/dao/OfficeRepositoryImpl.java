package com.company.technika.dao;

import com.company.technika.entity.Office;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OfficeRepositoryImpl implements OfficeRepository {
    private final Persistence persistence;

    @Autowired
    public OfficeRepositoryImpl(Persistence persistence) {
        this.persistence = persistence;
    }

    @Override
    @Transactional
    public void save(Office office) {
        EntityManager entityManager = persistence.getEntityManager();
        entityManager.persist(office);
    }
}
