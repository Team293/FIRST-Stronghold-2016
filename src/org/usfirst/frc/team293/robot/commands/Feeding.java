package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.RobotMap;
import org.usfirst.frc.team293.robot.subsystems.Hood;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Feeding extends Command {

    public Feeding() {
    	requires(Robot.feeder);
    	requires(Robot.ledFeeder);
    	requires(Robot.drivercamera);
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// shooterrotation.setangle(0);
    	Robot.feeder.outsidefeederset(1);
    	Robot.feeder.insidefeederset(-.5);
    	Robot.ledFeeder.on();
    	Hood.setPosition(768);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.ledFeeder.flash(RobotMap.flashnorm);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//return (isTimedOut());
    	return (!Robot.feeder.boulderoptical());
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.feeder.outsidefeederset(0);
    	Robot.feeder.insidefeederset(0);
    	Robot.drivercamera.Lookup();
    	Robot.ledFeeder.off();
    	//new RotateShooter();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
