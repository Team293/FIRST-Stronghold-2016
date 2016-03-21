package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.RobotMap;
//import org.usfirst.frc.team293.robot.subsystems.ShooterWheel;
import org.usfirst.frc.team293.robot.subsystems.Arduino;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RunShooterWheel extends Command {

    public RunShooterWheel() {
    	requires(Robot.shooterwheel);
    	requires(Robot.ledShooterWheels);
    	//requires(Robot.drivercamera);
    	
    	// Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shooterwheel.setShooterRPM();
    	Robot.drivercamera.Lookup();
    	Robot.ledShooterWheels.on();
    	Robot.continuousfunctions.setShooterOn(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.shooterwheel.atSpeed()){
    		Robot.ledShooterWheels.on();
    	}else{
    		Robot.ledShooterWheels.flash(RobotMap.flashnorm);
    	}
    	//SmartDashboard.putNumber("RPM", Robot.shooterwheel);
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
    	new StopShooterWheel();
    }
}
