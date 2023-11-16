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
    FOREIGN KEY (idProjet) REFERENCES Projet(idProjet)
);

-- Table NoteSoutenance
CREATE TABLE NoteSoutenance (
    idBinome INT,
    idEtudiant INT,
    PRIMARY KEY (idEtudiant, idBinome),
    noteSoutenance DOUBLE,
    FOREIGN KEY (idBinome) REFERENCES Binome(idBinome),
    FOREIGN KEY (idEtudiant) REFERENCES Etudiant(idEtudiant)
);

