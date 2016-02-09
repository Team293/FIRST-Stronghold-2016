package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LifterDriveTrain extends Subsystem {
	private CANTalon lifterMotor;
    public LifterDriveTrain(){
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	lifterMotor = new CANTalon(RobotMap.lifterMotor);
	// lifterMotor.changeControlMode(TalonControlMode.Position);//Change
	// control mode of talon, default is PercentVbus (-1.0 to 1.0)
	lifterMotor.setFeedbackDevice(FeedbackDevice.AnalogPot);
	
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

	public void liftdrivetrain() {
		lifterMotor.set(.5);
		if (lifterMotor.getAnalogInRaw() == 2.5) {
			lifterMotor.set(0);
		}	
	}

	public void dropdrivetrain() {
		lifterMotor.set(-.5);
		if (lifterMotor.getAnalogInRaw() == 0) {
			lifterMotor.set(0);
		}
	}

	public void lift() {
		lifterMotor.set(1);
		if (lifterMotor.getAnalogInRaw() == 0) {
			lifterMotor.set(0);
		}
		// lifterMotor.get //probably should use this....
		// lifterMotor.set(0);//https://wpilib.screenstepslive.com/s/3120/m/7912/l/85776-analog-triggers
	}
}

