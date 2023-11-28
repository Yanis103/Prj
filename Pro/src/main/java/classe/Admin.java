package classe;

public class Admin {
	private String identifiant;
	private String motDePasse;
	
	public Admin() {
		
	}
	
	public Admin(String identifiant, String motDepasse) {
		this.identifiant = identifiant;
		this.motDePasse = motDepasse;
	}
	
	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}
	
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
	public String getIdentifiant() {
		return this.identifiant;
	}
	
	public String getMotDePasse() {
		return this.motDePasse;
	}
}
