-- Suppression et creation de la base de donnees
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

CREATE TABLE t_carburant(
    id_carburant SERIAL PRIMARY KEY,
    carburant VARCHAR(20)
);


-- Table t_vehicule
CREATE TABLE t_vehicule(
    id_vehicule SERIAL PRIMARY KEY,
    numero VARCHAR(50) NOT NULL,
    nb_place INTEGER NOT NULL,
    carburant INTEGER NOT NULL REFERENCES t_carburant(id_carburant)
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
INSERT INTO t_carburant(carburant) VALUES 
('essence'),
('gasoil');
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

