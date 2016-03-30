package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.RobotMap;
import org.usfirst.frc.team293.robot.subsystems.LifterDriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DropPosition extends Command {
	//private boolean status=false;
	//private boolean pastlift=false;
	//private boolean point=false;

    public DropPosition() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	//this.up = up;
    	requires(Robot.lifterdrivetrain);
    	requires(Robot.ledCenterWheel);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    	Robot.lifterdrivetrain.lifterMotor.set(-.5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {	

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    
		return (LifterDriveTrain.lifterMotor.getAnalogInRaw()<70);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lifterdrivetrain.stopMotor();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.lifterdrivetrain.stopMotor();
    }
}
