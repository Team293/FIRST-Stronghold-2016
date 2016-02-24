package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterRotation extends Subsystem {//rotate the shooter and setup that PID
	private static CANTalon shooterrotation;
	private static AnalogPotentiometer pot;
	public static final double[] rotateRange = {-12.0,12.0};
	private static final double hoodLum = 2.0;							//Max angle inside hood
	private static final double wumbo = 5.0;							//Degrees per Second
	private static long lastTime = System.currentTimeMillis();
	
	private static final double centerTolerance = 0.5;
	
	private static double setpoint = 0.0;

	public ShooterRotation() {
		super();
		shooterrotation = new CANTalon(RobotMap.shooterRotation);
		shooterrotation.changeControlMode(TalonControlMode.Position);
		shooterrotation.setFeedbackDevice(FeedbackDevice.AnalogPot);
		shooterrotation.setPID(2, .001, 1);
		shooterrotation.enableControl(); // Enable PID control on the talon
	}
	// Put methods for controlling this subsystem
	// here. Call these from Command

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void setsetpoint(double angle) {	//this can only happen if we are above 10 degrees
		if(Math.abs(angle) > 2.0 && !Hood.hoodIsUp()){
			angle = 1.8 * angle / Math.abs(angle);
		}
		angle = Math.min(Math.max(angle,rotateRange[0]), rotateRange[1]);			//Constrain Angle Value
		setpoint = angle;
		shooterrotation.set(angle);
	}
	
	public static boolean isInHoodBounds(){
		return (Math.abs(getShooterAngle()) < hoodLum);
	}
	
	public void turn(boolean right){
		double Dt = (double)(System.currentTimeMillis() - lastTime) / 1000.0;
		lastTime = System.currentTimeMillis();
		if(Dt > .08){
			Dt = 0.02;
		}
		if(right){
			this.setsetpoint(setpoint + (wumbo * Dt));
		}else{
			this.setsetpoint(setpoint - (wumbo * Dt));
		}
	}
		
	public void turnToGoal(double angle){
		setsetpoint(getShooterAngle() + angle);
	}
	
	public static double getShooterAngle(){
		return shooterrotation.getPosition();
	}
	
	public void ledLight(){
		if(getShooterAngle() < centerTolerance){
			if(!Robot.ledRotateLeft.getStatus()){
				Robot.ledRotateLeft.on();
			}
			if(Robot.ledRotateRight.getStatus()){
				Robot.ledRotateRight.off();
			}
		}else if(getShooterAngle() > centerTolerance){
			if(!Robot.ledRotateRight.getStatus()){
				Robot.ledRotateRight.on();
			}
			if(Robot.ledRotateLeft.getStatus()){
				Robot.ledRotateLeft.off();
			}
		}else{
			if(!Robot.ledRotateRight.getStatus()){
				Robot.ledRotateRight.on();
			}
			if(!Robot.ledRotateLeft.getStatus()){
				Robot.ledRotateLeft.on();
			}
		}
	}
}
