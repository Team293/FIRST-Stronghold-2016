package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class FollowGoal extends Command {
	private double[] xGainsFollow, yGainsFollow;
	private static long lastTime = System.currentTimeMillis();
	private static final int Dt = 50;
	private static boolean lost = false;
	private static long timeLost = 0;
	private static final int timeToLost = 1200;
	
    public FollowGoal(double[] xGains,double[] yGains) {
        // Use requires() here to declare subsystem dependencies
    	xGainsFollow = xGains;
    	yGainsFollow = yGains;
    	requires(Robot.Camera);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.Camera.initializePID(xGainsFollow,yGainsFollow);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	int newData = Robot.Camera.getGoalCoordinates();
    	if(newData == -1 && !lost){
    		lost = true;
    		timeLost = System.currentTimeMillis();
    		Robot.continuousfunctions.setCanSeeSwag(false);
    	}
    	if(newData == 1 || (System.currentTimeMillis() - lastTime > Dt && !lost)){
    		if(lost && newData == 1){
    			Robot.continuousfunctions.setCanSeeSwag(true);
    		}
    		Robot.Camera.updatePID(newData);
    		Robot.Camera.setServos();
    		SmartDashboard.putNumber("Azimuth", Robot.Camera.getAzimuth());
        	SmartDashboard.putNumber("Distance", Robot.Camera.getDistance());
        	lost = false;
    		lastTime = System.currentTimeMillis();
    	}else if(lost && System.currentTimeMillis() - lastTime > Dt && System.currentTimeMillis() - timeLost > timeToLost){
    		Robot.Camera.search();
    		Robot.Camera.setServos();
    		lastTime = System.currentTimeMillis();
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
