package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.RobotMap;
import org.usfirst.frc.team293.robot.subsystems.Arduino;
import org.usfirst.frc.team293.robot.subsystems.Hood;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    	
    	SmartDashboard.putNumber("Azimuth IMU",Robot.drivetrain.getAttitude()[0]);


    	
    	//SmartDashboard.putNumber("angle vel", Robot.shooterrotation.getVel());
    	Robot.shooterwheel.printShooter();
    	SmartDashboard.putNumber("Camera Coord Distance", Robot.Camera.getDistance());
    	SmartDashboard.putNumber("Camera Coord Azimuth", Robot.Camera.getAzimuth());
    	SmartDashboard.putBoolean("Aimed Enough", Math.abs(Robot.Camera.getAzimuth()) < 0.6);
    	SmartDashboard.putNumber("ShooterAngle",Robot.shooterrotation.getangle());
    	//SmartDashboard.putNumber("timer", Robot.lifterdrivetrain.timer());
    	Robot.shooterwheel.printShooter();
       	//SmartDashboard.putNumber("Iaccum",Hood.getI());
      	SmartDashboard.putNumber("Hood Angle", Hood.getPosition());
    	SmartDashboard.putNumber("Error", 850-Hood.getPosition());
    	if(Robot.Camera.isAimed()){
    		Robot.ledManual.on();
    	}else{
    		Robot.ledManual.flash((int)(Robot.Camera.getAzimuth() * 30.0));
    		//Robot.ledManual.off();
    	}
    	if(Robot.continuousfunctions.isAiming()){
    		Robot.ledHighGoal.flash(RobotMap.flashnorm);
    	}else if(Robot.Camera.canSeeSwagadelia()){
    		Robot.ledHighGoal.off();
    	}
    	if(!Robot.Camera.canSeeSwagadelia()){
    		Robot.ledHighGoal.on();
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
