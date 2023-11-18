package classe;

public class EtudiantBinome {
    private Etudiant etudiant;
    private Binome binome;
    private Double noteSoutenance;

    public EtudiantBinome() {
        // Constructeur par défaut
    }

    public EtudiantBinome(Etudiant etudiant, Binome binome, Double noteSoutenance) {
        this.etudiant = etudiant;
        this.binome = binome;
        this.noteSoutenance = noteSoutenance;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Binome getBinome() {
        return binome;
    }

    public void setBinome(Binome binome) {
        this.binome = binome;
    }

    public Double getNoteSoutenance() {
        return noteSoutenance;
    }

    public void setNoteSoutenance(Double noteSoutenance) {
        this.noteSoutenance = noteSoutenance;
    }
    
    public void updateEtudiantBinome(EtudiantBinome updatedEtudiantBinome) {
        // Mettez à jour les propriétés de l'objet avec les nouvelles valeurs
        this.etudiant = updatedEtudiantBinome.etudiant;
        this.binome = updatedEtudiantBinome.binome;
        this.noteSoutenance = updatedEtudiantBinome.noteSoutenance;
    }
}
