CREATE TABLE Person(
                        person_id       SERIAL PRIMARY KEY,
                        name            TEXT NOT NULL,
                        age             INT,
                        driving_license BOOLEAN DEFAULT false
                    );

CREATE TABLE Car (
                     car_id SERIAL PRIMARY KEY,
                     brand TEXT NOT NULL,
                     model TEXT NOT NULL,
                     price DECIMAL NOT NULL
                );

CREATE TABLE PersonCar (
                           person_car_id SERIAL PRIMARY KEY,
                           person_id INT REFERENCES Person(person_id),
                           car_id INT REFERENCES Car(car_id)
                        )