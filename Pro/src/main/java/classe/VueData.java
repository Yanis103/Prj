package classe;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class VueData {
    private String etudiantNom;
    private String etudiantPrenom;
    private String nomFormation;
    private String promotion;
    private String nomMatiere;
    private String sujet;
    private Double noteRapport;
    private Double noteSoutenance;
    private String binomeReference;
    private LocalDate dateRemiseEffective;
    private LocalDate dateRemisePrevue; 
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

