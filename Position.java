package ev3dev.sensors.ev3;
import USSensorDemo;
public class Position() {
  Wheel roue1 = WheeledChassis.modeWheel((RegulatorMotor) Motor.B,diametre).offset(70);
  Wheel roue2 = WheeledChassis.modeWheel((RegulatorMotor) Motor.A,diametre).offset(-70);
  Chassis chassis = new WheeledChassis(new Wheel[] {roue1, roue2}), WheeledChassis.TYPE_DIFFERENTIAL);
  MovePilot pilot = new MovePilot(chassis);
  int x=10; //Constante à définir en testant

  public static void perpendiculaire(){
    pilot.travel(x);
  }
}
    
