package S5;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;

public class DifferentialDrive
{
    private EV3LargeRegulatedMotor mLeftMotor;
    private EV3LargeRegulatedMotor mRightMotor;

    private final static int SPEED = 1500;


    public DifferentialDrive(Port left_port, Port right_port)
    {
        setmLeftMotor(new EV3LargeRegulatedMotor(left_port));
        setmRightMotor(new EV3LargeRegulatedMotor(right_port));

        getmLeftMotor().setSpeed(SPEED);
        getmRightMotor().setSpeed(SPEED);
    }


    public void forward()
    {
        getmLeftMotor().forward();
        getmRightMotor().forward();
    }


    public void stop()
    {
        getmLeftMotor().stop();
        getmRightMotor().stop();
    }


    public void rotateClockwise()
    {
        getmLeftMotor().forward();
        getmRightMotor().backward();
    }


    public void rotateCounterClockwise()
    {
        getmLeftMotor().backward();
        getmRightMotor().forward();
    }


	public EV3LargeRegulatedMotor getmRightMotor() {
		return mRightMotor;
	}


	public void setmRightMotor(EV3LargeRegulatedMotor mRightMotor) {
		this.mRightMotor = mRightMotor;
	}


	public EV3LargeRegulatedMotor getmLeftMotor() {
		return mLeftMotor;
	}


	public void setmLeftMotor(EV3LargeRegulatedMotor mLeftMotor) {
		this.mLeftMotor = mLeftMotor;
	}
}
