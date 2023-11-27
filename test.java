package S5;

import lejos.hardware.sensor.EV3UltrasonicSensor;

import java.util.ArrayList;

import lejos.utility.Delay;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Moteur m = new Moteur();
		EV3UltrasonicSensor ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S4);
		SampleProvider distanceProvider = ultrasonicSensor.getDistanceMode(); 
		float[] sample = new float[distanceProvider.sampleSize()];
		
		 m.avancerSync(60); m.ouvrirPinces(1200); m.stop();
		 m.fermerPinces(1200);
		 m.tourner(90); m.avancer(15); m.tourner(-80); 
		 m.avancer(190);
		 m.ouvrirPinces(1200); m.avancer(-30); m.fermerPinces(1200);
		 m.tourner(-m.getDirection()); 
		 Delay.msDelay(2000);
		 m.tourner(120);		 
//		 m.avancerSync(f.enFace());
//		 while(m.isMoving()) {
//		 distanceProvider.fetchSample(sample, 0);
//		 if(sample[0]<=31) {
//			 f.enFace();
//		 }
		ArrayList<Float> samples = new ArrayList<Float>();
//		//m.tourner(201);
//		//m.ouvrirPinces(1200); m.stop();
////		 m.fermerPinces(1200);
////		 m.tourner(90); m.avancer(15); m.tourner(-90); 
////		 m.avancer(190);
////		 m.ouvrirPinces(1200); m.avancer(-30); m.fermerPinces(1200);
////		 Delay.msDelay(2000);
////		 System.out.println("je me mets droit");
////		 m.tourner(-m.getDirection()); 
////		 Delay.msDelay(2000);
////		 m.tourner(100);
		m.changerVitRot(36);
		int i = 0;
			
		  m.tournerSync(100); while(m.isMoving()) {
			  distanceProvider.fetchSample(sample, 0); samples.add(sample[0] * 100); //Convertir en centimètres 
			  System.out.println(i+"- "+samples.get(i)); i++; }
			  
			  double min=120;
		
			  for(int j=0; j<samples.size();j++) {
				  if(samples.get(j) < min && samples.get(j) >= 32) { min=samples.get(j); i=j; }
			  }
			  System.out.println("minimum :" +min); Delay.msDelay(3000);
			  System.out.println("size : " + samples.size()); Delay.msDelay(3000);
			  System.out.println("premier : "+ samples.get(0)); Delay.msDelay(3000);

			  m.changerVitRot(90);
			  //if(i<=180) { m.tourner(i); }else m.tourner(i-360);
			  m.tourner(i*100.000/samples.size());
			  m.avancer(min);
//			  distanceProvider.fetchSample(sample, 0);
//			  if(sample[0]<32)m.stop();
//			  System.out.println("en avance");
//				 while(m.isMoving()) {
//				 distanceProvider.fetchSample(sample, 0);
//				 if(sample[0]<=31) {
//					 m.stop();
//				 }}
//	
////		m.tourner(200);
//		m.avancer(180);
//		m.tourner(120);
//		m.tourner(-m.getDirection());
		}

	}

