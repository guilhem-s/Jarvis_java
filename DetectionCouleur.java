package S5;


import java.util.HashMap;
import java.util.Map;
import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;

public class DetectionCouleur {
    public static void main(String[] args) {
    	
    	//Convertir entier en string avec dictionnaire
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
    	
    	
    	
        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);
        if(! colorSensor.isFloodlightOn())  // teste si la lampe est allumée
        	colorSensor.setFloodlight(true);
        while (true) {
            int colorID = colorSensor.getColorID();
            System.out.println("Couleur detectee : " + map.get(colorID));
            try {
                Thread.sleep(1000); // Attendre 1 seconde entre les mesures
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(Button.ENTER.isDown())break;
        }
        colorSensor.close();
    }
}
