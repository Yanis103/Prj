package classe;

/**
 * Cette classe représente un Etudiant
 * Un Etudiant se caractérise par : 
 * 		idEtudiant : de type int, qui représente le numéro étudiant.
 * 		nom : de type String, qui représente le nom de l'étudiant.
 * 		prenom : de type String qui représente le prénom de l'étudiant.
 * 		formation : de type Formation, qui représente la formation suivie par l'étudiant.
 */
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
