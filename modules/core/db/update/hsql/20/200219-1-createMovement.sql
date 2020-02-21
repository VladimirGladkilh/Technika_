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
);