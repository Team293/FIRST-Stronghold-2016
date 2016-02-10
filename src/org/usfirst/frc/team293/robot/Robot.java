
package org.usfirst.frc.team293.robot;

import org.usfirst.frc.team293.robot.commands.Aim;
import org.usfirst.frc.team293.robot.commands.Autonomous;
import org.usfirst.frc.team293.robot.commands.HoodRestPosition;
import org.usfirst.frc.team293.robot.commands.LookDown;
import org.usfirst.frc.team293.robot.subsystems.Camera;
import org.usfirst.frc.team293.robot.subsystems.DriveTrain;
import org.usfirst.frc.team293.robot.subsystems.DriverCamera;
import org.usfirst.frc.team293.robot.subsystems.Feeder;
import org.usfirst.frc.team293.robot.subsystems.Hood;
import org.usfirst.frc.team293.robot.subsystems.LifterDriveTrain;
import org.usfirst.frc.team293.robot.subsystems.Logging;
import org.usfirst.frc.team293.robot.subsystems.ShooterRotation;
import org.usfirst.frc.team293.robot.subsystems.ShooterWheel;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
//import org.usfirst.frc.team293.robot.commands.ExampleCommand;
//import org.usfirst.frc.team293.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	//public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;
	public static DriverCamera drivercamera;
	public static DriveTrain drivetrain;
	public static Feeder feeder;
	public static Hood hood;
	public static ShooterRotation shooterrotation;
	public static ShooterWheel shooterwheel;
	public static LifterDriveTrain lifterdrivetrain;
	public static Camera Camera;
	public static Logging logging;
	
   // Command autonomousCommand = new Autonomous();
    SendableChooser chooser;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		
		
        drivetrain = new DriveTrain();
        
        hood= new Hood();
        lifterdrivetrain = new LifterDriveTrain();
       // Camera=new Camera(null, null, null, 0, 0, null);
        drivercamera=new DriverCamera();
        feeder=new Feeder();
        shooterrotation=new ShooterRotation();
        shooterwheel=new ShooterWheel();
        logging=new Logging();
        
        oi = new OI();
 
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
    	// autonomousCommand.start();
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
        //Logging.log;
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
