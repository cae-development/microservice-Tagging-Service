--
-- Database Schema:  cae-schema 
-- Automatically generated sql script for the service Tagging Service, created by the CAE.
-- --------------------------------------------------------

--
-- Table structure for table comments.
--
CREATE TABLE `cae-schema`.`comments` (
  comment text ,
  imgId int ,
  id int NOT NULL AUTO_INCREMENT ,
CONSTRAINT id_PK PRIMARY KEY (id)
);
--
-- Table structure for table tags.
--
CREATE TABLE `cae-schema`.`tags` (
  name varchar(255) ,
  imgId int ,
  id int NOT NULL AUTO_INCREMENT ,
CONSTRAINT id_PK PRIMARY KEY (id)
);

