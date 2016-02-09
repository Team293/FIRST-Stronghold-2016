package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;
import org.usfirst.frc.team293.robot.Serial;
import org.usfirst.frc.team293.robot.commands.TankDriveWithJoystick;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.SerialPort.Port;
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
	private CANTalon lifterMotor; // Put methods for controlling this subsystem
	// here. Call these from Commands.
	RobotDrive drive;
	Serial pi;// The IMU Data

	public DriveTrain() {
		super();
		leftMotor = new VictorSP(RobotMap.leftMotor);
		rightMotor = new VictorSP(RobotMap.rightMotor);
		drive = new RobotDrive(leftMotor, rightMotor);

		lifterMotor = new CANTalon(RobotMap.lifterMotor);
		// lifterMotor.changeControlMode(TalonControlMode.Position);//Change
		// control mode of talon, default is PercentVbus (-1.0 to 1.0)
		lifterMotor.setFeedbackDevice(FeedbackDevice.AnalogPot); 

		pi = new Serial(Port.kOnboard);// rs232

	}

	public void initDefaultCommand() {
		setDefaultCommand(new TankDriveWithJoystick());
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void drive(double left, double right) {
		drive.tankDrive(left, right);

	}

	public void liftdrivetrain() {
		lifterMotor.set(.5);
		if (lifterMotor.getAnalogInRaw() == 2.5) {
			lifterMotor.set(0);
		}
	}

	public void dropdrivetrain() {
		lifterMotor.set(-.5);
		if (lifterMotor.getAnalogInRaw() == 0) {
			lifterMotor.set(0);
		}
	}

	public void lift() {
		lifterMotor.set(1);
		if (lifterMotor.getAnalogInRaw() == 0) {
			lifterMotor.set(0);
		}
		// lifterMotor.get //probably should use this....
		// lifterMotor.set(0);//https://wpilib.screenstepslive.com/s/3120/m/7912/l/85776-analog-triggers
	}

	public void drivestraight() {// paste in PID stuff here
		pi.getData();
	}

}
