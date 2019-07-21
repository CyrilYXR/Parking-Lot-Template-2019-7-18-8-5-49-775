CREATE TABLE PARKING_ORDER (
  id IDENTITY PRIMARY KEY,
  parking_lot_name VARCHAR,
  car_number VARCHAR,
  create_time DATE,
  end_time DATE,
  status smallint,
  FOREIGN KEY(parking_lot_name) REFERENCES parking_lot(NAME)
);