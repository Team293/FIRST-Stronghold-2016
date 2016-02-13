package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Aim extends Command {// sets up the shooter to match the camera
									// stuff.
	double distance;
	double angle;

	public Aim() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.hood);
		requires(Robot.shooterrotation);
		requires(Robot.drivetrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (Robot.Camera.canSeeSwagadelia()) {	
			//////////////Angle Stuff
			if (Robot.Camera.getAzimuth() <= 10.0) {	//if its just a shooter rotation
				Robot.shooterrotation.setsetpoint(Robot.Camera.getAzimuth());
			} else {
				if (Robot.Camera.getAzimuth() >= 0) {//if its more one direction
					Robot.drivetrain.turnToAngle(Robot.Camera.getAzimuth() - 12);
					Robot.shooterrotation.setsetpoint(12);
				} else {								//more the other way
					Robot.drivetrain.turnToAngle(Robot.Camera.getAzimuth()+12);
					Robot.shooterrotation.setsetpoint(-12);
				}
			}
			//////////////Distance stuff
			distance=Robot.Camera.getDistance();
			angle=-6.087e-3*Math.pow(distance, 3)+3.587e-1*Math.pow(distance,2)-7.783*distance+91.16;
			Robot.hood.setposition(angle);
			
		}
	
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (Math.abs(Robot.shooterrotation.getShooterAngle()
				- Robot.Camera.getAzimuth()) <= 1.0) {	//when we are within shooting point
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
