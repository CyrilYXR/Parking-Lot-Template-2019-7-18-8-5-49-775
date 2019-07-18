CREATE TABLE PARKING_LOT (
  id INT PRIMARY KEY,
  name VARCHAR UNIQUE,
  capacity INT CHECK(capacity>=0),
  position VARCHAR
);