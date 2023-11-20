package classe;

public class Etudiant {
    private int idEtudiant;
    private String nom;
    private String prenom;
    private Formation formation;

    public Etudiant() {
    }

    public Etudiant(String nom, String prenom, Formation formation) {
        this.nom = nom;
        this.prenom = prenom;
        this.formation = formation;
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

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }
}
