package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class StartDriveStraight extends Command {
	
	private static final double Kp = 0.000004,
	Ki = 0.0,
	Kd = 0.0;
	public long time = 0;
	private long startTime = System.currentTimeMillis();

    public StartDriveStraight(long time) {
        // Use requires() here to declare subsystem dependencies
    	this.time = time;
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
    	startTime = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.getAttitude();
    	Robot.drivetrain.PID();
    	Robot.drivetrain.usePID();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (System.currentTimeMillis() - startTime > time);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
