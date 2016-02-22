package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.RobotMap;
import org.usfirst.frc.team293.robot.commands.HoodRestPosition;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Hood extends Subsystem {//the hood that aims up and down and manipulates.  PID
	private static CANTalon Hood;
	
	private static final double UP = 10.0;
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public Hood() {
		super();
		Hood = new CANTalon(RobotMap.hoodMotor);
		Hood.changeControlMode(TalonControlMode.Position);
		Hood.setFeedbackDevice(FeedbackDevice.AnalogPot);
		Hood.setPID(3, 0.001, 0.0); // Set the PID constants (p, i, d)

		Hood.enableControl(); // Enable PID control on the talon
	}

	public void initDefaultCommand() {
		
		// Set the default command for a subsystem here.
		 setDefaultCommand(new HoodRestPosition());
	}

	public static void setPosition(double i) {
		if (ShooterRotation.isInHoodBounds() && i < UP) {
			i = UP;
		}
		Hood.setSetpoint(i);		
	}
	
	public static double getPosition(){
		return Hood.getPosition();
	}
	
	public static boolean hoodIsUp(){
		if(getPosition() >= 9.9){
			return true;
		}
		return false;
	}

	public static void disable() {
		Hood.disable();	
	}
	public static void enable(){
		Hood.enable();
	}

}
