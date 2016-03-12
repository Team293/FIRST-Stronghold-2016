package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Climb extends Command {

    public Climb() {//this is going to lift up from the center wheel
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	//requires(Robot.lifterdrivetrain);
    	requires(Robot.ledClimb);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.ledClimb.on();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.ledClimb.flash(RobotMap.flashnorm);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.ledClimb.off();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
