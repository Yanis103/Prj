package exception;

public class EtudiantInexistant extends Exception {
    private int idEtudiant;

    public EtudiantInexistant(int id) {
        super("Etudiant dont l'ID est " + id + " n'existe pas");
        this.idEtudiant = id;
    }
}