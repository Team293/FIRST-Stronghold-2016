package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;
//import org.usfirst.frc.team293.robot.subsystems.Arduino;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Fire extends Command {

    public Fire() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.feeder);
    	requires(Robot.ledHighGoal);

    	setTimeout(2);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.feeder.outsidefeederset(1);
    	Robot.feeder.insidefeederset(-1);
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
    	Robot.ledManual.off();
    	Robot.feeder.outsidefeederset(0);
    	Robot.feeder.insidefeederset(0);
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
