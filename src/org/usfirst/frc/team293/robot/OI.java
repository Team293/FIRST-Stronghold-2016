package org.usfirst.frc.team293.robot;

import org.usfirst.frc.team293.robot.commands.CenterWheelDrop;
import org.usfirst.frc.team293.robot.commands.Climb;
import org.usfirst.frc.team293.robot.commands.Feeding;
import org.usfirst.frc.team293.robot.commands.Fire;
import org.usfirst.frc.team293.robot.commands.HoodRestPosition;
import org.usfirst.frc.team293.robot.commands.LiftCenterWheel;

import org.usfirst.frc.team293.robot.commands.LowGoal;
import org.usfirst.frc.team293.robot.commands.ManualHood;
import org.usfirst.frc.team293.robot.commands.RotateShooter;
import org.usfirst.frc.team293.robot.commands.RunShooterWheel;
import org.usfirst.frc.team293.robot.commands.RunShooterWheelSlow;
import org.usfirst.frc.team293.robot.commands.ShootHighGoal;
import org.usfirst.frc.team293.robot.commands.SimpleAim;
import org.usfirst.frc.team293.robot.commands.StopShooterWheel;
import org.usfirst.frc.team293.robot.commands.aimAndShoot;
import org.usfirst.frc.team293.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	///////////instantiate joysticks
	 private static Joystick joy1 = new Joystick(0);
	 private static Joystick joy2=new Joystick(1);
	 
	 public static Joystick launchpad=new Joystick(2);
	 
	 public static Joystick launchpad2=new Joystick(3);

	 
	 public OI(){
		 /////////////////////instantiate buttons Launchpad
	JoystickButton feederbutton=new JoystickButton(launchpad,RobotMap.feederButt[0]);
	JoystickButton aimbutton=new JoystickButton(launchpad,RobotMap.aimButt[0]);
	JoystickButton manualshoot=new JoystickButton(launchpad,RobotMap.manualButt[0]);
	JoystickButton setcam=new JoystickButton(launchpad,RobotMap.shooterButt[0]);
	JoystickButton climb=new JoystickButton(launchpad,RobotMap.climbButt[0]);
	JoystickButton centerwheellift=new JoystickButton(launchpad,RobotMap.wheelButt[0]);
	JoystickButton portcullis=new JoystickButton(launchpad,RobotMap.portButt[0]);
	JoystickButton lowGoal = new JoystickButton(launchpad,RobotMap.lowButt[0]);
	JoystickButton AutoAimandShoot= new JoystickButton(launchpad,RobotMap.autoAimAndShootButt[0]);
	 /////////////////////instantiate buttons Launchpad2
	JoystickButton manualHoodSwitch=new JoystickButton(launchpad2,1);
	JoystickButton disableHoodSwitch=new JoystickButton(launchpad2,2);
	JoystickButton RPMFastSwitch=new JoystickButton(launchpad2,3);
	JoystickButton RPMSlowSwitch=new JoystickButton (launchpad2,4);
	
	
	JoystickButton feederbuttonBackup=new JoystickButton(joy1,7);
	JoystickButton lowgoalbuttonBackup=new JoystickButton(joy1,6);
	JoystickButton aimbuttonBackup=new JoystickButton(joy1,11);
	JoystickButton manualshootBackup=new JoystickButton(joy1,10);
	JoystickButton setshooterwheelBackup=new JoystickButton(joy2,7);
	JoystickButton aimAndShootButton = new JoystickButton(joy1,3);
	//JoystickButton chevaldefrise=new JoystickButton(launchpad,RobotMap.chevaldeFesse[0]);
//	JoystickButton drawbridge=new JoystickButton(launchpad,RobotMap.drawButt[0]);
	//JoystickButton sallyport=new JoystickButton(launchpad,RobotMap.sallyButt[0]);
	JoystickButton rightTrigger = new JoystickButton(joy1,1);
	JoystickButton leftTrigger = new JoystickButton(joy2,1);
		
	
	manualHoodSwitch.toggleWhenPressed(new ManualHood());
	disableHoodSwitch.whenActive(new HoodRestPosition());
	
	 climb.toggleWhenPressed(new Climb());
	 lowGoal.toggleWhenPressed(new LowGoal());
	 lowgoalbuttonBackup.toggleWhenPressed(new LowGoal());
	 feederbutton.toggleWhenPressed(new Feeding());
	 feederbuttonBackup.toggleWhenPressed(new Feeding());
	 setcam.whenPressed(new LiftCenterWheel());
	 setcam.whenReleased(new CenterWheelDrop());
	 RPMFastSwitch.whenPressed(new RunShooterWheel());
	 setshooterwheelBackup.whenPressed(new RunShooterWheel());
	 RPMSlowSwitch.whenPressed(new RunShooterWheelSlow());
	 RPMFastSwitch.whenReleased(new StopShooterWheel());
	 RPMSlowSwitch.whenReleased(new StopShooterWheel());
	 setshooterwheelBackup.whenReleased(new StopShooterWheel());
	 centerwheellift.toggleWhenPressed(new LiftCenterWheel());
	 

	 aimbutton.toggleWhenPressed(new SimpleAim(true));
	 aimbuttonBackup.toggleWhenPressed(new SimpleAim(true));
	 leftTrigger.whenPressed(new SimpleAim(true));
	 manualshoot.toggleWhenPressed(new ShootHighGoal());
	 manualshootBackup.toggleWhenPressed(new ShootHighGoal());
	 rightTrigger.toggleWhenPressed(new ShootHighGoal());
	 
	 AutoAimandShoot.toggleWhenPressed(new aimAndShoot());
	 aimAndShootButton.toggleWhenPressed(new aimAndShoot());
	 
	 
	 }
	    public static double getJoystick1() {
	        return joy1.getY();
	    }
	    public static double getJoystick2(){
	    	return joy2.getY();
	    }
	    public static double getHoodDial(){
	    	double raw = launchpad2.getRawAxis(RobotMap.inDaHood);
	    	return (raw);
	    }
	    public static double getRotationDial(){
	    	double raw=launchpad.getRawAxis(RobotMap.HVUKNO);
	    	return (raw);
	    }
	    public static double getBackupHoodDial(){
	    	return joy1.getZ();
	    }
	    
}


//// CREATING BUTTONS
// One type of button is a joystick button which is any button on a joystick.
// You create one by telling it which joystick it's on and which button
// number it is.
// Joystick stick = new Joystick(port);
// Button button = new JoystickButton(stick, buttonNumber);

// There are a few additional built in buttons you can use. Additionally,
// by subclassing Button you can create custom triggers and bind those to
// commands the same as any other Button.

//// TRIGGERING COMMANDS WITH BUTTONS
// Once you have a button, it's trivial to bind it to a button in one of
// three ways:

// Start the command when the button is pressed and let it run the command
// until it is finished as determined by it's isFinished method.
// button.whenPressed(new ExampleCommand());

// Run the command while the button is being held down and interrupt it once
// the button is released.
// button.whileHeld(new ExampleCommand());

// Start the command when the button is released  and let it run the command
// until it is finished as determined by it's isFinished method.
// button.whenReleased(new ExampleCommand());

