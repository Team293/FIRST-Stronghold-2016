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
public class Camera extends Subsystem {			//This manages the OpenCV camera by getting its coordinate values and outputting to the servos
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	/*                         SWAGADELIC MOUNT STUFF                              */
    public long lastServoSet = System.currentTimeMillis();
	
	private int[] servoPins = new int[2];
	Servo cameraServos[] = new Servo[2];
	
	private static double servoRange[][] = {{0.0, 0.89},{0.19, 0.555}};
	private static double servoRangeSearch[][] = {{0.0, 0.89},{0.4, 0.53}};
	
    private static double cameraHeight = 42.5;														//cm
    private static double goalHeight = 90.0;
    static double calibrationDist = 100.0;
    static double calibrationAngle = 0.435;
    static double baseY = Math.toDegrees(Math.atan((goalHeight - cameraHeight)/calibrationDist)) + 170.0*calibrationAngle;
    
    private double inc[] = {0.026,0.015};
    
    public double[] servoAngles = {(servoRange[0][0] + servoRange[0][1]) / 2.0, 0.5};				//initial Servo angles (0.0-1.0 scale)
    
    /*                         SWAGADELIC CAMERA STUFF                              */
    
    long lastReading = System.currentTimeMillis();
    long lastPIDUpdate = System.currentTimeMillis();
	
	Serial raspberryPi;
	private double goalCenter[] = {160.0,140.0};
	public double goalCoordinates[] = new double[2];
	
	private double PIDGains[][] = {{0.025/goalCenter[0],0.0,0.0008/goalCenter[0]},
			{0.0165/goalCenter[1],0.0,0.00045/goalCenter[1]}};
	private double error[] = {0.0,0.0};
	private double lastError[] = {0.0,0.0};
	private double integralError[] = {0.0,0.0};
	
	private boolean foundGoal = false;
	
	public Camera(){											//Instantiates the Servos and Pi
		raspberryPi = new Serial(Port.kMXP,9600);
    	servoPins[0] = 8;
    	servoPins[1] = 9;
    	for(int i = 0;i < 2;i++){
    		cameraServos[i] = new Servo(servoPins[i]);
    		goalCoordinates[i] = goalCenter[i];
    	}
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new FollowGoal(PIDGains[0],PIDGains[1]));		//default command is to follow goal
    }
    
    public int getGoalCoordinates(){	//Gets the coordinates from the Pi to setup the Rio
    	String x = raspberryPi.getData();
    	if(x != "null"){
    		String delims = "[ ]";
    		String[] vals = x.split(delims);							//split string at spaces
    		if(vals.length == 2){
    			for(int i = 0;i < 2;i++){
    				goalCoordinates[i] = Integer.parseInt(vals[i]);		//get the integer values
    			}
    		}
   			lastReading = System.currentTimeMillis();					//set time for the last reading
   			if(goalCoordinates[0] == -1){
   				return -1;												//if can't see goal, return -1
   			}
    		return 1;													//if can see goal, return 1
    	}
    	return 0;														//else return 0
    }
    
    public void initializePID(double[] xGains,double[] yGains){	// Start the PID for the Camera Servos to the coordinates
    	for(int i = 0;i < 3;i++){
    		PIDGains[0][i] = xGains[i];							//set x gains
    	}
    	for(int i = 0;i < 3;i++){
    		PIDGains[1][i] = yGains[i];							//set y gains
    	}
    }
    
    public void updatePID(int newData){
    	double Dt = (double)(System.currentTimeMillis() - lastPIDUpdate) / 1000.0;
    	double[] outputs = {0.0,0.0};
    	for(int i = 0;i < 2;i++){
    		if(newData == 1){
    			lastError[i] = error[i];
    		}
    		error[i] = goalCoordinates[i] - goalCenter[i];
    		if(error[i] < 0){
    			if(inc[i] > 0){
    				inc[i] *= -1;
    			}
    		}else{
    			if(inc[i] < 0){
    				inc[i] *= -1;
    			}
    		}
    		double derivative = 0.0;
    		if(Dt < 0.400){
    			derivative = (error[i] - lastError[i]) / Dt;
    		}
    		integralError[i] += error[i] * Dt;
    		integralError[i] = Math.min(Math.max(integralError[i],-1000.0), 1000.0);
    		if(i == 1){
    			SmartDashboard.putNumber("Center X", goalCoordinates[0]);
        		SmartDashboard.putNumber("Center Y", goalCoordinates[1]);
        		//SmartDashboard.putNumber("goalCenter X", goalCenter[0]);
        		//SmartDashboard.putNumber("goalCenter Y", goalCenter[1]);
    			//SmartDashboard.putNumber("Error Y", error[1]);
    			//SmartDashboard.putNumber("outputValue Y", outputs[1]);
    		}
    		outputs[i] = servoAngles[i] + PIDGains[i][0] * error[i] + PIDGains[i][1] * integralError[i] + PIDGains[i][2] * derivative;
    	}
    	this.setServoValues(outputs);
    	lastPIDUpdate = System.currentTimeMillis();
    	foundGoal = true;
    }
    
    public void search(){		//If the camera can't find the goal, it needs to search around
    	double outputs[] = {0.0,0.0};
    	for(int i = 0;i < 2;i++){
    		if(servoAngles[i] + inc[i] < servoRangeSearch[i][0] && inc[i] < 0 ){				//changes direction (x)
    			inc[i] = -1*inc[i];
    		}
    		if(servoAngles[i] + inc[i] > servoRangeSearch[i][1] && inc[i] > 0){					//changes direction (y)
    			inc[i] = -1*inc[i];
    		}
    		outputs[i] = servoAngles[i] + inc[i];												//changes servo values
    	}
    	SmartDashboard.putNumber("inc Y", inc[1]);
    	SmartDashboard.putNumber("outputValue Y", outputs[1]);
    	this.setServoValues(outputs);															//actually moves servos
    	foundGoal = false;
    }
    
    public void setServoValues(double[] vals){														//sets the servo values	
    	vals[0] = Math.min(Math.max(vals[0],servoRange[0][0]), servoRange[0][1]);					//Constrain Servo Values
    	vals[1] = Math.min(Math.max(vals[1],servoRange[1][0]), servoRange[1][1]);
    	SmartDashboard.putNumber("yAngle", servoAngles[1]);
    	for(int i = 0;i < 2;i++){
    		servoAngles[i] = vals[i];								//Set Servo values (Does not actually set servos)
    	}
    }
    
    public void setServos(){			//Sets servos to a certain value
    	for(int i = 0;i < 2;i++){
    		cameraServos[i].set(servoAngles[i]);											//Set Servos
    	}
    	lastServoSet = System.currentTimeMillis();
    }
    
    public double getDistance(){		//Returns how far we are from the goal according to the camera
    	double rad = Math.toRadians(baseY - 170.0*servoAngles[1]);
    	return ((goalHeight - cameraHeight) / Math.tan(rad));//in inches
    }
    
    public double getAzimuth(){			//Tells us what angle we are at (RELATIVE).
    	return (170.0*(servoAngles[0] + (1.0 - servoRange[0][1])/2.0) - 90.0);//degrees
    }
    
    public boolean canSeeSwagadelia(){		//returns if the camera can indeed see the goal or if its searching around
    	SmartDashboard.putBoolean("RobotCanSeeGoal", foundGoal);
    	return foundGoal;
    }
}

