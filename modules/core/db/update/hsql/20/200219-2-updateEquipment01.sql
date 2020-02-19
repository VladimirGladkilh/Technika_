-- update TECHNIKA_EQUIPMENT set TECHNIKA_ID = <default_value> where TECHNIKA_ID is null ;
alter table TECHNIKA_EQUIPMENT alter column TECHNIKA_ID set not null ;
