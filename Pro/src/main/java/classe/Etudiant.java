package classe;

public class Etudiant {
    private int idEtudiant;
    private String nom;
    private String prenom;
    private int idFormation;

    public Etudiant() {
        // Constructeur par défaut
    }

    public Etudiant(String nom, String prenom, int idFormation) {
        this.nom = nom;
        this.prenom = prenom;
        this.idFormation = idFormation;
    }

    public int getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(int idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(int idFormation) {
        this.idFormation = idFormation;
    }
}
