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
	boolean state;
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

	public boolean startliftdrivetrain() {			//This powers up the motor to start lifting
		lifterMotor.set(.5);
		state=false;
		return state;
	}
	public boolean stopliftdrivetrain(){
		if (lifterMotor.getAnalogInRaw()>2.5) {			//This stops the motor when the angle hits a certain value, which is the lifting position
			state=true;
			
		}
		return state;
	}

	public boolean startdropdrivetrain() {			///This powers up the motor to start dropping the drivetrain
		lifterMotor.set(-.5);
		state=false;
		return state;
	}
	
	public boolean stopdropdrivetrain(){			//This stops the motor when the angle hits a certain value
		if (.01<lifterMotor.getAnalogInRaw()) {
			state=true;
			
		}
		return state;
	}

	public void lift() {
		lifterMotor.set(1);

		// lifterMotor.get //probably should use this....
		// lifterMotor.set(0);//https://wpilib.screenstepslive.com/s/3120/m/7912/l/85776-analog-triggers
	}
}

