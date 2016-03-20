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
	public boolean position;
	boolean wheelSetUp = true;
	boolean itsoffthetape;
	private DigitalInput DriveLimit;
	public DigitalInput DriveLimitDown;
	//public Timer timer=new Timer();
	private boolean up = true;
	int pos = 0;

    public LifterDriveTrain(){
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	lifterMotor = new CANTalon(RobotMap.lifterMotor);
	lifterMotor.enableBrakeMode(true);
	DriveLimitDown=new DigitalInput(5);	//the Reflective Banner Sensor
	DriveLimit = new DigitalInput(12);
	
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }


public void lift1() {	//This powers up the motor to start lifting
	pos = position();
	if(pos != 1){
		lifterMotor.set(-.9);
		/*if (DriveLimitDown.get()==true){
			itsoffthetape=true;
		}
		if(itsoffthetape && !DriveLimit.get() && timer.get()>.2){
			lifterMotor.set(0);
			itsoffthetape=false;
			wheelSetUp=true;
			timer.reset();
		}*/
	}else{
		lifterMotor.set(0);
		//itsoffthetape=false;
		//wheelSetUp=true;
		//timer.reset();
	}
}
public void drop1() {
	pos = position();
	if(pos != -1){
		lifterMotor.set(.9);
		/*if (DriveLimit.get()==true){
			itsoffthetape=true;
		}
		if(itsoffthetape && !DriveLimitDown.get() && timer.get()>.2){
			lifterMotor.set(0);
			itsoffthetape=false;
			wheelSetUp=false;
			timer.reset();
		}*/
	}else{
		lifterMotor.set(0);
		//itsoffthetape=false;
		//wheelSetUp=false;
		//timer.reset();
	}
}

public void stopMotor(){
	lifterMotor.set(0);
}
	
public boolean change(){
	pos = position();
	if(pos == -1){
		up = true;
	}else{
		up = false;
	}
	return up;
	}
	
public boolean issetUp(){
	if(!DriveLimit.get()){
		wheelSetUp = true;
	}else if(!DriveLimitDown.get()){
		wheelSetUp = false;
	}
	return wheelSetUp;
}
	
	public int position(){
		if(DriveLimit.get()){
			return 1;
		}else if(!DriveLimitDown.get()){
			return -1;
		}
		return 0;
	}
}