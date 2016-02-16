package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterWheel extends Subsystem {
	CANTalon shooterwheel;
	double scalefactor = -1.66;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public ShooterWheel() {		//setup the PID on the shooterwheel Using a CANTalon
		super();
		shooterwheel = new CANTalon(RobotMap.shooterwheel);
		shooterwheel.changeControlMode(TalonControlMode.Speed);
		shooterwheel.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooterwheel.setPID(3, 0.001, .5); // Set the PID constants (p, i, d)
		shooterwheel.setF(.4);// what we think it should be

		shooterwheel.enableControl(); // Enable PID control on the talon
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void setShooterRPM() {
		shooterwheel.enableControl();
		shooterwheel.setSetpoint(2400);
	}

	public void disableShooter() {
		shooterwheel.disableControl();
	}
}
