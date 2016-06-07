package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.subsystems.Hood;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class driverAim extends Command {

    public driverAim() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.Camera.shooterRotcompensation(true);			//initialize camera servo compensation initial values
    	Robot.continuousfunctions.setAiming(true);
    	Hood.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.Camera.shooterRotcompensation(false);
    	if (Robot.Camera.canSeeSwagadelia()){
    		int distance=(((int) Robot.Camera.getDistance())/12);
    		if(distance < Robot.hood.Hoodangle.length){
    			Hood.setPosition(Hood.bottompoint-Robot.hood.Hoodangle[distance]);
    			SmartDashboard.putNumber("AUTO aim angle", (Hood.bottompoint-Robot.hood.Hoodangle[distance]));
    		}
    		SmartDashboard.putNumber("AUTO AIM DISTANCE FROM array", distance);
    		
    		
   
    		
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.continuousfunctions.setAiming(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
