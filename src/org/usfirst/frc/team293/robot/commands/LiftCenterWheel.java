package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftCenterWheel extends Command {		//Toggles the Drivetrain Center Wheel up and down with a button press
boolean status;
    public LiftCenterWheel() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.lifterdrivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.lifterdrivetrain.position=false){//if its at the bottom point
    	Robot.lifterdrivetrain.startliftdrivetrain();//start lifting
    	}
    	if(Robot.lifterdrivetrain.position=true){//if its at the top point
    	Robot.lifterdrivetrain.startdropdrivetrain();//start dropping
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Robot.lifterdrivetrain.position=false){//if it starts at the bottom
        status=(Robot.lifterdrivetrain.stopliftdrivetrain());//return when it hits the top
    	}
    	if(Robot.lifterdrivetrain.position=true){//if it starts at the top
            status= (Robot.lifterdrivetrain.stopdropdrivetrain());//return when it hits the bottom
    	}
    	return status;
    }

    // Called once after isFinished returns true
    protected void end() {
    	if(Robot.lifterdrivetrain.position=true){	//flip the status, if it started up now its down.  If it started down, now its up
    		Robot.lifterdrivetrain.position=false;
    	}
    	if(Robot.lifterdrivetrain.position=false){
    		Robot.lifterdrivetrain.position=true;
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
