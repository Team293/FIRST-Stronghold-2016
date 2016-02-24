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
     outsideFeeder=3,
     insideFeeder=2;
    
     //TalonSRX Stuff
     public static int 
     shooterwheel=2,
     hoodMotor=1,
     shooterRotation=0,
     lifterMotor=4;
     
     //TalonSRX Sensors
     /*
     Shooter Encoder
     shooterRotation Potentionmeter
     Hood Potentiometer
     drivetrainLifterMotor Potentiometer
     
     */
     //Butt stuff ([0] = button number, [1] = LED number)
     /*public static final int[]
     feederButt={3,5},
     lowButt={5,3},
     aimButt={2,11},
     manualButt={4,4},
     climbButt={10,10},
     shooterButt={11,1},
     wheelButt={9,9},
     portButt={1,6},
     chevaldeFesse={6,7},
     sallyButt={8,8};*/
     
     public static final int[]
    	     feederButt={2,11},
    	     lowButt={9,9},
    	     aimButt={5,3},
    	     manualButt={4,4},
    	     rotateLeftButt={10,10},
    	     shooterButt={11,1},
    	     rotateRightButt={3,5},
    	     portButt={1,6},
    	     wheelButt={8,8},
    	     climbButt={6,7};
     
     //Hood Knobber
     public static final int inDaHood = 6;			//We Back
    		 
     
     //Servos
     public static final int
     drivercamera=7,
     horizontalvisioncamera=8,
     verticalvisioncamera=9;
     
     //Sensors
     public static final int
     //digital sensors
     leftEncoderone=0,
     leftEncodertwo=1,
     rightEncoderone=2,
     rightEncodertwo=3,
     opticallimit=4;  
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
