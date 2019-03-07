--TO CREATE A NEW USER

CREATE USER myusfdb IDENTIFIED BY pas$w0rd;

GRANT CONNECT, RESOURCE TO myusfdb;

GRANT DBA TO myusfdb WITH ADMIN OPTION;




DROP TABLE user_account;

TRUNCATE TABLE user_account;



SELECT * FROM user_account;

	

CREATE TABLE user_account (

    name       VARCHAR2(40),

    password   VARCHAR2(40) default 'asd',

    balance_checkings    number (13,2)default 0,

    balance_savings    number (13,2)default 0,

    admin      INT default 0, 

    employee   INT default 0,

    approved   INT default 0,

    CONSTRAINT pk_name PRIMARY KEY ( name )

);




INSERT INTO user_account VALUES ('admin','admin',100 , 100, 1, 0, 1);

commit;



SELECT * FROM user_account;

      

commit;




--Create a Sequence to generate the value for the user id's

CREATE SEQUENCE user_id_sequence

    START WITH 1 

    INCREMENT BY 1

    MAXVALUE 900

    MINVALUE -1

    NOCACHE;



create or replace procedure user_account(name VARCHAR2, password VARCHAR2, balance number, admin INTEGER, approved INTEGER)

as

tempVal number(10);



begin



   tempVal := user_id_seq.nextval;   

   insert into user_account values(name, password, balance, admin, approval);   



   commit;



end;



/




COMMIT;