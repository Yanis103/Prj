package classe;
import java.time.LocalDate;

/**
 * Cette classe représente un Projet
 * Un Projet se caractérise par : 
 * 		idProjet : de type int, qui représente l'identifiant du Projet.
 * 		nomMatiere : de type String, qui représente le nom de la matière.
 * 		sujet : de type String qui représente le sujet du Projet.
 * 		dateRemisePrevue : de type LocalDate, représente la date à laquelle le Projet doit etre remis.
 */
public class Projet {
    private int idProjet;
    private String nomMatiere;
    private String sujet;
    private LocalDate dateRemisePrevue;

    public Projet() {
        // Constructeur par défaut
    }

    public Projet(String nomMatiere, String sujet, LocalDate dateRemisePrevue) {
        this.nomMatiere = nomMatiere;
        this.sujet = sujet;
        this.dateRemisePrevue = dateRemisePrevue;
    }

    public int getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(int idProjet) {
        this.idProjet = idProjet;
    }

    public String getNomMatiere() {
        return nomMatiere;
    }

    public void setNomMatiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public LocalDate getDateRemisePrevue() {
        return dateRemisePrevue;
    }

    public void setDateRemisePrevue(LocalDate dateRemisePrevue) {
        this.dateRemisePrevue = dateRemisePrevue;
    }
}
