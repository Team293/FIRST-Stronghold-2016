package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterWheel extends Subsystem {
	CANTalon shooterwheel;
	double scalefactor = -2.4;
	//30/42

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public ShooterWheel() {		//setup the PID on the shooterwheel Using a CANTalon
		super();
		shooterwheel = new CANTalon(RobotMap.shooterwheel);
		shooterwheel.changeControlMode(TalonControlMode.Speed);
		shooterwheel.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooterwheel.reverseOutput(true);
		shooterwheel.reverseSensor(true);
		shooterwheel.setPID(18, 0.00001, 375); // Set the PID constants (p, i, d)
		shooterwheel.setF(.2);// what we think it should be

		shooterwheel.enableControl(); // Enable PID control on the talon

	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void setShooterRPM() {
		shooterwheel.enableControl();
		shooterwheel.setSetpoint(2800*scalefactor);
	}

	public void disableShooter() {
		shooterwheel.disableControl();
	}
	
	public boolean atSpeed(){
		return (Math.abs(shooterwheel.getEncVelocity()) < 175.0);
	}
	public void printShooter(){
		SmartDashboard.putNumber("Shooter RPM", shooterwheel.getSpeed()/scalefactor);
	}
}
