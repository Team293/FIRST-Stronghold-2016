package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;
import org.usfirst.frc.team293.robot.Serial;
import org.usfirst.frc.team293.robot.commands.TankDriveWithJoystick;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends PIDSubsystem {
	private SpeedController leftMotor, rightMotor;
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	RobotDrive drive;

	PIDController autoDrivePID;
	Serial pi;// The IMU Data

	public DriveTrain() {
		super("PIDRobotDrive", .1, 0, 0);

		setAbsoluteTolerance(0.03);
		setSetpoint(0.0); // target
		autoDrivePID = getPIDController();
		autoDrivePID.setContinuous(false);

		
		leftMotor = new VictorSP(RobotMap.leftMotor);
		rightMotor = new VictorSP(RobotMap.rightMotor);
		drive = new RobotDrive(leftMotor, rightMotor);
		drive.setExpiration(0.1);
		drive.setSensitivity(1.0);

		pi = new Serial(Port.kOnboard);// rs232

	}

	public void initDefaultCommand() {
		setDefaultCommand(new TankDriveWithJoystick());
	}

	public void drive(double left, double right) {
		drive.tankDrive(left, right);
	}

	public void setPID(double p, double i, double d) {
		
	}

	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		String data= pi.getData();
		
		String[]tokens=data.split("=");
		double heading=(Double.parseDouble(tokens[1].split("R")[0]));//Heading
		SmartDashboard.putNumber("angle",heading);
		return heading;
			//return (Double.parseDouble(tokens[1].split("P")[0]));//Roll
		//return (Double.parseDouble(tokens[1].split("H")[0]));//Pitch
	}

	public void enablestraightdrive() {
		autoDrivePID.enable();
	}

	public void disablestraightdrive() {
		autoDrivePID.disable();
	}

	@Override
	protected void usePIDOutput(double output) {
		drive.drive(-.3, output);
		// TODO Auto-generated method stub
	}

}
