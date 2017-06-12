INSERT INTO users(nickname, email, password, status_id) values ('paolo1967', 'paolo67@gmail.com', crypt('helloworld260467', gen_salt('bf')), 1);
INSERT INTO users(nickname, email, password, status_id) values ('alessia1994', 'alessia94@hotmail.com', crypt('thecatisonthetable', gen_salt('bf')), 1);
INSERT INTO users(nickname, email, password, status_id) values ('andrea1949', 'andrearossi@tiscali.it', crypt('qwerty123', gen_salt('bf')), 1);
INSERT INTO users(nickname, email, password, status_id) values ('bea2005', 'beautifulbea05@yahoo.com', crypt('selfiemania', gen_salt('bf')), 1);
INSERT INTO users(nickname, email, password, status_id) values ('UtenteNonAutenticato', 'nonautenticato@hotmail.com', crypt('12345678', gen_salt('bf')), 2);
INSERT INTO users(nickname, email, password) values ('martino', 'martino@polito.it', crypt('martino', gen_salt('bf')));
INSERT INTO users(nickname, email, password) values ('alessio', 'alessio@polito.it', crypt('alessio', gen_salt('bf')));
INSERT INTO users(nickname, email, password) values ('riccardo', 'riccardo@polito.it', crypt('riccardo', gen_salt('bf')));
INSERT INTO users(nickname, email, password) values ('sabrina', 'sabrina@polito.it', crypt('sabrina', gen_salt('bf')));
INSERT INTO user_profiles (id, sex, date_of_birth, education_level_id, employment_id, private_car_ownership, car_registration_year, fuel_id, car_sharing_usage, bike_sharing_usage, private_bike_usage, public_transport_usage)
values (1, 'M', '1967-04-26', 5, 3, 'Y', 2005, 2, 'N', 'N', 'Y', 'N');
INSERT INTO user_profiles (id, sex, date_of_birth, education_level_id, employment_id, private_car_ownership, car_registration_year, fuel_id, car_sharing_usage, car_sharing_service_id, bike_sharing_usage, private_bike_usage, public_transport_usage, habitual_travel_document_id)
values (2, 'F', '1994-07-12', 4, 1, 'Y', 2013, 1, 'Y', 2, 'Y', 'Y', 'Y', 3);
INSERT INTO user_profiles (id, sex, date_of_birth, education_level_id, employment_id, private_car_ownership, car_sharing_usage, car_sharing_service_id, bike_sharing_usage, private_bike_usage, public_transport_usage, habitual_travel_document_id)
values (3, 'M', '1949-01-05', 3, 1, 'N', 'Y', 2, 'Y', 'N', 'Y', 1);
INSERT INTO user_profiles (id, sex, date_of_birth, education_level_id, employment_id, private_car_ownership, car_sharing_usage, bike_usage, bike_sharing_usage, private_bike_usage, public_transport_usage, habitual_travel_document_id)
values (4, 'M', '2005-12-29', 2, 1, 'N', 'N', 'Y', 'Y', 'Y', 'Y', 1);
