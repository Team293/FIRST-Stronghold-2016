package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriverCamera extends Subsystem {//Changes angle for camera for driver
	Servo drivercamera=new Servo(RobotMap.drivercamera);

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public void LookDown() {
		drivercamera.set(-1);
	}

	public void Lookup() {
		drivercamera.set(1);
	}


	public void initDefaultCommand() {

		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
