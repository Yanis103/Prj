package classe;

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
