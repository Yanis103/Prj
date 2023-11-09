package classe;

import java.time.LocalDate;

public class Binome {
    private int idBinome;
    private int idProjet;
    private double noteRapport;
    private String binomeReference;
    private LocalDate dateRemiseEffective;

    public Binome() {
        // Constructeur par défaut
    }

    public Binome(int idProjet, double noteRapport, String binomeReference, LocalDate dateRemiseEffective) {
        this.idProjet = idProjet;
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

    public int getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(int idProjet) {
        this.idProjet = idProjet;
    }

    public double getNoteRapport() {
        return noteRapport;
    }

    public void setNoteRapport(double noteRapport) {
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
