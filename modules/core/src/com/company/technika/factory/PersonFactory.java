package com.company.technika.factory;

import com.company.technika.entity.Office;
import com.company.technika.entity.Person;
import com.company.technika.entity.Post;

public interface PersonFactory {
    Person create(String fam, String nam, String otch, String email, String phone, String officeName, String postName);
    Person create(String fam, String nam, String otch, String email, String officeName, String postName);
    Person create(String fam, String nam, String otch, String email, String phone, Office office, Post post);
    Person create(String fam, String nam, String otch, String email, Office office, Post post);
}
