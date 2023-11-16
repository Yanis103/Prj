-- Insertion des formations
INSERT INTO Formation (nomFormation, promotion) VALUES 
('Informatique', '2023'),
('Marketing', '2023'),
('Ingénierie civile', '2023');

-- Insertion des étudiants
INSERT INTO Etudiant (nom, prenom, idFormation) VALUES 
('Durand', 'Lucie', (SELECT idFormation FROM Formation WHERE nomFormation = 'Informatique' AND promotion = '2023')),
('Dubois', 'Jean', (SELECT idFormation FROM Formation WHERE nomFormation = 'Informatique' AND promotion = '2023')),
('Martin', 'Sophie', (SELECT idFormation FROM Formation WHERE nomFormation = 'Marketing' AND promotion = '2023')),
('Moreau', 'Pierre', (SELECT idFormation FROM Formation WHERE nomFormation = 'Marketing' AND promotion = '2023')),
('Lefevre', 'Camille', (SELECT idFormation FROM Formation WHERE nomFormation = 'Ingénierie civile' AND promotion = '2023')),
('Leroy', 'Antoine', (SELECT idFormation FROM Formation WHERE nomFormation = 'Ingénierie civile' AND promotion = '2023'));

-- Insertion des projets
INSERT INTO Projet (nomMatiere, sujet, dateRemisePrevue) VALUES 
('Mathématiques', 'Analyse numérique', '2023-12-31'),
('Physique', 'Mécanique quantique', '2023-12-31');

-- Insertion des binômes pour les projets
INSERT INTO Binome (idProjet, noteRapport, binomeReference, dateRemiseEffective) VALUES
((SELECT idProjet FROM Projet WHERE sujet = 'Analyse numérique'), 15.5, 'Binome1', '2023-12-31'),
((SELECT idProjet FROM Projet WHERE sujet = 'Mécanique quantique'), 14.5, 'Binome2', '2023-12-31');

-- Insertion dans la table de liaison pour les binômes et les étudiants
INSERT INTO NoteSoutenance (idBinome, idEtudiant, noteSoutenance) VALUES
((SELECT idBinome FROM Binome WHERE binomeReference = 'Binome1'), (SELECT idEtudiant FROM Etudiant WHERE nom = 'Durand' AND prenom = 'Lucie'), 16.0),
((SELECT idBinome FROM Binome WHERE binomeReference = 'Binome1'), (SELECT idEtudiant FROM Etudiant WHERE nom = 'Dubois' AND prenom = 'Jean'), 15.0),
((SELECT idBinome FROM Binome WHERE binomeReference = 'Binome2'), (SELECT idEtudiant FROM Etudiant WHERE nom = 'Martin' AND prenom = 'Sophie'), 14.0),
((SELECT idBinome FROM Binome WHERE binomeReference = 'Binome2'), (SELECT idEtudiant FROM Etudiant WHERE nom = 'Moreau' AND prenom = 'Pierre'), 13.0);

