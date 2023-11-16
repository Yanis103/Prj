-- Table Etudiant
CREATE TABLE Etudiant (
    idEtudiant INT PRIMARY KEY,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    idFormation INT,
    FOREIGN KEY (idFormation) REFERENCES Formation(idFormation)
);

-- Table Formation
CREATE TABLE Formation (
    idFormation INT PRIMARY KEY,
    nomFormation VARCHAR(50),
    promotion ENUM('Initiale', 'En Alternance', 'Formation Continue')
);

-- Table Projet
CREATE TABLE Projet (
    idProjet INT PRIMARY KEY,
    nomMatiere VARCHAR(50),
    sujet VARCHAR(100),
    dateRemisePrevue DATE
);

-- Table Binome
CREATE TABLE Binome (
    idBinome INT AUTO_INCREMENT PRIMARY KEY,
    idProjet INT,
    noteRapport DOUBLE,
    FOREIGN KEY (idProjet) REFERENCES Projet(idProjet)
);

-- Table EtudiantBinome (Relation N-N entre Etudiant et Binome pour la composition des binômes)
CREATE TABLE EtudiantBinome (
    idEtudiant INT,
    idBinome INT,
    PRIMARY KEY (idEtudiant, idBinome),
    FOREIGN KEY (idEtudiant) REFERENCES Etudiant(idEtudiant),
    FOREIGN KEY (idBinome) REFERENCES Binome(idBinome)
);

-- Table NoteSoutenance (pour stocker les notes de soutenance)
CREATE TABLE NoteSoutenance (
    idNoteSoutenance INT AUTO_INCREMENT PRIMARY KEY,
    idBinome INT,
    idEtudiant INT,
    noteSoutenance DOUBLE,
    dateRemiseEffective DATE,
    FOREIGN KEY (idBinome) REFERENCES Binome(idBinome),
    FOREIGN KEY (idEtudiant) REFERENCES Etudiant(idEtudiant)
);