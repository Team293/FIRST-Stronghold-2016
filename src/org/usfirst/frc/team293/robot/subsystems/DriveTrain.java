package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;
import org.usfirst.frc.team293.robot.commands.TankDriveWithJoystick;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
	private SpeedController leftMotor, rightMotor, lifterMotor;    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public DriveTrain(){
		super();
		leftMotor=new VictorSP(RobotMap.leftMotor);
		rightMotor=new VictorSP(RobotMap.rightMotor);
		lifterMotor=new TalonSRX(RobotMap.lifterMotor);
		RobotDrive drive = new RobotDrive(leftMotor,rightMotor);
	}
    public void initDefaultCommand() {
    	setDefaultCommand(new TankDriveWithJoystick());
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

