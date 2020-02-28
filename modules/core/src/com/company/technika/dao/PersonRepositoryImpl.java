package com.company.technika.dao;

import com.company.technika.entity.Person;
import com.company.technika.factory.PersonFactoryImpl;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PersonRepositoryImpl implements PersonRepository {
    private final Persistence persistence;

    @Autowired
    public PersonRepositoryImpl (Persistence persistence){
        this.persistence = persistence;
    }

    @Override
    @Transactional
    public void save(Person person) {
        EntityManager entityManager= persistence.getEntityManager();
        entityManager.persist(person);
    }
}
