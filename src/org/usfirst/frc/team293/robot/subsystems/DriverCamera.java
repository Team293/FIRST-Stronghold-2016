package org.usfirst.frc.team293.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriverCamera extends Subsystem {
	Servo drivercamera;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public void StraightAhead() {
		drivercamera.set(0);
	}

	public void Lookup() {
		drivercamera.set(.7);
	}


	public void initDefaultCommand() {

		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
