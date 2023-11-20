package classe;

import java.time.LocalDate;

public class Binome {
    private int idBinome;
    private Projet projet; // Utiliser l'objet Projet
    private Double noteRapport;
    private String binomeReference;
    private LocalDate dateRemiseEffective;

    public Binome() {
        // Constructeur par défaut
    }

    public Binome(Projet projet, Double noteRapport, String binomeReference, LocalDate dateRemiseEffective) {
        this.projet = projet;
        this.noteRapport = noteRapport;
        this.binomeReference = binomeReference;
        this.dateRemiseEffective = dateRemiseEffective;
    }

    public int getIdBinome() {
        return idBinome;
    }

    public void setIdBinome(int idBinome) {
        this.idBinome = idBinome;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public Double getNoteRapport() {
        return noteRapport;
    }

    public void setNoteRapport(Double noteRapport) {
        this.noteRapport = noteRapport;
    }

    public String getBinomeReference() {
        return binomeReference;
    }

    public void setBinomeReference(String binomeReference) {
        this.binomeReference = binomeReference;
    }

    public LocalDate getDateRemiseEffective() {
        return dateRemiseEffective;
    }

    public void setDateRemiseEffective(LocalDate dateRemiseEffective) {
        this.dateRemiseEffective = dateRemiseEffective;
    }
}
