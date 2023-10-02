import lejos.hardware.sensor.EV3UltrasonicSensor;


public class Test {
    public static void main(String args[]) {
        EV3UltrasonicSensor ultrason = new EV3UltrasonicSensor(UARTPort 4);
        if (!ultrason.isenable())
            ultrason.enable();
        print(ultrason.getDistanceMode()); /* comment sont stockées les données
        besoin de rajouter un tableau pour stocker les données ? */
    }
}
