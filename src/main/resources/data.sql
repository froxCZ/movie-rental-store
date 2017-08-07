INSERT INTO rental.movie_definition (title, type) VALUES ('Spiderman', 'OLD'); --1
INSERT INTO rental.movie_definition (title, type) VALUES ('Spiderman 2', 'REGULAR'); --2
INSERT INTO rental.movie_definition (title, type) VALUES ('Spiderman 3', 'NEW'); --3
INSERT INTO rental.movie_definition (title, type) VALUES ('Harry Potter 2', 'OLD'); --4
INSERT INTO rental.movie_definition (title, type) VALUES ('Harry Potter 5', 'REGULAR'); --5
INSERT INTO rental.movie_definition (title, type) VALUES ('Harry Potter 7', 'NEW'); --6

INSERT INTO rental.movie_tape (movie_definition_id, rental_id,version) VALUES (1, null,0); --1
INSERT INTO rental.movie_tape (movie_definition_id, rental_id,version) VALUES (1, null,0);
INSERT INTO rental.movie_tape (movie_definition_id, rental_id,version) VALUES (1, null,0);
INSERT INTO rental.movie_tape (movie_definition_id, rental_id,version) VALUES (2, null,0);
INSERT INTO rental.movie_tape (movie_definition_id, rental_id,version) VALUES (2, null,0); --5
INSERT INTO rental.movie_tape (movie_definition_id, rental_id,version) VALUES (3, null,0);
INSERT INTO rental.movie_tape (movie_definition_id, rental_id,version) VALUES (4, null,0);
INSERT INTO rental.movie_tape (movie_definition_id, rental_id,version) VALUES (4, null,0);
INSERT INTO rental.movie_tape (movie_definition_id, rental_id,version) VALUES (5, null,0);
INSERT INTO rental.movie_tape (movie_definition_id, rental_id,version) VALUES (5, null,0); --10
INSERT INTO rental.movie_tape (movie_definition_id, rental_id,version) VALUES (5, null,0);
INSERT INTO rental.movie_tape (movie_definition_id, rental_id,version) VALUES (6, null,0);
INSERT INTO rental.movie_tape (movie_definition_id, rental_id,version) VALUES (6, null,0); --13

INSERT INTO rental.customer (bonus_points, name,version) VALUES (0, 'Vojta',0);
INSERT INTO rental.customer (bonus_points, name,version) VALUES (0, 'Peter',0);
INSERT INTO rental.customer (bonus_points, name,version) VALUES (0, 'Tom',0);