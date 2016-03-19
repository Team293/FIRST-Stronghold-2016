package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


/**
 *
 */
public class LowGoal extends Command {

    public LowGoal() {
    	requires(Robot.feeder);
    	requires(Robot.ledLowGoal);
    	 setTimeout(1);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.feeder.insidefeederset(1);
    	Robot.feeder.outsidefeederset(-1);
    	Robot.ledLowGoal.on();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	  Robot.feeder.insidefeederset(0);
    	  Robot.feeder.outsidefeederset(0);
    	  Robot.ledLowGoal.off();
    }
    

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
