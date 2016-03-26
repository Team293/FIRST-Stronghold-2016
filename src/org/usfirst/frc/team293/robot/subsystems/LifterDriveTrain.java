package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LifterDriveTrain extends Subsystem {							//the lifter center wheel on the drivetrain
	public static CANTalon lifterMotor;
	public int position;
	boolean wheelSetUp = true;
	public boolean finished;
	public boolean intendedposition;
	//public Timer timer=new Timer();
	private boolean up = true;
	int pos = 0;
	
	private static double bottomposition=300;
	private static double topposition=350;

    public LifterDriveTrain(){
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	lifterMotor = new CANTalon(RobotMap.lifterMotor);
	lifterMotor.enableBrakeMode(true);
	lifterMotor.setFeedbackDevice(FeedbackDevice.AnalogPot);
	lifterMotor.changeControlMode(TalonControlMode.PercentVbus);

	
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }


    public boolean lift() {	//This powers up the motor to start lifting
    	lifterMotor.set(-.5);
    	if(lifterMotor.getAnalogInRaw()==bottomposition){
    		lifterMotor.set(0);
    		finished=true;
    	}
    	return finished;
    }
    public boolean drop() {
    	lifterMotor.set(.5);
    	if(lifterMotor.getAnalogInRaw()==topposition){
    		lifterMotor.set(0);
    		finished=true;
    	}
    	return true;
    }

    public void stopMotor(){
    	lifterMotor.set(0);
    }
	
    public int isattop(){   //returns position of the cam at the current time.
    	double angle=lifterMotor.getAnalogInRaw();
    	if (angle>(bottomposition-10)&&angle<(bottomposition+10)){
    		position=2;		//at bottom
    	}
    	if (angle>(topposition-10)&&angle<(topposition+10)){
    		position=0;		//at top
    	}
    	else{
    		position=1;		//in the middle uh oh.
    	}
    	return position;
    }
    
}