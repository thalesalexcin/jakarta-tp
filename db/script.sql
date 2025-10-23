drop schema if exists immobilier;
create schema immobilier ;
use immobilier ;
create table utilisateur (
id int primary key auto_increment ,
nom varchar (30) not null ,
prenom varchar (30) not null ,
email varchar (50) not null unique ,
motDePasse varchar (100) not null
) ;
create table annonce (
id int primary key auto_increment ,
titre varchar (100) not null ,
description text ,
prix decimal (10 ,2) not null ,
ville varchar (50) ,
idUtilisateur int ,
foreign key ( idUtilisateur ) references utilisateur ( id )
) ;
create table favori (
id int primary key auto_increment ,
idUtilisateur int ,
idAnnonce int ,
foreign key ( idUtilisateur ) references utilisateur ( id ) ,
foreign key ( idAnnonce ) references annonce ( id )
) ;


DROP USER IF EXISTS 'db_admin_tp';
CREATE USER 'db_admin_tp' IDENTIFIED BY 'db_admin_tp_mdp';
GRANT ALL PRIVILEGES ON immobilier.* TO 'db_admin_tp'@'%';


insert into utilisateur values (null, 'Albuquerque', 'Thales', 'tata@immo.com', '12345678');