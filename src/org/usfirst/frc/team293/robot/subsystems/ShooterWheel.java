package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterWheel extends Subsystem {
	CANTalon shooterwheel;
    double scalefactor=-1.66;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public ShooterWheel(){
		super();
	shooterwheel= new CANTalon(RobotMap.shooterwheel);
	shooterwheel.changeControlMode(TalonControlMode.Speed);//Change control mode of talon, default is PercentVbus (-1.0 to 1.0)
	shooterwheel.setFeedbackDevice(FeedbackDevice.QuadEncoder); //Set the feedback device that is hooked up to the talon
	shooterwheel.setPID(3, 0.001, 0.0); //Set the PID constants (p, i, d)
	shooterwheel.setF(.4);//what we think it should be 
	
	shooterwheel.enableControl(); //Enable PID control on the talon
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void setShooterRPM(int rpm){
    	shooterwheel.setSetpoint(rpm);
    	
    }
}

