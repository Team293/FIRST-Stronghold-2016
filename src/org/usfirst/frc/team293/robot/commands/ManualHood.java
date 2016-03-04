package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.OI;
import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.subsystems.Hood;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ManualHood extends Command {

    public ManualHood() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.hood);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	Hood.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Hood.setPosition(850);
    	SmartDashboard.putNumber("Iaccum",Hood.getI());
  
    	SmartDashboard.putNumber("angle", Hood.getPosition());
    	//SmartDashboard.putNumber("anglesetpoint");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
