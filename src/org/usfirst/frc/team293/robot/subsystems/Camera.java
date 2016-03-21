package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.RobotMap;
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
	
	private static final double servoRange[][] = {{0.15, 0.74},{0.19, 0.567}};
	private static final double servoRangeSearch[][] = {{0.15, 0.74},{0.32, 0.49}};
	
<<<<<<< HEAD
    private static final double cameraHeight = 19.5;				//42.5 on robot //in
    private static final double goalHeight = 90.0;					//height of the middle of the goal
    private static final double calibrationDist = 80.0;				//distance that calibrationAngle was taken at
    private static final double calibrationAngle = 0.345;			//angle of Y servo
=======
    private static final double cameraHeight = 19.5;				//19.5 on robot //in
    private static final double goalHeight = 100.0;					//height of the middle of the goal
    private static final double calibrationDist = 80.0;			//distance that calibrationAngle was taken at
    private static final double calibrationAngle = 0.369;			//angle of Y servo
>>>>>>> origin/master
    //calculates base y angle
    private static final double baseY = Math.toDegrees(Math.atan((goalHeight - cameraHeight)/calibrationDist))
    		+ 170.0*calibrationAngle;
    //x servo angle pointed straight forward
    private static final double baseX = 0.402;
    //x and y search speeds
    private static final double inc[] = {0.024,0.015};
    //starting servo angles
    private static double[] servoAngles = {baseX, calibrationAngle};				//initial Servo angles (0.0-1.0 scale)
    
    /*                         SWAGADELIC CAMERA STUFF                              */
    
    public long lastReading = System.currentTimeMillis();
    public long lastPIDUpdate = System.currentTimeMillis();
	
	Serial raspberryPi;												//serial port for Pi
	private static final double goalCenter[] = {160.0,140.0};		//center of screen in pixels
	public double goalCoordinates[] = new double[2];				//coordinates received from Pi
	
	//PID variables
	private static final double PIDGains[][] = {{0.025/goalCenter[0],0.0,0.0008/goalCenter[0]},
			{0.0165/goalCenter[1],0.0,0.00045/goalCenter[1]}};
	private static double error[] = {0.0,0.0};
	private static double lastError[] = {0.0,0.0};
	private static double integralError[] = {0.0,0.0};
	//if it can see the goal
	private static boolean foundGoal = false;
	
	private static final double AIMED = 0.79;		//tolerance for being aimed at the goal
	//for shooter and drivetrain rotation compensation
	private double lastAngle = 0.0;					
	private double lastAzimuth = -1.0;
	
	public Camera(){											//Instantiates the Servos and Pi
		raspberryPi = new Serial(Port.kMXP,9600);				//9600 baud
    	servoPins[0] = RobotMap.horizontalvisioncamera;
    	servoPins[1] = RobotMap.verticalvisioncamera;
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
   			SmartDashboard.putNumber("goal x",goalCoordinates[0]);
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
    	double Dt = (double)(System.currentTimeMillis() - lastPIDUpdate) / 1000.0;		//gets dt (seconds)
    	double[] outputs = {0.0,0.0};
    	for(int i = 0;i < 2;i++){
    		//if we have new data update the last error
    		if(newData == 1){
    			lastError[i] = error[i];
    		}
    		//get error (actual - wanted)
    		error[i] = goalCoordinates[i] - goalCenter[i];
    		//wierd stuff to make it find the goal faster after it loses it
    		//moves to the side that it lost it on
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
    		if(Dt < 0.400){				//exclude derivative and integral if it has been too long
    			derivative = (error[i] - lastError[i]) / Dt;
    			integralError[i] += error[i] * Dt;
    		}
    		//constrain integral (-1000 to 1000)
    		integralError[i] = Math.min(Math.max(integralError[i],-1000.0), 1000.0);
    		//set outputs based on PID
    		outputs[i] = servoAngles[i] + PIDGains[i][0] * error[i] + PIDGains[i][1] * integralError[i] + PIDGains[i][2] * derivative;
    	}
    	this.setServoValues(outputs);							//set the servo values (DOES NOT MOVE SERVOS)
    	lastPIDUpdate = System.currentTimeMillis();
    	foundGoal = true;
    }
    
    public void search(){		//If the camera can't find the goal, it needs to search around
    	double outputs[] = {0.0,0.0};
    	for(int i = 0;i < 2;i++){
    		if(servoAngles[i] + inc[i] < servoRangeSearch[i][0] && inc[i] < 0 ){			//changes direction (x)
    			inc[i] = -1*inc[i];
    		}
    		if(servoAngles[i] + inc[i] > servoRangeSearch[i][1] && inc[i] > 0){				//changes direction (y)
    			inc[i] = -1*inc[i];
    		}
    		outputs[i] = servoAngles[i] + inc[i];											//changes servo values
    	}

    	this.setServoValues(outputs);														//actually moves servos
    	foundGoal = false;
    }
    
    public void setServoValues(double[] vals){												//sets the servo values	
    	vals[0] = Math.min(Math.max(vals[0],servoRange[0][0]), servoRange[0][1]);			//Constrain Servo Values
    	vals[1] = Math.min(Math.max(vals[1],servoRange[1][0]), servoRange[1][1]);
    	for(int i = 0;i < 2;i++){
    		servoAngles[i] = vals[i];								//Set Servo values (Does not actually set servos)
    	}
    }
    
    public void setServos(){			//Sets servos to a certain value
    	for(int i = 0;i < 2;i++){
    		cameraServos[i].set(servoAngles[i]);											//Set Servos
    	}
    	SmartDashboard.putNumber("x Servo",servoAngles[0]);
    	SmartDashboard.putNumber("y Servo",servoAngles[1]);
    	lastServoSet = System.currentTimeMillis();
    }
    
    public double getDistance(){		//Returns how far we are from the goal according to the camera
    	double rad = Math.toRadians(baseY - 170.0*servoAngles[1]);
    	return (((goalHeight - cameraHeight)*1.18 / Math.tan(rad)));//in inches
    }
    
    public double getAzimuth(){			//Tells us what angle we are at (RELATIVE).
    	return (170.0*(servoAngles[0] - baseX));//degrees
    }
    
    public boolean isAimed(){			//returns whether the robot is aimed at the goal (azimuth only)
    	return (Math.abs(getAzimuth()) < AIMED);
    }
    
    public boolean canSeeSwagadelia(){		//returns if the camera can indeed see the goal or if its searching around
    	SmartDashboard.putBoolean("RobotCanSeeGoal", foundGoal);
    	return foundGoal;
    }
    public boolean whenstaringatSwagadelia(){
    	boolean staring = false;
    	if(Math.abs(goalCoordinates[0] - goalCenter[0]) < 30.0 && Math.abs(goalCoordinates[1] - goalCenter[1]) < 25.0){
    		staring=true;
    	}
    	else{
    		staring =false;
    	}
    	return staring;
    }
    public void shooterRotcompensation(boolean first){		//compensates for shooter and drivetrain rotation
    	double az = Robot.drivetrain.getAttitude()[0];		//get azimuth
    	if(first){											//if it is running for the first time
    		lastAngle = Robot.shooterrotation.getangle();	//get base shooter rotation angle
    		lastAzimuth = az;								//get base IMU angle
    	}
    	double change = az - lastAzimuth;					//get change in IMU angle
    	if(change > 180.0){									//compensate for discontinuity
    		change -= 360.0;
    	}else if(change < -180.0){
    		change += 360.0;
    	}
    	if(first || az == -1.0 || lastAzimuth == -1.0){		//if no IMU values were received yet don't compensate
    		change = 0;
    	}
    	//translate changes to changes in servo values
    	double vals[] = {baseX + (getAzimuth() + Robot.shooterrotation.getangle() - lastAngle - change)/170.0
    			,servoAngles[1]};
    	//move servos
    	setServoValues(vals);
    	setServos();
    	//set last stuff
    	lastAngle = Robot.shooterrotation.getangle();
    	lastAzimuth = az;
    }
}

