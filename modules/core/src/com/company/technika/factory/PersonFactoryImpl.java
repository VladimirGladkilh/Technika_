package com.company.technika.factory;

import com.company.technika.entity.Office;
import com.company.technika.entity.Person;
import com.company.technika.entity.Post;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class PersonFactoryImpl implements PersonFactory {
    private final Metadata metadata;

    @Autowired
    private PersonFactoryImpl (Metadata metadata){
        this.metadata = metadata;
    }
    @Override
    public Person create(String fam, String nam, String otch, String email, String phone, String officeName, String postName) {
        Person person = metadata.create(Person.class);
        person.setFamilia(fam);
        person.setImya(nam);
        person.setOtchestvo(otch);
        person.setEmail(email);
        person.setPhone(phone);
        if (!postName.equals("")) {
            Post post;
            post = getPostByName(postName);
            if (post != null){
                person.setPost(post);
            }
        }
        if (!officeName.equals("")){
            Office office;
            office = getOfficeByName(officeName);
            if (office != null){
                person.setOffice(office);
            }
        }
        return person;
    }

    @Override
    public Person create(String fam, String nam, String otch, String email, String officeName, String postName) {
        Person person = metadata.create(Person.class);
        person.setFamilia(fam);
        person.setImya(nam);
        person.setOtchestvo(otch);
        person.setEmail(email);
        if (!postName.equals("")) {
            Post post;
            post = getPostByName(postName);
            if (post != null){
                person.setPost(post);
            }
        }
        if (!officeName.equals("")){
            Office office;
            office = getOfficeByName(officeName);
            if (office != null){
              person.setOffice(office);
            }
        }
        return person;
    }

    @Override
    public Person create(String fam, String nam, String otch, String email, String phone, Office office, Post post) {
        Person person = metadata.create(Person.class);
        person.setFamilia(fam);
        person.setImya(nam);
        person.setOtchestvo(otch);
        person.setEmail(email);
        person.setPhone(phone);
        person.setPost(post);
        person.setOffice(office);

        return person;
    }

    @Override
    public Person create(String fam, String nam, String otch, String email, Office office, Post post) {
        Person person = metadata.create(Person.class);
        person.setFamilia(fam);
        person.setImya(nam);
        person.setOtchestvo(otch);
        person.setEmail(email);
        person.setPost(post);
        person.setOffice(office);
        return person;
    }

    @Inject
    private DataManager dataManager;

    private Post getPostByName(String postName){
        String postQuery="select p from technika_Post p where lower(trim(p.name))=:name and p.deleteTs is null";
        LoadContext<Post> lcp = LoadContext.create(Post.class)
                .setQuery(LoadContext.createQuery(postQuery)
                .setParameter("name", postName.toLowerCase())
                        .setMaxResults(1));
        //Post post = dataManager.load(lcp);
        return dataManager.load(lcp);
    }
    private Office getOfficeByName(String officeName){
        String officeQuery="select p from technika_Office p where lower(trim(p.name))=:name and p.deleteTs is null";
        LoadContext<Office> lcp = LoadContext.create(Office.class)
                .setQuery(LoadContext.createQuery(officeQuery)
                        .setParameter("name", officeName.toLowerCase())
                        .setMaxResults(1));
        //Office office = dataManager.load(lcp);
        return dataManager.load(lcp);
    }
}
