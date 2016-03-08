package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SimpleAim extends Command {
	private long lastRun = System.currentTimeMillis();

    public SimpleAim() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	//requires(Robot.hood);
		requires(Robot.shooterrotation);
		requires(Robot.drivetrain);
		requires(Robot.ledHighGoal);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	lastRun = System.currentTimeMillis();
    	Robot.shooterrotation.setangle(-Robot.Camera.getAzimuth()+Robot.shooterrotation.getangle());
    	Robot.Camera.shooterRotcompensation(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//if (Robot.Camera.whenstaringatSwagadelia()==true && System.currentTimeMillis() - lastRun > 1){
    		Robot.shooterrotation.setangle(-Robot.Camera.getAzimuth()+Robot.shooterrotation.getangle());
    		lastRun = System.currentTimeMillis();
    	//}
    	//Robot.Camera.shooterRotcompensation(false);
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
