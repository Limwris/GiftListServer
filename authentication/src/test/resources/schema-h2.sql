-- Database: giftlistserver
-- ------------------------------------------------------

--
-- Table structure for table room
--

CREATE TABLE room (
  idRoom INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  roomName varchar(45) NOT NULL,
  occasion varchar(45) DEFAULT NULL
);

--
-- Table structure for table user
--

CREATE TABLE user (
  idUser INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  username varchar(45) NOT NULL,
  password varchar(45) NOT NULL,
  phoneNumber varchar(45) NOT NULL,
  gcmId varchar(45),
  creationDate varchar(45) NOT NULL,
  UNIQUE (username),
  UNIQUE (phoneNumber)
);

--
-- Table structure for table user_room
--

CREATE TABLE user_room (
  userId INTEGER NOT NULL,
  roomId INTEGER NOT NULL,
  FOREIGN KEY (roomId) REFERENCES room (idRoom) ON DELETE CASCADE ON UPDATE NO ACTION,
  FOREIGN KEY (userId) REFERENCES user (idUser) ON DELETE CASCADE ON UPDATE NO ACTION
);

--
-- Table structure for table gifts
--

CREATE TABLE gifts (
  idGifts INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name varchar(45) DEFAULT NULL,
  price varchar(45) DEFAULT NULL,
  roomId INTEGER NOT NULL
--  FOREIGN KEY (roomId) REFERENCES room (idRoom) ON DELETE CASCADE ON UPDATE NO ACTION
);



--
-- Table structure for table user_gift
--

CREATE TABLE user_gift (
  userId INTEGER NOT NULL,
  giftId INTEGER NOT NULL,
  allocatedAmount double DEFAULT NULL,
  FOREIGN KEY (giftId) REFERENCES gifts (idGifts) ON DELETE CASCADE ON UPDATE NO ACTION,
  FOREIGN KEY (userId) REFERENCES user (idUser) ON DELETE CASCADE ON UPDATE NO ACTION
);