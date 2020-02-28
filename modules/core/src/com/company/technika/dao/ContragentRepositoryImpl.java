package com.company.technika.dao;

import com.company.technika.entity.Contragent;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ContragentRepositoryImpl implements ContragentRepository {
    private final Persistence persistence;

    @Autowired
    public ContragentRepositoryImpl(Persistence persistence){
        this.persistence = persistence;
    }
    @Override
    @Transactional
    public void save(Contragent contragent) {
        EntityManager entityManager=persistence.getEntityManager();
        entityManager.persist(contragent);

    }
}
