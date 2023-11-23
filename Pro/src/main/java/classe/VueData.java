package classe;

/**
 * Cette classe permet de rassembler l'ensemble des informations et de calculer la note finale de l'étudiant pour un projet précis
 */
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class VueData {
	/*Nom et prénom de l'étudiant*/
    private String etudiantNom;
    private String etudiantPrenom;
    /*Nom de la formation que suit l'étudiant ainsi que sa promotion*/
    private String nomFormation;
    private String promotion;
    /*le sujet du projet ainsi que la matière et la date de remise prévue*/
    private String nomMatiere;
    private String sujet;
    private LocalDate dateRemisePrevue; 
    /*la note de rapport du binome*/
    private Double noteRapport;
    /*la note de soutenance de l'étudiant*/
    private Double noteSoutenance;
    private String binomeReference;
    /*la date de remise du projet*/
    private LocalDate dateRemiseEffective;
    
    private Double noteFinale; 

    // Constructeur

    public VueData() {
    }

    // Getteurs et setteurs

    public String getEtudiantNom() {
        return etudiantNom;
    }

    public void setEtudiantNom(String etudiantNom) {
        this.etudiantNom = etudiantNom;
    }

    public String getEtudiantPrenom() {
        return etudiantPrenom;
    }

    public void setEtudiantPrenom(String etudiantPrenom) {
        this.etudiantPrenom = etudiantPrenom;
    }

    public String getNomFormation() {
        return nomFormation;
    }

    public void setNomFormation(String nomFormation) {
        this.nomFormation = nomFormation;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
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

    public Double getNoteRapport() {
        return noteRapport;
    }

    public void setNoteRapport(Double noteRapport) {
        this.noteRapport = noteRapport;
    }

    public Double getNoteSoutenance() {
        return noteSoutenance;
    }

    public void setNoteSoutenance(Double noteSoutenance) {
        this.noteSoutenance = noteSoutenance;
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

    public LocalDate getDateRemisePrevue() {
        return dateRemisePrevue;
    }

    public void setDateRemisePrevue(LocalDate dateRemisePrevue) {
        this.dateRemisePrevue = dateRemisePrevue;
    }

    public Double getNoteFinale() {
        return noteFinale;
    }

    public void setNoteFinale(Double noteFinale) {
        this.noteFinale = noteFinale;
    }
   
    public double calculerNoteFinale(Double noteRapport, Double noteSoutenance, LocalDate dateRemisePrevue, LocalDate dateRemiseEffective) {
        if (dateRemisePrevue == null || dateRemiseEffective == null) {
            // Gérer le cas où l'une des dates est nulle
            return 0.0;
        }

        double poidsNoteRapport = 0.8;
        double poidsNoteSoutenance = 0.2;

        // Calcul du retard en jours
        long retardsEnJours = ChronoUnit.DAYS.between(dateRemisePrevue, dateRemiseEffective);

        // Vérifier si le retard est négatif (date de remise en avance)
        if (retardsEnJours < 0) {
            // Retourner la note finale sans retard
            return Math.max(0.0, Math.min(noteRapport * poidsNoteRapport + noteSoutenance * poidsNoteSoutenance, 20.0));
        }

        // déduire un certain nombre de points par jour de retard
        double pointsParRetard = 0.25; // points en moins par jour de retard
        double noteFinaleSansRetard = (noteRapport * poidsNoteRapport) + (noteSoutenance * poidsNoteSoutenance);

        double noteFinaleAvecRetard = noteFinaleSansRetard - (pointsParRetard * retardsEnJours);

        // la note finale reste entre 0 et 20
        return Math.max(0.0, Math.min(noteFinaleAvecRetard, 20.0));
    }


}

