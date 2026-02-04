\c postgres;
DROP DATABASE IF EXISTS db_reservation_hotel;
CREATE DATABASE db_reservation_hotel;

-- Se connecter à la base
\c db_reservation_hotel;

-- ======================================
-- CReATION DES TABLES
-- ======================================

-- Table t_hotel
CREATE TABLE t_hotel(
    id_hotel SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL
);

-- Table t_reservation
CREATE TABLE t_reservation(
    id_reservation SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    nb_passager INTEGER NOT NULL,
    dateheure_arrivee TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    assigner BOOLEAN NOT NULL DEFAULT FALSE,
    id_hotel INTEGER NOT NULL REFERENCES t_hotel(id_hotel)
);

-- Table t_vehicule
CREATE TABLE t_vehicule(
    id_vehicule SERIAL PRIMARY KEY,
    numero VARCHAR(50) NOT NULL,
    nb_place INTEGER NOT NULL,
    carburant INTEGER
);

-- Table t_distance
CREATE TABLE t_distance(
    id_distance SERIAL PRIMARY KEY,
    distance_from INTEGER NOT NULL REFERENCES t_hotel(id_hotel),
    distance_to INTEGER NOT NULL REFERENCES t_hotel(id_hotel),
    distance NUMERIC(5,2) NOT NULL
);

-- Table t_parametre
CREATE TABLE t_parametre(
    id_parametre SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    valeur NUMERIC(10,2) NOT NULL
);

-- Table t_assignation
CREATE TABLE t_assignation(
    id_assignation SERIAL PRIMARY KEY,
    heure_depart TIMESTAMP,
    heure_retour TIMESTAMP,
    id_vehicule INTEGER NOT NULL REFERENCES t_vehicule(id_vehicule)
);

-- Table t_assignation_reservation (table de liaison)
CREATE TABLE t_assignation_reservation(
    id_assignation INTEGER NOT NULL REFERENCES t_assignation(id_assignation),
    id_reservation INTEGER NOT NULL REFERENCES t_reservation(id_reservation),
    PRIMARY KEY(id_assignation, id_reservation)
);

-- Table t_assignation_hotel (table de liaison pour l'ordre des hôtels)
CREATE TABLE t_assignation_hotel(
    ordre SERIAL PRIMARY KEY,
    id_assignation INTEGER NOT NULL REFERENCES t_assignation(id_assignation),
    id_hotel INTEGER NOT NULL REFERENCES t_hotel(id_hotel),
    UNIQUE(id_assignation, id_hotel)
);

-- ======================================
-- INSERTION DES DONNeES DE ReFeRENCE
-- ======================================

-- Paramètres
INSERT INTO t_parametre(nom, valeur) VALUES 
('vitesse', 20),
('temps_attente', 30);

-- Hôtels
INSERT INTO t_hotel(nom) VALUES 
('TNR (Aeroport)'),  -- id = 1
('Carlton'),         -- id = 2
('Radisson Blu'),    -- id = 3
('Colbert');         -- id = 4

-- Vehicules
INSERT INTO t_vehicule(numero, nb_place, carburant) VALUES 
('V-001', 4, 1),   -- id = 1, essence
('V-002', 4, 2),   -- id = 2, gasoil
('V-003', 6, 1),   -- id = 3, essence
('V-004', 7, 2);   -- id = 4, gasoil

-- Distances (matrice de distances entre hôtels)
-- From TNR (Aeroport) - id=1
INSERT INTO t_distance(distance_from, distance_to, distance) VALUES 
(1, 1, 0),      -- TNR -> TNR
(1, 2, 18.5),   -- TNR -> Carlton
(1, 3, 14),     -- TNR -> Radisson Blu
(1, 4, 19);     -- TNR -> Colbert

-- From Carlton - id=2
INSERT INTO t_distance(distance_from, distance_to, distance) VALUES 
(2, 1, 18.5),   -- Carlton -> TNR
(2, 2, 0),      -- Carlton -> Carlton
(2, 3, 7),      -- Carlton -> Radisson Blu
(2, 4, 4);      -- Carlton -> Colbert

-- From Radisson Blu - id=3
INSERT INTO t_distance(distance_from, distance_to, distance) VALUES 
(3, 1, 14),     -- Radisson Blu -> TNR
(3, 2, 7),      -- Radisson Blu -> Carlton
(3, 3, 0),      -- Radisson Blu -> Radisson Blu
(3, 4, 6);      -- Radisson Blu -> Colbert

-- From Colbert - id=4
INSERT INTO t_distance(distance_from, distance_to, distance) VALUES 
(4, 1, 19),     -- Colbert -> TNR
(4, 2, 4),      -- Colbert -> Carlton
(4, 3, 6),      -- Colbert -> Radisson Blu
(4, 4, 0);      -- Colbert -> Colbert

-- ======================================
-- TEST 1 : Une seule reservation par TA
-- ======================================
-- Date : 2026-01-15

INSERT INTO t_reservation(nom, prenom, nb_passager, dateheure_arrivee, assigner, id_hotel) VALUES 
('Rakoto', 'Tiana', 4, '2026-01-15 08:30:00', false, 2),    -- id=1 -> Carlton
('Randria', 'Miora', 4, '2026-01-15 10:30:00', false, 3),   -- id=2 -> Radisson Blu
('Jean', 'Jean', 5, '2026-01-15 11:10:00', false, 3),       -- id=3 -> Radisson Blu
('Paul', 'Paul', 5, '2026-01-15 11:45:00', false, 4);       -- id=4 -> Colbert

-- ======================================
-- TEST 2 : Plusieurs reservations dans TA
-- ======================================
-- Date : 2026-01-16

INSERT INTO t_reservation(nom, prenom, nb_passager, dateheure_arrivee, assigner, id_hotel) VALUES 
('Rakoto', 'Tiana', 2, '2026-01-16 08:30:00', false, 2),    -- id=5 -> Carlton
('Randria', 'Miora', 4, '2026-01-16 08:40:00', false, 3),   -- id=6 -> Radisson Blu
('Jean', 'Jean', 3, '2026-01-16 08:45:00', false, 4),       -- id=7 -> Colbert
('Paul', 'Paul', 2, '2026-01-16 08:45:00', false, 2),       -- id=8 -> Carlton
('Paulin', 'Paulin', 2, '2026-01-16 08:50:00', false, 4),   -- id=9 -> Colbert
('Paulos', 'Paulos', 2, '2026-01-16 09:10:00', false, 3),   -- id=10 -> Radisson Blu
('Pauline', 'Pauline', 2, '2026-01-16 09:20:00', false, 4); -- id=11 -> Colbert

-- ======================================
-- VeRIFICATIONS
-- ======================================

-- Verifier les hôtels
SELECT * FROM t_hotel ORDER BY id_hotel;

-- Verifier les vehicules
SELECT * FROM t_vehicule ORDER BY id_vehicule;

-- Verifier les paramètres
SELECT * FROM t_parametre ORDER BY id_parametre;

-- Verifier les distances
SELECT 
    d.id_distance,
    h1.nom as from_hotel,
    h2.nom as to_hotel,
    d.distance
FROM t_distance d
JOIN t_hotel h1 ON d.distance_from = h1.id_hotel
JOIN t_hotel h2 ON d.distance_to = h2.id_hotel
ORDER BY d.distance_from, d.distance_to;

-- Verifier les reservations Test 1
SELECT * FROM t_reservation WHERE dateheure_arrivee::date = '2026-01-15' ORDER BY dateheure_arrivee;

-- Verifier les reservations Test 2
SELECT * FROM t_reservation WHERE dateheure_arrivee::date = '2026-01-16' ORDER BY dateheure_arrivee;