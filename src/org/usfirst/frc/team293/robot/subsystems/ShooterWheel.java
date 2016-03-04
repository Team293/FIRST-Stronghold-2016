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
	double scalefactor = -2.5;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public ShooterWheel() {		//setup the PID on the shooterwheel Using a CANTalon
		super();
		shooterwheel = new CANTalon(RobotMap.shooterwheel);
		shooterwheel.changeControlMode(TalonControlMode.Speed);
		shooterwheel.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooterwheel.setPID(-1, 0.00001, .001); // Set the PID constants (p, i, d)
		shooterwheel.setF(.1);// what we think it should be

		shooterwheel.enableControl(); // Enable PID control on the talon

	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void setShooterRPM() {
		shooterwheel.enableControl();
		shooterwheel.setSetpoint(-2400*scalefactor);
	}

	public void disableShooter() {
		shooterwheel.disableControl();
	}
	
	public boolean atSpeed(){
		return (Math.abs(shooterwheel.getEncVelocity()) < 175.0);
	}
	public void printShooter(){
		
	}
}
