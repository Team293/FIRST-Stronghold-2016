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
	boolean done = true;
	int previousPosition;
    public SwitchPosition() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.lifterdrivetrain);
    	requires(Robot.ledCenterWheel);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lifterdrivetrain.finished=false;
    	done=false;
    	previousPosition=Robot.lifterdrivetrain.isattop();

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {	
    	if(previousPosition==2){
    		Robot.lifterdrivetrain.drop();
    	}
    	if(previousPosition==0){
    		Robot.lifterdrivetrain.lift();
    	}
    	if(previousPosition==1){
    		Robot.lifterdrivetrain.lift();
    	}

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(previousPosition==2){
    		done=Robot.lifterdrivetrain.drop();		//put this in execute ^^^^
    	}
    	if(previousPosition==0){
    		done=Robot.lifterdrivetrain.lift();
    	}
    	if(previousPosition==1){
    		done=Robot.lifterdrivetrain.lift();
    	}
		return done;
 
    }

    // Called once after isFinished returns true
    protected void end() {

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.lifterdrivetrain.stopMotor();
    }
}
