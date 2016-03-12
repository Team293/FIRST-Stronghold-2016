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
	//double speed;
	//30/42

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public ShooterWheel() {		//setup the PID on the shooterwheel Using a CANTalon
		super();
		// speed=SmartDashboard.getNumber("SetShooterPercentage");
		shooterwheel = new CANTalon(RobotMap.shooterwheel);
		shooterwheel.changeControlMode(TalonControlMode.Speed);
		shooterwheel.setFeedbackDevice(FeedbackDevice.EncFalling);
		shooterwheel.reverseOutput(false);
		shooterwheel.reverseSensor(false);
		shooterwheel.configEncoderCodesPerRev(4);
		//shooterwheel.getMotionProfileTopLevelBufferCount();
		shooterwheel.setPID(225,0.00001, 0); // Set the PID constants (p, i, d)
		shooterwheel.setF(.6);// what we think it should be

		shooterwheel.enableControl(); // Enable PID control on the talon

	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void setShooterRPM() {
		shooterwheel.enableControl();
		shooterwheel.setSetpoint(3400);
	}

	public void disableShooter() {
		shooterwheel.disableControl();
	}
	
	public boolean atSpeed(){
		return (Math.abs(shooterwheel.getEncVelocity()) < 175.0);
	}
	
	public boolean atSetpoint(){
		return (shooterwheel.getSpeed() > 1600);
	}
	
	public void printShooter(){
		SmartDashboard.putNumber("Shooter RPM", shooterwheel.getSpeed());
		SmartDashboard.putNumber("shooteroutput", shooterwheel.getOutputVoltage());
	}
}
