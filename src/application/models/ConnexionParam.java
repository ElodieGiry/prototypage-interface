package application.models;

public class ConnexionParam {
	
	String identifiant;
	String typeConnexion;
	
	private static final ConnexionParam instance = new ConnexionParam();
	
	private ConnexionParam() {
    }
	
	public static final ConnexionParam getInstance(){
		return instance;
	}
	
	public void setConnexion(String identifiant, String typeConnexion){
		System.out.println("identifiant = "+identifiant);
		System.out.println("typeConnexion = "+typeConnexion);
		
        this.identifiant=identifiant;
        this.typeConnexion=typeConnexion;
    }
	
	public String getId() {
        return identifiant;
    }
    
    public String getTypeConnexion() {
        return typeConnexion;
    }
}

