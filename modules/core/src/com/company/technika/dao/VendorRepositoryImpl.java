package com.company.technika.dao;

import com.company.technika.entity.Vendor;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class VendorRepositoryImpl implements VendorRepository {
    private final Persistence persistence;

    @Autowired
    public VendorRepositoryImpl(Persistence persistence){
        this.persistence = persistence;
    }

    @Override
    @Transactional
    public void save(Vendor vendor) {
        EntityManager entityManager = persistence.getEntityManager();
        entityManager.persist(vendor);
    }
}
