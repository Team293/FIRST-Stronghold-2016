package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.RobotMap;
import org.usfirst.frc.team293.robot.subsystems.LifterDriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CenterWheelDrop extends Command {

    public CenterWheelDrop() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
      	requires(Robot.lifterdrivetrain);
    	requires(Robot.ledCenterWheel);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
      	//Robot.lifterdrivetrain.timer.start();
    	Robot.ledCenterWheel.on();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Robot.lifterdrivetrain.drop();
    	Robot.ledCenterWheel.flash(RobotMap.flashnorm);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return true;
       // return !Robot.lifterdrivetrain.issetUp();
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Robot.lifterdrivetrain.timer.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	LifterDriveTrain.lifterMotor.set(0);
    }
}
