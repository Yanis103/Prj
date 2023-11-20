package classe;
import java.time.LocalDate;

/**
 * Cette classe représente un Binome
 * Un Binome se caractérise par : 
 * 		idBinome : de type int, qui représente l'identifiant du binome.
 * 		projet : de type Projet, qui représente le projet sur lequel le binome travaille.
 * 		noteRapport : de type Double qui représente la note de rapport.
 * 		binomeReference : de type String, nom du binome.
 * 		dateRemiseEffective : de type LocalDate, date de remise du projet par le Binome.
 */

public class Binome {
    private int idBinome;
    private Projet projet; 
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
