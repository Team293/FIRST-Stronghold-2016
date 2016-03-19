package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SwitchPosition extends Command {
	private boolean status=false;
	private boolean pastlift=false;
	private boolean point=false;
    public SwitchPosition() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.lifterdrivetrain);
    	requires(Robot.ledCenterWheel);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	pastlift=Robot.lifterdrivetrain.issetUp();
    	Robot.ledCenterWheel.on();
    	point=false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {	
    	if(Robot.lifterdrivetrain.issetUp()==true){
    		Robot.lifterdrivetrain.drop();
    	}
    	if(Robot.lifterdrivetrain.issetUp()==false){
    		Robot.lifterdrivetrain.lift();
    		
    	}
    	Robot.ledCenterWheel.flash(RobotMap.flashnorm);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(pastlift==true){
    		point=(!Robot.lifterdrivetrain.issetUp());
    	}
    	if(pastlift==false){
    		point=Robot.lifterdrivetrain.issetUp();
    	}
    	return point;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
