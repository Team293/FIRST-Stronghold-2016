package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterRotation extends Subsystem {//rotate the shooter and setup that PID
	private CANTalon shooterrotation;
	private static double[] rotateRange = {-12.0,12.0};

	public ShooterRotation() {
		super();
		shooterrotation = new CANTalon(RobotMap.shooterRotation);
		shooterrotation.changeControlMode(TalonControlMode.Position);
		shooterrotation.setFeedbackDevice(FeedbackDevice.AnalogPot);
		shooterrotation.setPID(1, .001, 1);
		shooterrotation.enableControl(); // Enable PID control on the talon
	}
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void setsetpoint(double angle) {	//this can only happen if we are above 10 degrees
		angle = Math.min(Math.max(angle,rotateRange[0]), rotateRange[1]);			//Constrain Angle Value
		shooterrotation.set(angle);
	}
	
	public void turnToGoal(double angle){
		this.setsetpoint(this.getShooterAngle() + angle);
	}
	
	public double getShooterAngle(){
		return shooterrotation.getPosition();
	}
}
