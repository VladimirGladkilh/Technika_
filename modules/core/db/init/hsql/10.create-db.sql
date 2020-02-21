-- begin TECHNIKA_DEVICE_TYPE
create table TECHNIKA_DEVICE_TYPE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    --
    primary key (ID)
)^
-- end TECHNIKA_DEVICE_TYPE
-- begin TECHNIKA_DEVICE
create table TECHNIKA_DEVICE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    MODEL varchar(255) not null,
    PRIM varchar(512),
    TYPE_ID varchar(36) not null,
    VENDOR_ID varchar(36) not null,
    --
    primary key (ID)
)^
-- end TECHNIKA_DEVICE
-- begin TECHNIKA_VENDOR
create table TECHNIKA_VENDOR (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    --
    primary key (ID)
)^
-- end TECHNIKA_VENDOR
-- begin TECHNIKA_COMPONENT
create table TECHNIKA_COMPONENT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DEVICE_ID varchar(36) not null,
    SERIAL_NUMBER varchar(255) not null,
    PRICE double precision,
    LOW_PRICE double precision,
    BUSY boolean,
    COST varchar(255),
    --
    primary key (ID)
)^
-- end TECHNIKA_COMPONENT
-- begin TECHNIKA_OFFICE
create table TECHNIKA_OFFICE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    CITY varchar(255),
    STREET varchar(255),
    HOUSE varchar(20),
    OFFICE varchar(20),
    --
    primary key (ID)
)^
-- end TECHNIKA_OFFICE
-- begin TECHNIKA_POST
create table TECHNIKA_POST (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    --
    primary key (ID)
)^
-- end TECHNIKA_POST
-- begin TECHNIKA_CONTRAGENT
create table TECHNIKA_CONTRAGENT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    CONTACT varchar(255),
    PHONE varchar(255),
    PRIM varchar(255),
    --
    primary key (ID)
)^
-- end TECHNIKA_CONTRAGENT
-- begin TECHNIKA_COST
create table TECHNIKA_COST (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NUM varchar(255) not null,
    DAT date not null,
    CONTRAGENT_ID varchar(36) not null,
    MAIN_DOC_ID varchar(36),
    SUBJECT varchar(512) not null,
    COST_PRICE double precision,
    UPD_ID varchar(36),
    PAYER_ID varchar(36),
    --
    primary key (ID)
)^
-- end TECHNIKA_COST
-- begin TECHNIKA_PAYER
create table TECHNIKA_PAYER (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    PRIM varchar(255),
    --
    primary key (ID)
)^
-- end TECHNIKA_PAYER
-- begin TECHNIKA_EQUIPMENT
create table TECHNIKA_EQUIPMENT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TECHNIKA_ID varchar(36) not null,
    COMPONENT_ID varchar(36) not null,
    PRIM varchar(255),
    --
    primary key (ID)
)^
-- end TECHNIKA_EQUIPMENT
-- begin TECHNIKA_PERSON
create table TECHNIKA_PERSON (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    FAMILIA varchar(255) not null,
    IMYA varchar(255) not null,
    OTCHESTVO varchar(255),
    EMAIL varchar(50),
    PHONE varchar(50),
    OFFICE_ID varchar(36),
    POST_ID varchar(36),
    --
    primary key (ID)
)^
-- end TECHNIKA_PERSON
-- begin TECHNIKA_TECHIKA
create table TECHNIKA_TECHIKA (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    PRIM varchar(255),
    --
    primary key (ID)
)^
-- end TECHNIKA_TECHIKA
-- begin TECHNIKA_MOVEMENT
create table TECHNIKA_MOVEMENT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PERSON_ID varchar(36) not null,
    TECHNIKA_ID varchar(36) not null,
    START_DATE date not null,
    END_DATE date,
    PRIM varchar(255),
    --
    primary key (ID)
)^
-- end TECHNIKA_MOVEMENT
