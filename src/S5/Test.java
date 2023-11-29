package S5;


public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Fonctionnalite f = new Fonctionnalite();
		Surveillance surveillance = new Surveillance();
		surveillance.lancerSurveillanceBouton();
		f.troisPremiers();

        surveillance.attendreFinSurveillance();	 
		}
}
		
			  

				
//	
////		m.tourner(200);
//		m.avancer(180);
//		m.tourner(120);
//		m.tourner(-m.getDirection());



