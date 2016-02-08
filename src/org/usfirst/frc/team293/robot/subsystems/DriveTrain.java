package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;
import org.usfirst.frc.team293.robot.commands.TankDriveWithJoystick;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
	private SpeedController leftMotor, rightMotor; 
	private CANTalon lifterMotor;    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	RobotDrive drive;
	public DriveTrain(){
		super();
		leftMotor=new VictorSP(RobotMap.leftMotor);
		rightMotor=new VictorSP(RobotMap.rightMotor);
		drive = new RobotDrive(leftMotor,rightMotor);
		
		lifterMotor=new CANTalon(RobotMap.lifterMotor);
		lifterMotor.changeControlMode(TalonControlMode.Position);//Change control mode of talon, default is PercentVbus (-1.0 to 1.0)
    	lifterMotor.setFeedbackDevice(FeedbackDevice.AnalogPot); //Set the feedback device that is hooked up to the talon
    	lifterMotor.setPID(3, 0.001, 0.0); //Set the PID constants (p, i, d)
    	//shooterwheel.setF(.4);//what we think it should be 
		
		
	}
    public void initDefaultCommand() {
    	setDefaultCommand(new TankDriveWithJoystick());
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
	public void drive(double left, double right) {
		drive.tankDrive(left, right);
	}
	public void setdrivetrain(int setpoint){
		lifterMotor.setSetpoint(setpoint);
	}
	
	

	
}

