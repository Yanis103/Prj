package classe;

public class EtudiantBinome {
    private int idEtudiant;
    private int idBinome;

    public EtudiantBinome() {
        // Constructeur par défaut
    }

    public EtudiantBinome(int idEtudiant, int idBinome) {
        this.idEtudiant = idEtudiant;
        this.idBinome = idBinome;
    }

    public int getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(int idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public int getIdBinome() {
        return idBinome;
    }

    public void setIdBinome(int idBinome) {
        this.idBinome = idBinome;
    }
}
