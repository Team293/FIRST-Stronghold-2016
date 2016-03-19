package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.RobotMap;
import org.usfirst.frc.team293.robot.Serial;
import org.usfirst.frc.team293.robot.commands.TankDriveWithJoystick;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
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
	Encoder[] encoders = new Encoder[2];
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	RobotDrive drive;
	boolean driving = false;
	double processedangle;
	Serial pi;// The IMU Data
	
	private double[] attitude = {-1.0,-1.0,-1.0,-1000.0};		//yaw, roll, pitch, z rate
	
	/*   PID VARIABLES       */
	private double setpoint = 0.0;
	private double[] PIDGains = {0.0,0.0,0.0};
	private double integral = 0.0;
	private double[] integralRange = {-450.0,450.0};
	private double lastTime = System.currentTimeMillis();
	public boolean newData = false;
	private static double output = 0.0;
	
	//encoder variables
	private double error = 0.0;
	private double lastDist = 0.0;
	private double correctedDist = 0.0;
	private static final int[][] encoderPorts = {{6,7},{8,9}};
	double setpointWanted;
	
	public DriveTrain(){
		leftMotor = new VictorSP(RobotMap.leftMotor);
		rightMotor = new VictorSP(RobotMap.rightMotor);
		drive = new RobotDrive(leftMotor, rightMotor);
		drive.setExpiration(0.1);
		drive.setSensitivity(1.0);
		
		for(int i = 0;i < 2;i++){
			encoders[i] = new Encoder(encoderPorts[i][0],encoderPorts[i][1],true,EncodingType.k4X);
			encoders[i].setMaxPeriod(0.1);
			encoders[i].setMinRate(10);
			encoders[i].setDistancePerPulse(0.09817477042);
			encoders[i].setSamplesToAverage(5);
		}
		
		pi = new Serial(Port.kOnboard,115200);// rs232
		
		this.setpoint = setpointWanted;
		
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new TankDriveWithJoystick());	
    }
    
    public void setSetpoint(double setpointWanted){
    	this.setpoint = setpointWanted;
    }
    
    public void resetPID(){							//resets PID variables
    	integral = 0.0;
    	lastTime = System.currentTimeMillis();
    }
    
    public void drive(double speed, double angle) {
		drive.arcadeDrive(speed, angle);
	}
    
    public void tankdrive(double left, double right){
		drive.tankDrive(left, right);
	}

	public void setPID(double p, double i, double d) {		//sets P, I, and D
		PIDGains[0] = p;
		PIDGains[1] = i;
		PIDGains[2] = d;
	}
	
	public void initsensor(){								//initialize IMU values
		for(int i = 0;i < 3;i++){
			attitude[i] = -1.0;
		}
		attitude[3] = -100.0;
	}
	
	public double[] getAttitude() {
		// TODO Auto-generated method stub
		String data= pi.getData();
		if(data != "null"){
			String[]tokens=data.split(",");									//split at delimiters
			if(tokens.length == 4){											//make sure we have the right amount of data
				for(int i = 0;i < 4;i++){
					attitude[i]=(Double.parseDouble(tokens[i]));			//get angles
				}
			}
			newData = true;
		}else{
			newData = false;
		}
		return attitude;
	}
	
	public double[] returnAttitude(){
		return attitude;
	}
	
	public void PID(){
		double Dt = (double)(System.currentTimeMillis() - lastTime) / 1000.0;			//Dt, For calc stuff!!!
		lastTime = System.currentTimeMillis();
		error = setpoint - attitude[0];
		//compensate for discontinuity
		if(error > 180){
			error-=360;
		}else if(error < -180){
			error+=360;
		}
		integral += error*Dt;
		integral = Math.min(Math.max(integral,integralRange[0]), integralRange[1]);		//constrain
		double derivative = attitude[3];										//derivative straight from gyro
		output = PIDGains[0] * error + PIDGains[1] * integral + PIDGains[2] * derivative;
		//SmartDashboard.putNumber("Drivetrain Error", error);
		//SmartDashboard.putNumber("Drivetrain Integral", integral);
		//SmartDashboard.putNumber("Drivetrain Derivative", derivative);
	}
	
	public void usePID() {											//Method for Driving
		if(attitude[0] != -1.0){									//Don't drive without IMU data
			if(!driving){
				this.setSetpoint(attitude[0]);						//set the setpoint to the first value from the IMU
				SmartDashboard.putNumber("setpoint", attitude[0]);
				driving = true;
			}
			//drive.drive(-0.5, output);
			
			drive.arcadeDrive(-0.5, -output);
		}else{
			driving = false;
		}
		// TODO Auto-generated method stub
	}

	public void turnToAngle() {			//Turning, speed is set to 0, output is set for turning
		//constrain max turn speed
		if(output > 0){
			output = Math.min(output, 0.4);
		}else{
			output = Math.max(output, -0.4);
		}
		//constrain min turn speed
		if(output > 0){
			output = Math.max(output, 0.05);
		}else{
			output = Math.min(output, -0.05);
		}
		//turn
		drive.arcadeDrive(0.0, -output);
	}
	
	public void turnLeft(){
		drive.arcadeDrive(0.0,0.07);
	}
	
	public void turnRight(){
		drive.arcadeDrive(0.0,-0.07);
	}
	
	public void stop(){
		drive.arcadeDrive(0.0,0.0);
	}
	
	public void resetEnc(){
		encoders[0].reset();
		encoders[1].reset();
		lastDist = 0.0;
	}
	
	public double getDist(){
		return (encoders[0].getDistance() + encoders[1].getDistance()) / 2.0;
	}
	
	public double getCorrectedDistance(){
		return correctedDist;
	}
	
	public void updateCorrectedDistance(){
		double dist = this.getDist();
		correctedDist += (dist - lastDist) * Math.cos(Math.toRadians(error));
		lastDist = dist;
	}
	
}

