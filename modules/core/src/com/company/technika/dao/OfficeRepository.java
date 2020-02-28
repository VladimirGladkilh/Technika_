package com.company.technika.dao;

import com.company.technika.entity.Office;
import org.springframework.stereotype.Repository;

public interface OfficeRepository {
    void save(Office office);
}
