package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterRotation extends Subsystem {//rotate the shooter and setup that PID
	private static CANTalon shooterrotation;
	private static AnalogPotentiometer pot;
	public static final double[] rotateRange = {-10.0,10.0};
	private static final double hoodLum = 2.0;							//Max angle inside hood
	private static final double wumbo = 15.0;							//Degrees per Second
	private static long lastTime = System.currentTimeMillis();
	
	private static final double centerTolerance = 0.5;
	
	private static double setpoint = 292;

	public ShooterRotation() {
		super();
		shooterrotation = new CANTalon(RobotMap.shooterRotation);
		shooterrotation.changeControlMode(TalonControlMode.Position);
		shooterrotation.setFeedbackDevice(FeedbackDevice.AnalogPot);
		shooterrotation.setPID(12, .00000001, 2);
		shooterrotation.enableControl(); // Enable PID control on the talon
		shooterrotation.setSetpoint(setpoint);
	}
	// Put methods for controlling this subsystem
	// here. Call these from Command

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void setsetpoint(double angle) {	//this can only happen if we are above 10 degrees

		shooterrotation.setSetpoint(angle);
	}
	public double getsetpoint(){
		return shooterrotation.getSetpoint();
	}
	
	public double getVel(){
		return shooterrotation.getAnalogInVelocity();
	}
	
	public static boolean isInHoodBounds(){
		return (Math.abs(getShooterAngle()) < hoodLum);
	}
	
	public boolean isAtAngle(){
		return (Math.abs(shooterrotation.getPosition()-shooterrotation.getSetpoint()) < 20);
	}
		
	public void turnToGoal(double angle){
		setangle((getangle() - angle));
		SmartDashboard.putNumber("shooter Angle", getangle());
	}
	
	public static double getShooterAngle(){
		return shooterrotation.getPosition();
	}
	public void setangle(double angle){	//ASSUMING 299 CENTER, AND 210 RIGHT-11.5 DEGREES, AND 395 +11.5 DEGREES
		angle = Math.min(rotateRange[1], Math.max(rotateRange[0], angle));
		shooterrotation.setSetpoint(angle*7.74+299.0);
	}
	
	public double getangle(){
		return (shooterrotation.getPosition() - 299.0) / 7.74;
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
