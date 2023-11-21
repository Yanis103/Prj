-- Table Formation
CREATE TABLE Formation (
    idFormation INT AUTO_INCREMENT PRIMARY KEY,
    nomFormation VARCHAR(50),
    promotion VARCHAR(50)
);

-- Table Etudiant
CREATE TABLE Etudiant (
    idEtudiant INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    idFormation INT,
    FOREIGN KEY (idFormation) REFERENCES Formation(idFormation)
);

-- Table Projet
CREATE TABLE Projet (
    idProjet INT AUTO_INCREMENT PRIMARY KEY,
    nomMatiere VARCHAR(50),
    sujet VARCHAR(100),
    dateRemisePrevue DATE
);

-- Table Binome
CREATE TABLE Binome (
    idBinome INT AUTO_INCREMENT PRIMARY KEY,
    idProjet INT,
    noteRapport DOUBLE,
    binomeReference VARCHAR(50) UNIQUE,
    dateRemiseEffective DATE,
    FOREIGN KEY (idProjet) REFERENCES Projet(idProjet) ON DELETE CASCADE
);

--Table association 

CREATE TABLE EtudiantBinome (
    idEtudiant INT,
    idBinome INT,
    noteSoutenance DOUBLE,
    FOREIGN KEY (idEtudiant) REFERENCES Etudiant(idEtudiant) ON DELETE CASCADE,
    FOREIGN KEY (idBinome) REFERENCES Binome(idBinome) ON DELETE CASCADE,
    PRIMARY KEY (idEtudiant, idBinome)
);

-- Vue data
CREATE VIEW dataVue AS
SELECT
    -- le nom et le prenom de l'étudiant
    e.nom AS etudiantNom,
    e.prenom AS etudiantPrenom,
    -- le nom de la formation de l'étudiant ainsi que la promotion
    f.nomFormation,
    f.promotion,
    -- le nom de la matière du projet ainsi que le sujet du projet
    p.nomMatiere,
    p.sujet,
    -- la note du rapport attribué au binome auquel appartient l'étudiant e
    b.noteRapport,
    -- la  note de soutenance attribué à l'étudiant e
    eb.noteSoutenance,
    -- la référence du binome ainsi que la date de remise effective du projet
    b.binomeReference,
    b.dateRemiseEffective,
    -- la date de remise du projet par le binome
    p.dateRemisePrevue
FROM
    Formation f
    -- on effectue une jointure entre la table Etudiant et la table formation pour récuperer le nom, le prénom de l'étudiant ainsi que le nom et la promotion de sa formation
    NATURAL JOIN Etudiant e
    -- Puis on effectue une jointure entre le résultat précédent et la table EtudiantBinome afin d'obtenir la note de soutenance attribuée à l'étudiant
    NATURAL JOIN EtudiantBinome eb
    -- On effectue une jointure avec la table Binome pour obtenir la note de rapport.
    NATURAL JOIN Binome b
    -- Enfin on effectue une dernière jointure pour obtenir le Projet concerné.
    NATURAL JOIN Projet p
ORDER BY
    binomeReference;

