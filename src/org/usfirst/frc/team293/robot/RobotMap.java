package org.usfirst.frc.team293.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	//VictorSP Stuff
     public static int 
     leftMotor = 0,  //splitter
     rightMotor = 1, //splitter
     outsideFeeder=5,
     insideFeeder=2;
     
     //shooterwheel1= 
    
     //TalonSRX Stuff
     public static int 
     shooterwheel=2,
     hoodMotor=4,
     shooterRotation=3 ,
     lifterMotor=1;
     
     //TalonSRX Sensors
     /*
     Shooter Encoder
     shooterRotation Potentionmeter
     Hood Potentiometer
     drivetrainLifterMotor Potentiometer
     
     */
     //Butt stuff ([0] = button number, [1] = LED number)
     
     public static final int[]
    	     feederButt={2,11},
    	     downWheelButt={9,9},
    	     aimButt={5,3},
    	     manualButt={4,4},
    	     lowButt={10,10},
    	     camButt={11,1},
    	     autoAimAndShootButt={3,5},
    	     portButt={1,6},
    	     upWheelButt={8,8},
    	     driverAimButt={6,7};
     
     //Hood Knobber
     public static final int inDaHood = 5;			//We Back
     public static final int HVUKNO = 6;			//We Back
    		 
     
     //Servos
     public static final int
     drivercamera=7,
     horizontalvisioncamera=8,
     verticalvisioncamera=6;
     
     //Sensors
     public static final int
     //digital sensors
     leftEncoderone=0,
     leftEncodertwo=1,
     rightEncoderone=2,
     rightEncodertwo=3,
     opticallimit=4;
     //Drivetrainlimit=6,
    // Drivetrainlimitdown=5;
     
     //Flash time
     public static final int
     flashnorm=300,
     flashfast=200;
     /**************Power Stuff*************
      
      * Right Side:
      RightDrive 2,3
      Feeder 4
      Lifter
      Hood
      
      
      *Left Side:
     Leftdrive 12, 13
     Feeder 11
     Shooterwheel
     ShooterRotation
     
     Arduino 12v 500ma
     
     Pi in the 5v 2a
     */
}
