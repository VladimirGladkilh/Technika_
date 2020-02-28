package com.company.technika.dao;

import com.company.technika.entity.DeviceType;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public class DeviceTypeRepositoryImpl implements DeviceTypeRepository {
    private final Persistence persistence;

    @Autowired
    public DeviceTypeRepositoryImpl(Persistence persistence) {
        this.persistence = persistence;
    }

    @Override
    @Transactional
    public void save(DeviceType deviceType) {
        EntityManager entityManager= persistence.getEntityManager();
        entityManager.persist(deviceType);
    }
}
