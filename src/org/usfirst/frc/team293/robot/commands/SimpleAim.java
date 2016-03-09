package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.subsystems.ShooterRotation;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SimpleAim extends Command {
	private double azimuth = 0.0;

    public SimpleAim() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	//requires(Robot.hood);
    	Robot.drivetrain.setPID(0.013, 0.001, 0.0);
		requires(Robot.shooterrotation);
		requires(Robot.drivetrain);
		requires(Robot.ledHighGoal);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shooterrotation.setangle(-Robot.Camera.getAzimuth()+Robot.shooterrotation.getangle());
    	Robot.Camera.shooterRotcompensation(true);
    	Robot.drivetrain.resetPID();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.Camera.canSeeSwagadelia()){
    		azimuth = Robot.Camera.getAzimuth();
    		Robot.shooterrotation.setangle(-azimuth+Robot.shooterrotation.getangle());
    	}
    	/*if(Robot.shooterrotation.getangle() >= ShooterRotation.rotateRange[1] - 0.5){
    		Robot.drivetrain.turnLeft();
    	}else if(Robot.shooterrotation.getangle() <= ShooterRotation.rotateRange[0] + 0.5){
    		Robot.drivetrain.turnRight();
    	}else{
    		Robot.drivetrain.stop();
    	}*/
    	if(azimuth > Robot.shooterrotation.getangle() + ShooterRotation.rotateRange[1] - 1.0 
    			|| azimuth < Robot.shooterrotation.getangle() - ShooterRotation.rotateRange[1] + 1.0 
    			&& Robot.drivetrain.returnAttitude()[0] != -1.0){
    		double absoluteAngle = azimuth + Robot.drivetrain.getAttitude()[0] - Robot.shooterrotation.getangle();
    		if(absoluteAngle > 360.0){
				absoluteAngle -= 360.0;
			}else if(absoluteAngle < 0.0){
				absoluteAngle += 360.0;
			}
    		SmartDashboard.putNumber("absolute Angle", absoluteAngle);
    		Robot.drivetrain.setSetpoint(absoluteAngle);
    		Robot.drivetrain.PID();
			Robot.drivetrain.turnToAngle();
    	}
    	Robot.Camera.shooterRotcompensation(false);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
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
