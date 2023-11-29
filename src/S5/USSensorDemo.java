package S5;

import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class USSensorDemo {

	/*private static EV3UltrasonicSensor us1 = new EV3UltrasonicSensor(SensorPort.S4);

	public static void main(String[] args) {

		final SampleProvider sp = us1.getDistanceMode();
		int distanceValue = 0;

        final int iteration_threshold = 100;
        for(int i = 0; i <= iteration_threshold; i++) {

        	float [] sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            distanceValue = (int)sample[0];

			System.out.println("Iteration: " + i + ", Distance: " + distanceValue);

			Delay.msDelay(500);
        }

	}*/
	public static void main(String[] args) {
        // Créer une instance du capteur à ultrasons sur le port S1 (ou le port que vous utilisez)
//        EV3UltrasonicSensor ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S4);
//
//        // Obtenir un SampleProvider pour mesurer la distance
//        SampleProvider distanceProvider = ultrasonicSensor.getDistanceMode();
//        float[] sample = new float[distanceProvider.sampleSize()];
//        Wheel roue1 = WheeledChassis.modelWheel((RegulatedMotor)Motor.B,5.6).offset(-7.1);
//		Wheel roue2 = WheeledChassis.modelWheel((RegulatedMotor)Motor.A,5.6).offset(7.1);
//		Chassis chassis = new WheeledChassis(new Wheel[] { roue1, roue2}, WheeledChassis.TYPE_DIFFERENTIAL);
//		MovePilot pilot = new MovePilot(chassis);
//        /* Boucle de mesure de la distance
//         * Palet : 32-100cm pour le détecter
//         * mur : 0-249cm
//         * 
//         */
//        while (true) {
//        	
//            distanceProvider.fetchSample(sample, 0);
//            float distance = sample[0] * 100; // Convertir en centimètres
//            System.out.println("Distance : " + distance + " cm");
//            	pilot.travel(300,true);
//            if(distance<40) {
//            	pilot.rotate(90);
//            }
//            try {
//                Thread.sleep(1000); // Attendre 1 seconde entre les mesures
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if(Button.ENTER.isDown()) break;
//        }
		 EV3UltrasonicSensor us1 = new EV3UltrasonicSensor(SensorPort.S4);
    		final SampleProvider sp = us1.getDistanceMode();
    		float distanceValue = 0;

            final int iteration_threshold = 100;
            for(int i = 0; i <= iteration_threshold; i++) {

            	float [] sample = new float[sp.sampleSize()];
                sp.fetchSample(sample, 0);
                distanceValue = sample[0];

    			System.out.println("Iteration: " + i + ", Distance: " + distanceValue*100);

    			Delay.msDelay(500);
            }

    	}

}
