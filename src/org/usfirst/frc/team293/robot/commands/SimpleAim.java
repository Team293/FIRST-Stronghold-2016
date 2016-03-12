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
	double[] Hoodangle={83,83,83,83,65,66,74,75,79,80,83,78,75,83};
	/////////////////////0  1  2  3  4  5  6  7  8  9 10 11 12 13
	private boolean continuous = false;
	private long wasAimedAt = -1100;
	private final long aimTime = 1000;
	boolean aimed = false;
    public SimpleAim(boolean cont) {
    	//double[] Hoodangle= new double[11];
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	Robot.drivetrain.setPID(0.024, 0.001, 0.01);
		requires(Robot.shooterrotation);
		requires(Robot.drivetrain);
		requires(Robot.ledHighGoal);
		requires(Robot.hood);
		continuous = cont;
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

    	if(azimuth > Robot.shooterrotation.getangle() + ShooterRotation.rotateRange[1] - 0.4
    			|| azimuth < Robot.shooterrotation.getangle() + ShooterRotation.rotateRange[0] + 0.4
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
    			Robot.drivetrain.turnRight();
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
    	if(!continuous && Robot.Camera.isAimed()){
    		if(System.currentTimeMillis() - wasAimedAt > aimTime && aimed && Robot.shooterwheel.atSetpoint()){
    			return true;
    		}
    		if(!aimed){
    			aimed = true;
    			wasAimedAt = System.currentTimeMillis();
    		}
    	}else{
    		aimed = false;
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
