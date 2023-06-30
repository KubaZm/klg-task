-- dictionaries
INSERT INTO objects_for_rent (name, price, area, description) VALUES ('Apartment', 199.99, 63.51, 'An apartment with a view into the mountains');
INSERT INTO objects_for_rent (name, price, area, description) VALUES ('Hotel room', 150, 23, null);
INSERT INTO objects_for_rent (name, price, area, description) VALUES ('Villa', 450, 180, 'Big villa near the sea');
INSERT INTO objects_for_rent (name, price, area, description) VALUES ('Motel room', 70, 15, null);
INSERT INTO objects_for_rent (name, price, area, description) VALUES ('Farm stay', 129.99, 93.50, 'A farm stay on countryside');

INSERT INTO people (name) VALUES ('James Bond');
INSERT INTO people (name) VALUES ('Harry Potter');
INSERT INTO people (name) VALUES ('Geralt of Rivia');
INSERT INTO people (name) VALUES ('John Wick');
INSERT INTO people (name) VALUES ('Anakin Skywalker');
INSERT INTO people (name) VALUES ('Thomas Shelby');

-- sample reservations data
INSERT INTO reservations (object_id, landlord_id, renter_id, start_date, end_date, cost) VALUES (2, 4, 1, '2023-03-04', '2023-03-08', 600);
INSERT INTO reservations (object_id, landlord_id, renter_id, start_date, end_date, cost) VALUES (5, 1, 3, '2023-05-13', '2023-05-23', 1299.90);
INSERT INTO reservations (object_id, landlord_id, renter_id, start_date, end_date, cost) VALUES (4, 2, 6, '2022-11-10', '2022-11-15', 350);