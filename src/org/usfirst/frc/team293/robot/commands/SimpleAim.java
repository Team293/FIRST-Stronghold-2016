package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.OI;
import org.usfirst.frc.team293.robot.Robot;
//import org.usfirst.frc.team293.robot.subsystems.Camera;
import org.usfirst.frc.team293.robot.subsystems.Hood;
import org.usfirst.frc.team293.robot.subsystems.ShooterRotation;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SimpleAim extends Command {
	private double azimuth = 0.0;
	double[] Hoodangle={83,83,83,83,83,77,81,86,82,89,82,75,83,83};
    public SimpleAim() {
    	//double[] Hoodangle= new double[11];
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	Robot.drivetrain.setPID(0.06, 0.001, 0.01);
		requires(Robot.shooterrotation);
		requires(Robot.drivetrain);
		requires(Robot.ledHighGoal);
		requires(Robot.hood);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.initsensor();
    	Robot.shooterrotation.setangle(-Robot.Camera.getAzimuth()+Robot.shooterrotation.getangle());
    	Robot.Camera.shooterRotcompensation(true);
    	Robot.drivetrain.resetPID();
    	SmartDashboard.putBoolean("Using Aiming Backup", false);
    	Robot.continuousfunctions.setAiming(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.Camera.canSeeSwagadelia()){
    		azimuth = Robot.Camera.getAzimuth();
    		Robot.shooterrotation.setangle(Robot.shooterrotation.getangle()-azimuth);
    	}

    	if(azimuth > Robot.shooterrotation.getangle() + ShooterRotation.rotateRange[1] - 0.5 
    			|| azimuth < Robot.shooterrotation.getangle() - ShooterRotation.rotateRange[0] + 0.5 
    			&& Robot.drivetrain.returnAttitude()[0] != -1.0){
    		double absoluteAngle = azimuth + Robot.drivetrain.getAttitude()[0] - Robot.shooterrotation.getangle();
    		if(absoluteAngle > 360.0){
				absoluteAngle -= 360.0;
			}else if(absoluteAngle < 0.0){
				absoluteAngle += 360.0;
			}
    		//SmartDashboard.putNumber("absolute Angle", absoluteAngle);
    		Robot.drivetrain.setSetpoint(absoluteAngle);
    		Robot.drivetrain.PID();
			Robot.drivetrain.turnToAngle();
			SmartDashboard.putBoolean("using Backup", false);
    	}
    	if(Robot.drivetrain.returnAttitude()[0] == -1.0){
    		if(Robot.shooterrotation.getangle() > ShooterRotation.rotateRange[1] - 0.7){
    			Robot.drivetrain.turnLeft();
    		}else if(Robot.shooterrotation.getangle() < ShooterRotation.rotateRange[0] + 0.7){
    			Robot.drivetrain.turnRight();						////NO IDEA IF THIS IS RIGHT
    		}
    		SmartDashboard.putBoolean("using Backup", true);
    	}
    	Robot.Camera.shooterRotcompensation(false);
    	
    	///////////////////////////Distance Stuff///////////////////////////////////
    	int distance=(((int) Robot.Camera.getDistance())/12);
    	if(distance < Hoodangle.length){
    		Hood.setPosition(Hood.bottompoint-Hoodangle[distance]);
    		SmartDashboard.putNumber("AUTO aim angle", (Hood.bottompoint-Hoodangle[distance]));
    	}
    	SmartDashboard.putNumber("AUTO AIM DISTANCE FROM array", distance);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Math.abs(OI.getJoystick1()) > 0.8 || Math.abs(OI.getJoystick2()) > 0.8){
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.continuousfunctions.setAiming(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.continuousfunctions.setAiming(false);
    }
}
