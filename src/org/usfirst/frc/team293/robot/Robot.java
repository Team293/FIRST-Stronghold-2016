
package org.usfirst.frc.team293.robot;

import org.usfirst.frc.team293.robot.commands.AutonomousHighShoot;
//import org.usfirst.frc.team293.robot.commands.Autonomous;
import org.usfirst.frc.team293.robot.commands.AutonomousManualBackwards;
import org.usfirst.frc.team293.robot.commands.AutonomousManualForwards;
import org.usfirst.frc.team293.robot.commands.ManualDriveStraight;
import org.usfirst.frc.team293.robot.subsystems.Arduino;
//import org.usfirst.frc.team293.robot.subsystems.Arduino;
import org.usfirst.frc.team293.robot.subsystems.Camera;
import org.usfirst.frc.team293.robot.subsystems.DriveTrain;
import org.usfirst.frc.team293.robot.subsystems.DriverCamera;
import org.usfirst.frc.team293.robot.subsystems.Feeder;
import org.usfirst.frc.team293.robot.subsystems.Hood;
import org.usfirst.frc.team293.robot.subsystems.LEDButtons;
import org.usfirst.frc.team293.robot.subsystems.LifterDriveTrain;
import org.usfirst.frc.team293.robot.subsystems.Logging;

import org.usfirst.frc.team293.robot.subsystems.ShooterWheel;
import org.usfirst.frc.team293.robot.subsystems.continuousFunctions;
import org.usfirst.frc.team293.robot.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	
	public static DriverStation.Alliance color;
	
	//Command autonomousCommand;
	//SendableChooser autoChooser;
	//public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	///////////instantiate subsystems
	public static OI oi;
	public static DriverCamera drivercamera;
	public static DriveTrain drivetrain;
	public static Feeder feeder;
	public static Hood hood;
	
	public static ShooterWheel shooterwheel;
	public static LifterDriveTrain lifterdrivetrain;
	public static Camera Camera;
	public static Logging logging;
	public static Arduino ledStrip;
	
	
	public static LEDButtons ledShooterWheels;
	public static LEDButtons ledLowGoal;
	public static LEDButtons ledClimb;
	public static LEDButtons ledFeeder;
	public static LEDButtons ledHighGoal;
	public static LEDButtons ledManual;
	public static LEDButtons ledCenterWheel;
	public static LEDButtons ledRotateLeft;
	public static LEDButtons ledRotateRight;
	
	public static Preferences prefs;
	
	public static continuousFunctions continuousfunctions;
	
   Command autonomousCommand;//instantiate auto command
   SendableChooser autoChooser;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */

    public void robotInit() {
		
///////////instantiate subsystems
    	//LED Buttons
    	ledShooterWheels = new LEDButtons(RobotMap.downWheelButt[1]);
    	ledClimb= new LEDButtons(RobotMap.driverAimButt[1]);
    	ledFeeder= new LEDButtons(RobotMap.feederButt[1]);
    	ledLowGoal = new LEDButtons(RobotMap.lowButt[1]);
    	ledHighGoal = new LEDButtons(RobotMap.aimButt[1]);
    	ledManual = new LEDButtons(RobotMap.manualButt[1]);
    	ledCenterWheel= new LEDButtons(RobotMap.upWheelButt[1]);    	
    	
        drivetrain			=new DriveTrain();
        hood				=new Hood();
        lifterdrivetrain	=new LifterDriveTrain();
       
        drivercamera		=new DriverCamera();
        feeder				=new Feeder();
   
        shooterwheel		=new ShooterWheel();
        logging				=new Logging();
        Camera				=new Camera();
        oi					=new OI();
        autonomousCommand	=new ManualDriveStraight();
        ledStrip			=new Arduino();
        continuousfunctions	=new continuousFunctions();
        
        ////////////////autonomous Chooser
        
        autoChooser = new SendableChooser();
        autoChooser.addDefault("Backwards Manual", new AutonomousManualBackwards());
        autoChooser.addObject("Fowards Manual", new AutonomousManualForwards());
        autoChooser.addObject("AutonomousHighShoot", new AutonomousHighShoot());
       // autoChooser.addObject(   
        //color=DriverStation.getInstance().getAlliance();
        
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
    autonomousCommand.start();
    	
    	color=DriverStation.getInstance().getAlliance();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	 Scheduler.getInstance().run();
    }

    public void teleopInit() {
    	//autonomousCommand.cancel();
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    public void teleopDisable(){
    	//Robot.lifterdrivetrain.drop();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
