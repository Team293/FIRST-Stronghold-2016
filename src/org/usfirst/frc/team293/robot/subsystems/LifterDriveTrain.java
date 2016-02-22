package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LifterDriveTrain extends Subsystem {//the lifter center wheel on the drivetrain
	private CANTalon lifterMotor;
	public boolean position;
	boolean state = false;
    public LifterDriveTrain(){
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	lifterMotor = new CANTalon(RobotMap.lifterMotor);
	lifterMotor.changeControlMode(TalonControlMode.Position);//Change
	// control mode of talon, default is PercentVbus (-1.0 to 1.0)
	lifterMotor.setFeedbackDevice(FeedbackDevice.AnalogEncoder);
	
	
	
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

	public void lift() {			//This powers up the motor to start lifting
		if (lifterMotor.getAnalogInRaw()<2.5) {			//This stops the motor when the angle hits a certain value, which is the lifting position
			lifterMotor.set(.5);
		}
		else {
			lifterMotor.set(0);
			state=true;
		}
	}
	public void drop() {			//This powers up the motor to start lifting
	/*	if (lifterMotor.getAnalogInRaw()>2.5) {			//I don't know this value
			lifterMotor.set(.5);
		}
		else {
			lifterMotor.set(0);
			state=false;
		}
	*/
	}
	public boolean getStatus(){
		return state;
	}
}

