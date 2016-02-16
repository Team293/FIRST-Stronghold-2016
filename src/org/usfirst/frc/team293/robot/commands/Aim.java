package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.subsystems.Hood;

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
		requires(Robot.hood);
		requires(Robot.shooterrotation);
		requires(Robot.drivetrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drivetrain.setPID(0.13, 0.0001, 0.001);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (Robot.Camera.canSeeSwagadelia() && Robot.drivetrain.returnAttitude()[0] != -1.0) {	
			/*********************************Angle Stuff************************************/
			System.out.println("TURNING TO GOAL");
			double azimuth = Robot.Camera.getAzimuth();
			if (azimuth <= 11.6) {	//if its just a shooter rotation
				Robot.shooterrotation.turnToGoal(azimuth);
			} else {
				double absoluteAngle = azimuth + Robot.drivetrain.getAttitude()[0];
				if(absoluteAngle > 360.0){
					absoluteAngle -= 360.0;
				}else if(absoluteAngle < 0.0){
					absoluteAngle += 360.0;
				}
				if (azimuth >= 0.0) {//if its more one direction
					Robot.drivetrain.setSetpoint(absoluteAngle - 12.0);
					Robot.drivetrain.PID();
					Robot.drivetrain.turnToAngle();
					Robot.shooterrotation.setsetpoint(12.0);
				} else {								//more the other way
					Robot.drivetrain.setSetpoint(absoluteAngle + 12.0);
					Robot.drivetrain.PID();
					Robot.drivetrain.turnToAngle();
					Robot.shooterrotation.setsetpoint(-12.0);
				}
			}
			/*********************************Distance Stuff***********************************/
			distance=Robot.Camera.getDistance();
			angle=-6.087e-3*Math.pow(distance, 3)+3.587e-1*Math.pow(distance,2)-7.783*distance+91.16;
			Hood.setPosition(angle);
		}
	
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (Math.abs(Robot.Camera.getAzimuth()) <= 1.0) {	//when we are within shooting point
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