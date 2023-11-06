package S5;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.Motor;
import lejos.utility.Delay;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.UARTSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.chassis.*;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class Perpendiculaire {

	public Perpendiculaire() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Moteur m = new Moteur();
		EV3UltrasonicSensor ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S4);

		        // Obtenir un SampleProvider pour mesurer la distance
		SampleProvider distanceProvider = ultrasonicSensor.getDistanceMode();
		float[] sample = new float[distanceProvider.sampleSize()];
		float[] tab = new float[81];
		m.changerVitRot(18);
		m.tournerSync(360);
		int i=0;
		while(m.isMoving()) {
			 distanceProvider.fetchSample(sample, 0);
	         tab[i] = sample[0] * 100; // Convertir en centimètres
	         
	         System.out.println(tab[i]);
	         i++;
	         Delay.msDelay(250);
		}
		
		  float min=tab[0]; for(int j=1; j<tab.length;j++) { 
			  if(min<32) 
			  { min=tab[j];
			  i=j; 
			  } 
			  if(tab[j]<min && tab[j]>=32) { 
				  min=tab[j]; i=j; 
				  } 
			  }
		  System.out.println("minimuuum"+tab[i]);
		  Delay.msDelay(3000);
		  m.changerVitRot(360); if(i<=20) { m.tourner((int) (i*4.5)); }else m.tourner((int) (i*4.5-360));
		 
		
		
		
		
		m.stop();
		
}
}


