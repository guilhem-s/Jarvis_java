package S5;
import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.Motor;
import lejos.utility.Delay;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.chassis.*;
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
		double diametre=2.2;
		int x =0;
        	/*c.pinces.rotate(angle);
         Delay.msDelay(10000);
       
        Motor.A.rotateTo(angle);
       */
		
        Wheel roue1 = WheeledChassis.modelWheel((RegulatedMotor)Motor.B,diametre ).offset(-70);
		Wheel roue2 = WheeledChassis.modelWheel((RegulatedMotor)Motor.A, diametre).offset(70);
		Chassis chassis = new WheeledChassis(new Wheel[] { roue1, roue2}, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
         pilot.travel(20);
         /*
         pilot.rotate(angle);
*/
	/*	
		for(x=0;x<=10;x++) {
		c.pinces.rotate(90);
		}
		}*/}}
