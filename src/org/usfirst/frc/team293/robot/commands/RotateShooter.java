package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RotateShooter extends Command {
	boolean direction = true;
    public RotateShooter(boolean right) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooterrotation);
    	this.direction = right;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (direction==true){
    	Robot.shooterrotation.setsetpoint(Robot.shooterrotation.getsetpoint()-5);
    	}
    	if (direction==false){
    		Robot.shooterrotation.setsetpoint(Robot.shooterrotation.getsetpoint()+5);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//SmartDashboard.putNumber("azimuthsetpoint", Robot.shooterrotation.getsetpoint());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
 