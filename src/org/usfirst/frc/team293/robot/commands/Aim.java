package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.subsystems.Arduino;
//import org.usfirst.frc.team293.robot.subsystems.Arduino;
import org.usfirst.frc.team293.robot.subsystems.Hood;
import org.usfirst.frc.team293.robot.subsystems.ShooterRotation;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */

public class Aim extends Command {// sets up the shooter to match the camera
									// stuff.
	private static double distance;
	private static double angle;
	

	public Aim() {//auto aiming
		requires(Robot.hood);
		requires(Robot.shooterrotation);
		requires(Robot.drivetrain);
		requires(Robot.ledHighGoal);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drivetrain.setPID(0.001, 0.00001, 0.001);
		Robot.continuousfunctions.setAiming(true);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.ledHighGoal.flash();
		if (Robot.Camera.canSeeSwagadelia()) {	
			/*********************************Angle Stuff************************************/
			double azimuth = Robot.Camera.getAzimuth();
			if (azimuth <= 11.6) {	//if its just a shooter rotation
				Robot.shooterrotation.turnToGoal(azimuth);
			} else if(Robot.drivetrain.returnAttitude()[0] != -1.0){
				double absoluteAngle = azimuth + Robot.drivetrain.getAttitude()[0];
				if(absoluteAngle > 360.0){
					absoluteAngle -= 360.0;
				}else if(absoluteAngle < 0.0){
					absoluteAngle += 360.0;
				}
				if (azimuth >= 0.0) {//if its more one direction
					Robot.drivetrain.setSetpoint(absoluteAngle - ShooterRotation.rotateRange[1]);
					Robot.drivetrain.PID();
					Robot.drivetrain.turnToAngle();
					Robot.shooterrotation.setsetpoint(ShooterRotation.rotateRange[1]);
				} else {								//more the other way
					Robot.drivetrain.setSetpoint(absoluteAngle - ShooterRotation.rotateRange[0]);
					Robot.drivetrain.PID();
					Robot.drivetrain.turnToAngle();
					Robot.shooterrotation.setsetpoint(ShooterRotation.rotateRange[0]);
				}
			}else{
			}
			/*********************************Distance Stuff***********************************/
			distance=Robot.Camera.getDistance();
			angle=getAngle(distance);
			Robot.hood.setPosition(angle);
		}
	}
	
	private static double getAngle(double distance){
		return -6.087e-3*Math.pow(distance, 3)+3.587e-1*Math.pow(distance,2)-7.783*distance+91.16;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (Robot.Camera.isAimed()) {	//when we are within shooting point
			Robot.ledHighGoal.off();
			Robot.ledManual.on();
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drivetrain.tankdrive(0, 0);
		Robot.continuousfunctions.setAiming(false);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
	
}