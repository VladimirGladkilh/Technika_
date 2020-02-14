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
