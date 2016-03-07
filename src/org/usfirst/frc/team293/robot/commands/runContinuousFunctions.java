package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.subsystems.Arduino;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class runContinuousFunctions extends Command {
	private static boolean hasBall = false;

    public runContinuousFunctions() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.continuousfunctions);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	requires(Robot.continuousfunctions);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shooterwheel.printShooter();
    	if(Robot.Camera.isAimed()){
    		Robot.ledManual.on();
    	}
    	Robot.shooterrotation.ledLight();
    	if(!Robot.feeder.boulderoptical()){
    		Robot.ledFeeder.on();
    		if(!hasBall){
    			Robot.continuousfunctions.setHasBall(true);
    		}
    	}else if(hasBall){
    		Robot.continuousfunctions.setHasBall(false);
    	}
    	if(Robot.continuousfunctions.isNewData()){
    		if(Robot.continuousfunctions.isShooterOn()){
    			if(Robot.continuousfunctions.isAiming()){
    				Robot.ledStrip.writeByte(Arduino.ShooterOnAiming);
    			}else if(Robot.continuousfunctions.isCanSeeSwag()){
    				Robot.ledStrip.writeByte(Arduino.ShooterOnCanSeeSwag);
    			}
    		}
    		if(Robot.continuousfunctions.isAutoBlue()){
    			Robot.ledStrip.writeByte(Arduino.AutoBlue);
    		}else{
    			Robot.ledStrip.writeByte(Arduino.AutoRed);
    		}
    		if(Robot.continuousfunctions.isTeleop() && !Robot.continuousfunctions.isHasBall()){
    			if(Robot.continuousfunctions.isShooterOn()){
    				Robot.ledStrip.writeByte(Arduino.TeleopNoBallShooteron);
    			}else{
    				Robot.ledStrip.writeByte(Arduino.TeleopNoBallShooteroff);
    			}
    		}
    		if(Robot.continuousfunctions.isAfterShooting()){
    			Robot.ledStrip.writeByte(Arduino.aftershooting);
    		}
    		if(Robot.continuousfunctions.isDrivetrainUp()){
    			Robot.ledStrip.writeByte(Arduino.DrivetrainUp);
    		}else{
    			Robot.ledStrip.writeByte(Arduino.DrivetrainDown);
    		}
    		if(Robot.continuousfunctions.isPARTY()){
    			Robot.ledStrip.writeByte(Arduino.PARTY);
    		}
    		Robot.continuousfunctions.setNewData(false);
    	}
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
