package S5;
import java.util.HashMap;
import java.util.Map;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.utility.Delay;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.BaseSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.chassis.*;
import lejos.robotics.*;
public class App {

	public App() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*GraphicsLCD g=BrickFinder.getDefault().getGraphicsLCD();
			g.drawString("GJW9", 0, 0, GraphicsLCD.VCENTER |
			GraphicsLCD.LEFT);
		
			
			Delay.msDelay(5000);*/
		final int traverser_le_terrain = 100;
		Map<Integer, String> map = new HashMap<>();
		map.put(0, "Rouge");
		map.put(1, "Vert");
		map.put(2, "Bleu");
		map.put(3, "Jaune");
		map.put(6, "Blanc");
		map.put(7, "Noir");
		map.put(9, "Gris");
		map.put(10, "Gris");
		map.put(11, "Gris");  
		//Controller c = new Controller(MotorPort.A, MotorPort.C, MotorPort.D);
	 /*c.run();
	}*/
		/*c.sweepClockwise(90);*/
		
		/*c.skip_forward(300);*/
		//DifferentialDrive dd = new DifferentialDrive(MotorPort.A, MotorPort.B);
		/*for(int i=0;i<250000;i++) {
			dd.rotateClockwise();
			i++;
		}*/
		
		//for (int i=0; i<50;i++)dd.getmLeftMotor().forward();
		int angle = 45;
		double diametre=5.6;
		int x =0;
        	/*c.pinces.rotate(angle);
         Delay.msDelay(10000);
       
        Motor.A.rotateTo(angle);
       */
		
        Wheel roue1 = WheeledChassis.modelWheel((RegulatedMotor)Motor.B,diametre ).offset(-70);
		Wheel roue2 = WheeledChassis.modelWheel((RegulatedMotor)Motor.A, diametre).offset(70);
		Chassis chassis = new WheeledChassis(new Wheel[] { roue1, roue2}, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		CommandePinces p = new CommandePinces();
		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S2);
		/*EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S3);
	    SampleProvider touchProvider = touchSensor.getTouchMode();*/
	    //float[] sample = new float[touchProvider.sampleSize()];
	   // Sound.playTone(7,3000 , 100);
	    /*SensorModes touch = new EV3TouchSensor(SensorPort.S3);
		SampleProvider t = touch.getMode("touch");*/
		/*float[] ech = new float[touchProvider.sampleSize()];
		touchProvider.fetchSample(ech, 0);*/
		 EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S3);

	        SampleProvider touchProvider = touchSensor.getTouchMode();

	        float[] sample = new float[touchProvider.sampleSize()];
	        p.fermerPinces(1600);
		p.ouvrirPinces(1600);
		pilot.setLinearSpeed(100);
		pilot.forward();
		while (!Button.ENTER.isDown()) {
        touchProvider.fetchSample(sample, 0);
		if (sample[0]==1.00) {
			p.fermerPinces(1600);
			break;
		}
            try {
                Thread.sleep(1000); // Attendez 1 seconde
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		
		//-----------------------
		
		pilot.setLinearSpeed(1500);
		pilot.forward();
	    
		//-----------------------
		
		while (!Button.ENTER.isDown()) {
			int colorID = colorSensor.getColorID();
			if(colorID==6) {
				Motor.D.rotate(1600, true);
				break;
			}
			
            try {
                Thread.sleep(1000); // Attendez 1 seconde
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        p.fermerPinces(1600);
        pilot.travel(-20);
        }}}
		
		//pilot.travel(20,true);
		//p.ouvrirPinces(1600);
		
	
	
	
	//-----------------------

     // Obtient un SampleProvider pour le capteur de toucher

     // Crée un tableau pour stocker les échantillons du capteur de toucher
     

     // Boucle principale
     
		/*NXTRegulatedMotor pinces=Motor.D;
		System.out.println(pinces.getLimitAngle()); 
		Button.waitForAnyPress();
		pinces.stop();
		pilot.travel(240,true);
		p.fermerPinces(500);
		p.ouvrirPinces(500);
		pilot.travel(-20);
		pilot.rotate(16);*/
		/*NXTRegulatedMotor pinces=Motor.D;
		System.out.println(pinces.getPosition());
		Button.waitForAnyPress();
		pinces.stop();*/
         /*
         pilot.rotate(angle);
*/
	/*	
		for(x=0;x<=10;x++) {
		c.pinces.rotate(90);
		}
		}*/
