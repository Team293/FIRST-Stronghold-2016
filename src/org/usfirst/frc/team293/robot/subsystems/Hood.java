package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;
import org.usfirst.frc.team293.robot.commands.HoodRestPosition;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Hood extends Subsystem {//the hood that aims up and down and manipulates.  PID
	private static CANTalon Hood;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public Hood() {
		super();
		Hood = new CANTalon(RobotMap.hoodMotor);
		Hood.changeControlMode(TalonControlMode.Position);
		Hood.setFeedbackDevice(FeedbackDevice.AnalogPot);
		Hood.setPID(3, 0.001, 0.0); // Set the PID constants (p, i, d)

		Hood.enableControl(); // Enable PID control on the talon
	}

	public void initDefaultCommand() {
		
		// Set the default command for a subsystem here.
		 setDefaultCommand(new HoodRestPosition());
	}

	public static void setposition() {
		double x=Camera.getDistance();

	}
}
