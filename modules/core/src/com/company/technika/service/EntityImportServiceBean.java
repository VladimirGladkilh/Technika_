package com.company.technika.service;

import com.company.technika.dao.*;
import com.company.technika.entity.*;
import com.company.technika.factory.*;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileLoader;
import com.haulmont.cuba.core.global.FileStorageException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Service(EntityImportService.NAME)
public class EntityImportServiceBean implements EntityImportService {
    private final VendorFactory vendorFactory;
    private final VendorRepository vendorRepository;
    private final OfficeFactory officeFactory;
    private final OfficeRepository officeRepository;
    private final DeviceTypeRepository deviceTypeRepository;
    private final DeviceTypeFactory deviceTypeFactory;
    private final ContragentFactory contragentFactory;
    private final ContragentRepository contragentRepository;
    private final PostFactory postFactory;
    private final PostRepository postRepository;
    private final PayerFactory payerFactory;
    private final PayerRepository payerRepository;
    private final PersonFactory personFactory;
    private final PersonRepository personRepository;
    private final DeviceFactory deviceFactory;
    private final DeviceRepository deviceRepository;
    private final CostFactory costFactory;
    private final CostRepository costRepository;
    private final ComponentFactory componentFactory;
    private final ComponentRepository componentRepository;


    private String errorValues = "";
    private int recordCount = 0;
    private int loadedRecords = 0;
    @Inject
    private FileLoader fileLoader;

    @Autowired
    public EntityImportServiceBean(VendorFactory vendorFactory, VendorRepository vendorRepository,
                                   OfficeFactory officeFactory, OfficeRepository officeRepository,
                                   DeviceTypeFactory deviceTypeFactory, DeviceTypeRepository deviceTypeRepository,
                                   ContragentFactory contragentFactory, ContragentRepository contragentRepository,
                                   PostFactory postFactory, PostRepository postRepository,
                                   PayerFactory payerFactory, PayerRepository payerRepository,
                                   PersonFactory personFactory, PersonRepository personRepository,
                                   DeviceFactory deviceFactory, DeviceRepository deviceRepository,
                                   CostFactory costFactory, CostRepository costRepository,
                                   ComponentFactory componentFactory, ComponentRepository componentRepository){
        this.contragentFactory = contragentFactory;
        this.contragentRepository = contragentRepository;
        this.vendorFactory = vendorFactory;
        this.vendorRepository = vendorRepository;
        this.deviceTypeFactory = deviceTypeFactory;
        this.deviceTypeRepository = deviceTypeRepository;
        this.officeFactory = officeFactory;
        this.officeRepository = officeRepository;
        this.postFactory = postFactory;
        this.postRepository = postRepository;
        this.payerFactory = payerFactory;
        this.payerRepository = payerRepository;
        this.personFactory = personFactory;
        this.personRepository = personRepository;
        this.deviceFactory = deviceFactory;
        this.deviceRepository = deviceRepository;
        this.costFactory = costFactory;
        this.costRepository = costRepository;
        this.componentFactory = componentFactory;
        this.componentRepository = componentRepository;
    }

    @Override
    public String entityImportFromFile(FileDescriptor fileDescriptor, String entityName) throws IOException {
        errorValues = "";
        String res;
        ArrayList<String[]> multiRow = new ArrayList<>();
        try {
            FileInputStream fs = (FileInputStream) fileLoader.openStream(fileDescriptor);
            Sheet sheet;
            if (fileDescriptor.getExtension().equals("xls") || fileDescriptor.getExtension().equals("xlsx") ) {
                if (fileDescriptor.getExtension().equals("xls")) {
                    Workbook workbook = new HSSFWorkbook(fs);
                    sheet = workbook.getSheetAt(0);
                } else {
                    Workbook workbook = new XSSFWorkbook(fs);
                    sheet = workbook.getSheetAt(0);
                }
                //вычислим кол-во столбцов
                int j = 0;
                Row firstRow = sheet.getRow(0);
                while (firstRow.getCell(j) != null) {
                    j++;
                }
                // Распарсить строки
                for (int i = 1; true; i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) {
                        break;
                    }
                    //в первом столбце всегда будет код по нему и мониторим окончание строк
                    if (row.getCell(0) == null) {
                        break;
                    }
                    String[] oneRow = new String[j] ;
                    for (int k = 0; k < j; k++) {
                        if (row.getCell(k) != null) {
                            CellType cellType= row.getCell(k).getCellType();
                            String value ="";
                            switch (cellType){

                                case STRING:
                                    value = row.getCell(k).getStringCellValue();
                                    break;
                                case NUMERIC:
                                    value = String.valueOf(row.getCell(k).getNumericCellValue());
                                    if (HSSFDateUtil.isCellDateFormatted(row.getCell(k))){
                                        Date date = row.getCell(k).getDateCellValue();
                                        String dateFmt = row.getCell(k).getCellStyle().getDataFormatString();
                                        try {
                                            value = new CellDateFormatter("dd.MM.yyyy").format(date);
                                        }catch (Exception e){
                                            e.printStackTrace();
                                           // value = row.getCell(k).getStringCellValue();
                                        }

                                    }
                                    break;
                            }



                            oneRow[k] = value;
                        }
                        else{
                            oneRow[k] = "";
                        }
                    }
                    if (oneRow.length > 0) {
                        multiRow.add(oneRow);
                    }
                }
                res = "";
                switch (entityName){
                    case "Vendor":
                        res = entityVendorFillFromArray(multiRow);
                        break;
                    case "Office":
                        res = entityOfficeFillFromArray(multiRow);
                        break;
                    case "DeviceType":
                        res = entityDeviceTypeFillFromArray(multiRow);
                        break;
                    case "Contragent":
                        res = entityContragentFillFromArray(multiRow);
                        break;
                    case "Payer":
                        res = entityPayerFillFromArray(multiRow);
                        break;
                    case "Post":
                        res = entityPostFillFromArray(multiRow);
                        break;
                    case "Person":
                        res = entityPersonFillFromArray(multiRow);
                        break;
                    case "Device":
                        res = entityDeviceFillFromArray(multiRow);
                        break;
                    case "Cost":
                        res = entityCostFillFromArray(multiRow);
                        break;
                    case "Component":
                        res = entityComponentFillFromArray(multiRow);
                        break;
                }
            }
            else {
                return "Неверный формат файла";
            }
        } catch (FileStorageException e) {
            e.printStackTrace();
            errorValues = e.getLocalizedMessage();
            return "error";
        }
        return res;
    }

    @Override
    public String entityErrorMessage(){
        return  errorValues;
    }

    @Override
    public String entityVendorFillFromArray(ArrayList<String[]> multiRow){
        recordCount = multiRow.size();
        loadedRecords = 0;
        int i = 0;
        for (String[] oneRecord : multiRow){
            try {
                Vendor vendor = vendorFactory.create(oneRecord[1]);
                vendorRepository.save(vendor);
                loadedRecords = loadedRecords + 1;
            }
            catch (Exception ex){
                ex.printStackTrace();
                errorValues = String.format("%s%n%s", errorValues , ex.getLocalizedMessage());
            }
        }
        return "Загружено "+ loadedRecords + " из "+ recordCount;
    }

    @Override
    public String entityDeviceTypeFillFromArray(ArrayList<String[]> multiRow){
        recordCount = multiRow.size();
        loadedRecords = 0;
        for (String[] oneRecord : multiRow){
            try {
                DeviceType deviceType = deviceTypeFactory.create(oneRecord[1]);
                deviceTypeRepository.save(deviceType);
                loadedRecords = loadedRecords + 1;
            }
            catch (Exception ex){
                ex.printStackTrace();
                errorValues = String.format("%s%n%s", errorValues , ex.getLocalizedMessage());
            }
        }
        return "Загружено "+ loadedRecords + " из "+ recordCount;
    }

    @Override
    public String entityOfficeFillFromArray(ArrayList<String[]> multiRow){
        recordCount = multiRow.size();
        loadedRecords = 0;
        for (String[] oneRecord : multiRow){
            try {
                Office office = officeFactory.create("NewOffice", oneRecord[1], oneRecord[2], oneRecord[3], oneRecord[4]);
                officeRepository.save(office);
                loadedRecords = loadedRecords + 1;
            }
            catch (Exception ex){
                ex.printStackTrace();
                errorValues = String.format("%s%n%s", errorValues , ex.getLocalizedMessage());
            }
        }
        return "Загружено "+ loadedRecords + " из "+ recordCount;
    }

    @Override
    public String entityContragentFillFromArray(ArrayList<String[]> multiRow){
        recordCount = multiRow.size();
        loadedRecords = 0;
        for (String[] oneRecord : multiRow){
            try {
                Contragent contragent = contragentFactory.create(oneRecord[1], oneRecord[2], oneRecord[3], oneRecord[4]);
                contragentRepository.save(contragent);
                loadedRecords = loadedRecords + 1;
            }
            catch (Exception ex){
                ex.printStackTrace();
                errorValues = String.format("%s%n%s", errorValues , ex.getLocalizedMessage());
            }
        }
        return "Загружено "+ loadedRecords + " из "+ recordCount;
    }
    @Override
    public String entityPostFillFromArray(ArrayList<String[]> multiRow){
        recordCount = multiRow.size();
        loadedRecords = 0;
        for (String[] oneRecord : multiRow){
            try {
                Post post = postFactory.create(oneRecord[1]);
                postRepository.save(post);
                loadedRecords = loadedRecords + 1;
            }
            catch (Exception ex){
                ex.printStackTrace();
                errorValues = String.format("%s%n%s", errorValues , ex.getLocalizedMessage());
            }
        }
        return "Загружено "+ loadedRecords + " из "+ recordCount;
    }

    @Override
    public String entityPayerFillFromArray(ArrayList<String[]> multiRow){
        recordCount = multiRow.size();
        loadedRecords = 0;
        for (String[] oneRecord : multiRow){
            try {
                Payer payer = payerFactory.create(oneRecord[1]);
                payerRepository.save(payer);
                loadedRecords = loadedRecords + 1;
            }
            catch (Exception ex){
                ex.printStackTrace();
                errorValues = String.format("%s%n%s", errorValues , ex.getLocalizedMessage());
            }
        }
        return "Загружено "+ loadedRecords + " из "+ recordCount;
    }

    @Override
    public String entityPersonFillFromArray(ArrayList<String[]> multiRow){
        recordCount = multiRow.size();
        loadedRecords = 0;
        for (String[] oneRecord : multiRow){
            try {
                //String name = oneRecord[1];
                Person person = personFactory.create(oneRecord[1], oneRecord[2], oneRecord[3], oneRecord[4], oneRecord[5], oneRecord[6], oneRecord[7]);
                personRepository.save(person);
                loadedRecords = loadedRecords + 1;
            }
            catch (Exception ex){
                ex.printStackTrace();
                errorValues = String.format("%s%n%s", errorValues , ex.getLocalizedMessage());
            }
        }
        return "Загружено "+ loadedRecords + " из "+ recordCount;
    }

    @Override
    public String entityDeviceFillFromArray(ArrayList<String[]> multiRow){
        recordCount = multiRow.size();
        loadedRecords = 0;
        for (String[] oneRecord : multiRow){
            try {
                //String name = oneRecord[1];
                Device device = deviceFactory.create(oneRecord[1], oneRecord[2], oneRecord[3], oneRecord[4]);
                deviceRepository.save(device);
                loadedRecords = loadedRecords + 1;
            }
            catch (Exception ex){
                ex.printStackTrace();
                errorValues = String.format("%s%n%s", errorValues , ex.getLocalizedMessage());
            }
        }
        return "Загружено "+ loadedRecords + " из "+ recordCount;
    }
    @Override
    public String entityCostFillFromArray(ArrayList<String[]> multiRow){
        recordCount = multiRow.size();
        loadedRecords = 0;
        for (String[] oneRecord : multiRow){
            try {
                //String name = oneRecord[1];
                Cost cost = costFactory.create(oneRecord[1], oneRecord[2], oneRecord[3], oneRecord[4], oneRecord[5], oneRecord[6]);
                costRepository.save(cost);
                loadedRecords = loadedRecords + 1;
            }
            catch (Exception ex){
                ex.printStackTrace();
                String s=oneRecord[1] + oneRecord[2] + oneRecord[3] + oneRecord[4] + oneRecord[5] + oneRecord[6];
                errorValues = String.format("%s%n%s", errorValues , s+'\n'+ex.getLocalizedMessage());
            }
        }
        return "Загружено "+ loadedRecords + " из "+ recordCount;
    }

    @Override
    public String entityComponentFillFromArray(ArrayList<String[]> multiRow){
        recordCount = multiRow.size();
        loadedRecords = 0;

        for (String[] oneRecord : multiRow){
            try {
                //String name = oneRecord[1];
                Component component = componentFactory.create(oneRecord[1], oneRecord[2], oneRecord[3], oneRecord[4], oneRecord[5], oneRecord[6]);
                componentRepository.save(component);
                loadedRecords = loadedRecords + 1;
                //s =oneRecord[1] +"; "+ oneRecord[2] + "; "+oneRecord[3] +"; "+ oneRecord[4] +"; "+ oneRecord[5] +"; "+ oneRecord[6];
            }
            catch (Exception ex){
                ex.printStackTrace();
                //s =oneRecord[1] + oneRecord[2] + oneRecord[3] + oneRecord[4] + oneRecord[5] + oneRecord[6];
                errorValues = String.format("%s%n%s", errorValues , ex.getLocalizedMessage());
            }
        }
        return "Загружено "+ loadedRecords + " из "+ recordCount ;
    }
}