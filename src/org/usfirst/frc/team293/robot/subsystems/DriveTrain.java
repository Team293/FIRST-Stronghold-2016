package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;
import org.usfirst.frc.team293.robot.Serial;
import org.usfirst.frc.team293.robot.commands.TankDriveWithJoystick;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {		//this does the TankDrive as well as the drivestraight for autonomous
	private SpeedController leftMotor, rightMotor;
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	RobotDrive drive;
	boolean driving = false;
	double processedangle;
	Serial pi;// The IMU Data
	
	private double[] attitude = {-1.0,-1.0,-1.0};
	
	/*   PID VARIABLES       */
	private double setpoint = 0.0;
	private double[] PIDGains = {0.0,0.0,0.0};
	double error = 0.0;
	double lastError = 0.0;
	double integral = 57.0;
	double lastTime = System.currentTimeMillis();
	boolean newData = false;
	double output = 0.0;
	
	public DriveTrain(double setpointWanted){
		leftMotor = new VictorSP(RobotMap.leftMotor);
		rightMotor = new VictorSP(RobotMap.rightMotor);
		drive = new RobotDrive(leftMotor, rightMotor);
		drive.setExpiration(0.1);
		drive.setSensitivity(1.0);
		//drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);

		pi = new Serial(Port.kOnboard,115200);// rs232
		
		this.setpoint = setpointWanted;
		
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new TankDriveWithJoystick());	
    }
    
    public void setSetpoint(double setpointWanted){
    	this.setpoint = setpointWanted;
    }
    
    public void resetPID(){
    	error = 0.0;
    	lastError = 0.0;
    	integral = 57.0;
    	lastTime = System.currentTimeMillis();
    }
    
    public void drive(double speed, double angle) {
		//drive.tankDrive(left, right);
		drive.arcadeDrive(speed, angle);
	}
    
    public void tankdrive(double left, double right){
		drive.tankDrive(left, right);
	}

	public void setPID(double p, double i, double d) {
		PIDGains[0] = p;
		PIDGains[1] = i;
		PIDGains[2] = d;
	}
	
	public void initsensor(){
		for(int i = 0;i < 3;i++){
			attitude[i] = -1.0;
		}
	}
	
	public double[] getAttitude() {
		// TODO Auto-generated method stub
		String data= pi.getData();
		if(data != "null"){
			String[]tokens=data.split(",");
			if(tokens.length == 3){
				for(int i = 0;i < 3;i++){
					attitude[i]=(Double.parseDouble(tokens[i]));
				}
			}
			newData = true;
		}else{
			newData = false;
		}
		return attitude;
			//return (Double.parseDouble(tokens[1].split("P")[0]));//Roll
		//return (Double.parseDouble(tokens[1].split("H")[0]));//Pitch
	}
	
	public double[] returnAttitude(){
		return attitude;
	}
	
	public void PID(){
		double Dt = (double)(System.currentTimeMillis() - lastTime) / 1000.0;
		lastTime = System.currentTimeMillis();
		if(newData){
			lastError = error;
		}
		double error = setpoint - attitude[0];
		if(error > 180){
			error-=360;
		}else if(error < -180){
			error+=360;
		}
		SmartDashboard.putNumber("attitude",attitude[0]);
		integral += error*Dt;
		integral = Math.min(Math.max(integral,-80.0), 80.0);
		double derivative = 0.0;
		if(Dt < 0.400){
			derivative = (error - lastError) / Dt;
		}
		output = PIDGains[0] * error + PIDGains[1] * integral + PIDGains[2] * derivative;
		SmartDashboard.putNumber("Error", error);
		SmartDashboard.putNumber("Integral", integral);
		SmartDashboard.putNumber("Derivative", derivative);
		SmartDashboard.putNumber("output", output);
	}
	
	public void usePID() {		//Method for Driving
		if(attitude[0] != -1.0){
			if(!driving){
				this.setSetpoint(attitude[0]);
				SmartDashboard.putNumber("setpoint", attitude[0]);
				driving = true;
			}
			drive.drive(0.55, output);
		}else{
			driving = false;
		}
		// TODO Auto-generated method stub
	}

	public void turnToAngle() {			//Turning.  One wheel is set at the zero and the other is tasked at spinning.  Might not be the best, maybe they need opposite outputs......
		drive.drive(0.0, output);
		// TODO Auto-generated method stub
		
	}
}

