package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LifterDriveTrain extends Subsystem {//the lifter center wheel on the drivetrain
	private static CANTalon lifterMotor;
	public boolean position;
	boolean wheelSetUp = false;
	boolean itsoffthetape;
	DigitalInput DriveLimit;

    public LifterDriveTrain(){
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	lifterMotor = new CANTalon(RobotMap.lifterMotor);
	lifterMotor.enableBrakeMode(true);
	DriveLimit=new DigitalInput(RobotMap.Drivetrainlimit);	//the Reflective Banner Sensor
	
	
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

	public void lift() {	//This powers up the motor to start lifting
		if(wheelSetUp==false){
		lifterMotor.set(.5);
		if (DriveLimit.get()==false){
			itsoffthetape=true;
		}
		if(itsoffthetape==true && DriveLimit.get()==true){
			lifterMotor.set(0);
			itsoffthetape=false;
			wheelSetUp=true;
		}
	}
	}
	public void drop() {
		if(wheelSetUp==true){
		lifterMotor.set(-.5);
		if (DriveLimit.get()==false){
			itsoffthetape=true;
		}
		if(itsoffthetape==true && DriveLimit.get()==true){
			lifterMotor.set(0);
			itsoffthetape=false;
			wheelSetUp=false;
		}
	}
	}
	
	public boolean issetUp(){
		return wheelSetUp;
	}

}