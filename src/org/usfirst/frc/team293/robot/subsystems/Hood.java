package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.RobotMap;
import org.usfirst.frc.team293.robot.commands.DefaultHoodResting;
import org.usfirst.frc.team293.robot.commands.HoodRestPosition;
import org.usfirst.frc.team293.robot.commands.ManualHood;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 *
 */
public class Hood extends Subsystem {//the hood that aims up and down and manipulates.  PID
	private static CANTalon Hood;
	public double[] Hoodangle={83,83,83,83,85,86,84,84,81,80,83,78,75,83};
	public static final int restPosition = 825,
			bottompoint=890;
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public Hood() {
		super();
		Hood = new CANTalon(RobotMap.hoodMotor);
		Hood.changeControlMode(TalonControlMode.Position);
		Hood.setFeedbackDevice(FeedbackDevice.AnalogPot);
		Hood.reverseSensor(false);
		Hood.setPID(15,.00075,500); // Set the PID constants (p, i, d);
		Hood.setIZone(50);
		//Hood.setCloseLoopRampRate(.0025);

		Hood.enableControl(); // Enable PID control on the talon
	}

	public void initDefaultCommand() {
		
		// Set the default command for a subsystem here.
		 setDefaultCommand(new ManualHood());
	}

	public static void setPosition(double i) {
		if (i>bottompoint){
			i=bottompoint;
		}
		if (i<bottompoint-350){
			i=bottompoint-350;
		}
		Hood.enableControl();
		Hood.setSetpoint(i);
		//if (Hood.GetIaccum()>.1){
		//Hood;
		//}
	}
	
	public static double getPosition(){
		return Hood.getPosition();
	}
	public static double getI(){
		return Hood.GetIaccum();
	}
	public static double getError(){
		return Hood.getError();
	}

	public static void setposition(double angle) {
		Hood.set(angle);
	}

	public static void disable() {
		Hood.disable();	
	}
	public static void enable(){
		Hood.enable();
	}
	
	public boolean isDown(){
		return Math.abs(getPosition()) >= (bottompoint-40);
	}

}
