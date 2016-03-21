package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SwitchPosition extends Command {
	//private boolean status=false;
	//private boolean pastlift=false;
	//private boolean point=false;
	boolean up = true;
    public SwitchPosition() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.lifterdrivetrain);
    	requires(Robot.ledCenterWheel);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.ledCenterWheel.on();
    	Robot.lifterdrivetrain.position();
    	up = Robot.lifterdrivetrain.change();
    	SmartDashboard.putBoolean("going up", up);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {	
    	if(!up){
    		Robot.lifterdrivetrain.drop();
    	}
    	if(up){
    		Robot.lifterdrivetrain.lift();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	SmartDashboard.putNumber("Cam Pos", Robot.lifterdrivetrain.position());
    	if(up){
    		return Robot.lifterdrivetrain.position() == 1;					//is up
    	}else{
    		return Robot.lifterdrivetrain.position() == -1;					//is down
    	}
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
