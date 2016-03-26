package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.subsystems.Hood;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ManualAim extends Command {
double azimuth;
    public ManualAim() {
    	requires(Robot.Camera);
    	requires(Robot.hood);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.Camera.canSeeSwagadelia()){				//if robot can see goal
    		azimuth = Robot.Camera.getAzimuth();
    		if(azimuth>.5){
    			
    		}
    		if(azimuth<-.5){
    			
    		}
    		else if(Robot.Camera.isAimed()){
    		
    		}
    		
    		
    		
    		
    		
   //////////////distancestuff 		
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
