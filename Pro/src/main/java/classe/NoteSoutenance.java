package classe;

public class NoteSoutenance {
    private int idNoteSoutenance;
    private int idBinome;
    private int idEtudiant;
    private double noteSoutenance;

    public NoteSoutenance() {
        // Constructeur par défaut
    }

    public NoteSoutenance(int idBinome, int idEtudiant, double noteSoutenance) {
        this.idBinome = idBinome;
        this.idEtudiant = idEtudiant;
        this.noteSoutenance = noteSoutenance;
    }

    // Getters et setters

    public int getIdNoteSoutenance() {
        return idNoteSoutenance;
    }

    public void setIdNoteSoutenance(int idNoteSoutenance) {
        this.idNoteSoutenance = idNoteSoutenance;
    }

    public int getIdBinome() {
        return idBinome;
    }

    public void setIdBinome(int idBinome) {
        this.idBinome = idBinome;
    }

    public int getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(int idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public double getNoteSoutenance() {
        return noteSoutenance;
    }

    public void setNoteSoutenance(double noteSoutenance) {
        this.noteSoutenance = noteSoutenance;
    }
}
