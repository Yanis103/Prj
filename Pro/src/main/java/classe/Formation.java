package classe;

/**
 * Cette classe représente une Formation
 * Une Formation se caractérise par : 
 * 		idFormation : de type int, qui représente l'identifiant de la formation.
 * 		nomFormation : de type String, qui représente le nom de la formation.
 * 		promotion : de type String qui représente la promotion de la formation.
 */
public class Formation {
    private int idFormation;
    private String nomFormation;
    private String promotion;

    public Formation() {
        // Constructeur par défaut
    }

    public Formation(String nomFormation, String promotion) {
        this.nomFormation = nomFormation;
        this.promotion = promotion;
    }

    public int getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(int idFormation) {
        this.idFormation = idFormation;
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
}
