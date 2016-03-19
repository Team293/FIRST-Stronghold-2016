package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class StartDriveStraight extends Command {
	
	private static final double Kp = 0.00004,
	Ki = 0.0,
	Kd = 0.0;
	public double dist = 0.0;

    public StartDriveStraight(double distance) {
        // Use requires() here to declare subsystem dependencies
    	this.dist = distance;
    	requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.initsensor();
    	//Robot.drivetrain.resetEnc();
    	Robot.drivetrain.setSetpoint(Robot.drivetrain.getAttitude()[0]);
    	SmartDashboard.putNumber("setpoint", Robot.drivetrain.getAttitude()[0]);
    	Robot.drivetrain.setPID(Kp, Ki, Kd);
    	Robot.drivetrain.resetPID();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.getAttitude();
    	Robot.drivetrain.PID();
    	Robot.drivetrain.usePID();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	/*if(Robot.drivetrain.getDist() >= dist){
    		return true;
    	}*/
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
