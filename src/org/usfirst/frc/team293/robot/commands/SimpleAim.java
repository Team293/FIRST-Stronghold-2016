package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.OI;
import org.usfirst.frc.team293.robot.Robot;
//import org.usfirst.frc.team293.robot.subsystems.Camera;
import org.usfirst.frc.team293.robot.subsystems.Hood;
import org.usfirst.frc.team293.robot.subsystems.ShooterRotation;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SimpleAim extends Command {
	private double azimuth = 0.0;
	private static final double angleError = 0.8;
	double[] Hoodangle={83,83,83,83,85,86,74,75,81,80,83,78,75,83};
	
	/////////////////////0  1  2  3  4  5  6  7  8  9 10 11 12 13
	private boolean continuous = false;
	private long wasAimedAt = -1100;
	private final long aimTime = 1000;
	private boolean aimed = false;
    public SimpleAim(boolean cont) {
    	//double[] Hoodangle= new double[11];
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	Robot.drivetrain.setPID(0.1, 0.0, 0.001);
		requires(Robot.shooterrotation);
		requires(Robot.drivetrain);
		requires(Robot.ledHighGoal);
		requires(Robot.hood);
		continuous = cont;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.initsensor();						//initialize values of IMU to -1
    	Robot.Camera.shooterRotcompensation(true);			//initialize camera servo compensation initial values
    	Robot.drivetrain.resetPID();						//reset Integral of drivetrain
    	SmartDashboard.putBoolean("Using Aiming Backup", false);
    	Robot.continuousfunctions.setAiming(true);
    	Hood.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.Camera.canSeeSwagadelia()){				//if robot can see goal
    		azimuth = Robot.Camera.getAzimuth();			//get goal yaw
    	if(Robot.drivetrain.IMUData()){
    		//get true angle of goal (relative to IMU angle)
    		double absoluteAngle = getGoalAbsoluteAngle();
    		//turn robot to point at goal
    		Robot.drivetrain.setSetpoint(absoluteAngle);
    		Robot.drivetrain.PID();
			Robot.drivetrain.turnToAngle();
			SmartDashboard.putBoolean("using Backup", false);
    	}else{
    	//if IMU is not working
    		if(azimuth < -angleError){
    			Robot.drivetrain.turnLeft();
    		}else if(azimuth > angleError){
    			Robot.drivetrain.turnRight();
    		}
    		SmartDashboard.putBoolean("using Backup", true);
    	}
    	//compensate for shooter rotation and drivetrain rotation
    	Robot.Camera.shooterRotcompensation(false);
    	
    	///////////////////////////Distance Stuff///////////////////////////////////
    	int distance=(((int) Robot.Camera.getDistance())/12);
    	if(distance < Robot.hood.Hoodangle.length){
    		Hood.setPosition(Hood.bottompoint-Robot.hood.Hoodangle[distance]);
    		SmartDashboard.putNumber("AUTO aim angle", (Hood.bottompoint-Robot.hood.Hoodangle[distance]));
    	}
    	SmartDashboard.putNumber("AUTO AIM DISTANCE FROM array", distance);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//STOP if joystick is mashed
    	if(Math.abs(OI.getJoystick1()) > 0.8 || Math.abs(OI.getJoystick2()) > 0.8){
    		return true;
    	}
    	//STOP if the aiming is done and it is auto-aim and shoot
    	if(!continuous && Robot.Camera.isAimed()){
    		if(System.currentTimeMillis() - wasAimedAt > aimTime && aimed && Robot.shooterwheel.atSetpoint()){
    			return true;
    		}
    		if(!aimed){
    			aimed = true;
    			wasAimedAt = System.currentTimeMillis();
    		}
    	}else{
    		aimed = false;
    	}
        return false;
    }
    
    public double getGoalAbsoluteAngle(){
    	double absAngle = azimuth + Robot.drivetrain.returnAttitude()[0] - Robot.shooterrotation.getangle();
    	//compensate for 0-360 degree discontinuity
		if(absAngle > 360.0){
			absAngle -= 360.0;
		}else if(absAngle < 0.0){
			absAngle += 360.0;
		}
		return absAngle;
    }
    
    private boolean IMUDrivetrainTurn(){
    	return ((azimuth > Robot.shooterrotation.distFromLeft() - 0.4
    	    			|| azimuth < Robot.shooterrotation.distFromRight() + 0.4)
    	    			&& Robot.drivetrain.returnAttitude()[0] != -1.0);
    }
    
    // Called once after isFinished returns true
    protected void end() {
    	Robot.continuousfunctions.setAiming(false);
    	new RotateShooter();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	 end();
    }
}
