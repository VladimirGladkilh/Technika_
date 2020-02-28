package com.company.technika.service;

import com.haulmont.cuba.core.entity.FileDescriptor;

import java.io.IOException;
import java.util.ArrayList;

public interface EntityImportService {
    String NAME = "technika_EntityImportService";

    String entityImportFromFile(FileDescriptor fileDescriptor, String entityName) throws IOException;
    String entityErrorMessage();
    String entityVendorFillFromArray(ArrayList<String[]> multiRow);
    String entityDeviceTypeFillFromArray(ArrayList<String[]> multiRow);
    String entityOfficeFillFromArray(ArrayList<String[]> multiRow);
    String entityContragentFillFromArray(ArrayList<String[]> multiRow);

    String entityPostFillFromArray(ArrayList<String[]> multiRow);

    String entityPayerFillFromArray(ArrayList<String[]> multiRow);

    String entityPersonFillFromArray(ArrayList<String[]> multiRow);
}