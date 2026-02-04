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