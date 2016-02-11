package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.Serial;
import org.usfirst.frc.team293.robot.commands.FollowGoal;


import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.Servo;
/**
 *
 */
public class Camera extends Subsystem {//Danny's vision stuff
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	/*                         SWAGADELIC MOUNT STUFF                              */
    public long lastServoSet = System.currentTimeMillis();
	
	private int[] servoPins = new int[2];
	Servo cameraServos[] = new Servo[2];
	
	private static double xRange[] = {0.0, 1.0};
	private double yRange[] = {0.15, 0.62};
	
    private static double cameraHeight = 55.0;														//cm
    private static double goalHeight = 90.0;
    static double calibrationDist = 100.0;
    static double calibrationAngle = 0.4975;
    static double baseY = Math.toDegrees(Math.atan((goalHeight - cameraHeight)/calibrationDist)) + 170.0*calibrationAngle;
    
    public static double[] servoAngles = {(xRange[0] + xRange[1]) / 2.0, 0.5};				//initial Servo angles (0.0-1.0 scale)
    
    /*                         SWAGADELIC CAMERA STUFF                              */
    
    long lastReading = System.currentTimeMillis();
    long lastPIDUpdate = System.currentTimeMillis();
	
	Serial raspberryPi;
	private double goalCenter[] = new double[2];
	public double goalCoordinates[] = new double[2];
	
	private double PIDGains[][] = {{0.0,0.0,0.0},{0.0,0.0,0.0}};
	private double error[] = {0.0,0.0};
	private double lastError[] = {0.0,0.0};
	private double integralError[] = {0.0,0.0};
	
	
	public Camera(double[] xGains,double[] yGains,double[] screenCenter,int xServoPin,int yServoPin,Port port){
		raspberryPi = new Serial(port);
		this.initializePID(xGains,yGains);
    	goalCenter = screenCenter;
    	servoPins[0] = xServoPin;
    	servoPins[1] = yServoPin;
    	for(int i = 0;i < 2;i++){
    		cameraServos[i] = new Servo(servoPins[i]);
    		goalCoordinates[i] = goalCenter[i];
    	}
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new FollowGoal(PIDGains[0],PIDGains[1]));
    }
    
    public boolean getGoalCoordinates(){
    	String x = raspberryPi.getData();
    	if(x != "null"){
    		System.out.println(x);
    		String delims = "[ ]";
    		String[] vals = x.split(delims);
    		for(int i = 0;i < 2;i++){
    			try{
    				goalCoordinates[i] = Integer.parseInt(vals[i]);
    			}
    			catch(NumberFormatException nfe){
    				System.out.println("NumberFormatException: " + nfe.getMessage());
    			}
    		}
    		lastReading = System.currentTimeMillis();
    		return true;
    	}
    	return false;
    }
    
    public void initializePID(double[] xGains,double[] yGains){
    	for(int i = 0;i < 3;i++){
    		PIDGains[0][i] = xGains[i];
    	}
    	for(int i = 0;i < 3;i++){
    		PIDGains[1][i] = yGains[i];
    	}
    }
    
    public void updatePID(boolean newData){
    	double Dt = (double)(System.currentTimeMillis() - lastPIDUpdate) / 1000.0;
    	double[] outputs = {0.0,0.0};
    	for(int i = 0;i < 2;i++){
    		if(newData){
    			lastError[i] = error[i];
    		}
    		error[i] = goalCoordinates[i] - goalCenter[i];
    		double derivative = 0.0;
    		if(Dt < 0.400){
    			derivative = (error[i] - lastError[i]) / Dt;
    		}
    		integralError[i] += error[i];
    		integralError[i] = Math.min(Math.max(integralError[i],-1000.0), 1000.0);
    		if(i == 1){
    			/*SmartDashboard.putNumber("Center X", goalCoordinates[0]);
        		SmartDashboard.putNumber("Center Y", goalCoordinates[1]);
        		SmartDashboard.putNumber("goalCenter X", goalCenter[0]);
        		SmartDashboard.putNumber("goalCenter Y", goalCenter[1]);
    			SmartDashboard.putNumber("Error Y", error[1]);
    			SmartDashboard.putNumber("outputValue Y", outputs[1]);*/
    		}
    		outputs[i] = servoAngles[i] + PIDGains[i][0] * error[i] + PIDGains[i][1] * derivative + PIDGains[i][2] * integralError[i];
    	}
    	this.setServoValues(outputs);
    	lastPIDUpdate = System.currentTimeMillis();
    }
    
    public void setServoValues(double[] vals){
    	vals[0] = Math.min(Math.max(vals[0],xRange[0]), xRange[1]);					//Constrain Servo Values
    	vals[1] = Math.min(Math.max(vals[1],yRange[0]), yRange[1]);
    	SmartDashboard.putNumber("yAngle", servoAngles[1]);
    	for(int i = 0;i < 2;i++){
    		servoAngles[i] = vals[i];												//Set Servo values
    	}
    }
    
    public void setServos(){
    	for(int i = 0;i < 2;i++){
    		//SmartDashboard.putNumber("servoValue", servoAngles[0]);
    		cameraServos[i].set(servoAngles[i]);											//Set Servos
    	}
    	lastServoSet = System.currentTimeMillis();
    }
    
    public static double getDistance(){
    	double rad = Math.toRadians(baseY - 170.0*servoAngles[1]);
    	return ((goalHeight - cameraHeight) / Math.tan(rad));
    }
    
    public static double getAzimuth(){
    	return (170.0*servoAngles[0] - 90.0);
    }
}

