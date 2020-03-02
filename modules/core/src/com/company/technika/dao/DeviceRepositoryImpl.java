package com.company.technika.dao;

import com.company.technika.entity.Device;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DeviceRepositoryImpl implements DeviceRepository {
    private final Persistence persistence;

    @Autowired
    private DeviceRepositoryImpl(Persistence persistence){
        this.persistence = persistence;
    }
    @Override
    @Transactional
    public void save(Device device) {
        EntityManager entityManager = persistence.getEntityManager();
        entityManager.persist(device);
    }
}
