package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;
import org.usfirst.frc.team293.robot.Serial;
import org.usfirst.frc.team293.robot.commands.StartDriveStraight;
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
public class DriveTrain extends PIDSubsystem {//drivetrain PID IMU NEEDS HELP
	private SpeedController leftMotor, rightMotor;
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	RobotDrive drive;
	boolean driving = false;
	double processedangle;
	PIDController autoDrivePID;
	Serial pi;// The IMU Data
	
	private double[] attitude = {-1.0,-1.0,-1.0};

	public DriveTrain() {
		super("PIDRobotDrive", .1, 0, 0);

		//setAbsoluteTolerance(0.03);
		setSetpoint(0.0); // target
		autoDrivePID = getPIDController();
		autoDrivePID.setContinuous(false);
		
		
		leftMotor = new VictorSP(RobotMap.leftMotor);
		rightMotor = new VictorSP(RobotMap.rightMotor);
		drive = new RobotDrive(leftMotor, rightMotor);
		drive.setExpiration(0.1);
		drive.setSensitivity(0.05);
		//drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);

		pi = new Serial(Port.kOnboard,115200);// rs232

	}

	public void initDefaultCommand() {
		setDefaultCommand(new TankDriveWithJoystick());	
	}

	public void drive(double left, double right) {
		//drive.tankDrive(left, right);
		drive.arcadeDrive(left, right);
	}
	public void tankdrive(double left, double right){
		drive.tankDrive(left, right);
	}

	public void setPID(double p, double i, double d) {
		
	}
	public void initsensor(){
		for(int i = 0;i < 3;i++){
			attitude[i] = -1.0;
		}
	}

	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		String data= pi.getData();
		if(data != "null"){
			String[]tokens=data.split(",");
			if(tokens.length == 3){
				for(int i = 0;i < 3;i++){
					attitude[i]=(Double.parseDouble(tokens[0]));
					System.out.println(tokens);

				}
			}
			processedangle=attitude[0];
			if(this.getSetpoint() - processedangle > 180){
				processedangle+=360;
			}else if(this.getSetpoint() - processedangle < -180){
				processedangle-=360;
			}
			//while (attitude[0]<=-180) processedangle+=360;
			//while (attitude[0]>180) processedangle-=360;
			SmartDashboard.putNumber("angle",processedangle);
			
			
		}else{
			processedangle = attitude[0];
		}
		SmartDashboard.putNumber("attitude",attitude[0]);
		return processedangle;
			//return (Double.parseDouble(tokens[1].split("P")[0]));//Roll
		//return (Double.parseDouble(tokens[1].split("H")[0]));//Pitch
	}

	public double getAttitude(){
		return attitude[0];
	}
	
	public void turnToAngle(double angle){
		drive.arcadeDrive(0.0, angle*0.1);
	}

	@Override
	protected void usePIDOutput(double output) {
		if(attitude[0] != -1.0){
			if(!driving){
				this.setSetpoint(attitude[0]);
				SmartDashboard.putNumber("setpoint", attitude[0]);
				driving = true;
			}
			drive.drive(0.35, output);
		}else{
			driving = false;
		}
		// TODO Auto-generated method stub
	}

}
