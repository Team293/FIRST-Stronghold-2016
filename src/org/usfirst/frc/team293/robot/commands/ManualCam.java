package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.subsystems.LifterDriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualCam extends Command {

    public ManualCam() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.lifterdrivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	LifterDriveTrain.lifterMotor.set(.3);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//Robot.lifterdrivetrain.lifterMotor.set(0);
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	LifterDriveTrain.lifterMotor.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	LifterDriveTrain.lifterMotor.set(0);
    }
}
