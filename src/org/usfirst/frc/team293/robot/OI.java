package org.usfirst.frc.team293.robot;

import org.usfirst.frc.team293.robot.commands.Aim;
import org.usfirst.frc.team293.robot.commands.CenterWheelDrop;
import org.usfirst.frc.team293.robot.commands.Climb;
import org.usfirst.frc.team293.robot.commands.Feeding;
import org.usfirst.frc.team293.robot.commands.Fire;
import org.usfirst.frc.team293.robot.commands.LiftCenterWheel;

import org.usfirst.frc.team293.robot.commands.LowGoal;
import org.usfirst.frc.team293.robot.commands.RotateShooter;
import org.usfirst.frc.team293.robot.commands.RunShooterWheel;
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
	JoystickButton lowgoalbutton=new JoystickButton(launchpad,RobotMap.lowButt[0]);
	JoystickButton aimbutton=new JoystickButton(launchpad,RobotMap.aimButt[0]);
	JoystickButton manualshoot=new JoystickButton(launchpad,RobotMap.manualButt[0]);
	JoystickButton setcam=new JoystickButton(launchpad,RobotMap.shooterButt[0]);
	JoystickButton climb=new JoystickButton(launchpad,RobotMap.climbButt[0]);
	JoystickButton centerwheellift=new JoystickButton(launchpad,RobotMap.wheelButt[0]);
	JoystickButton portcullis=new JoystickButton(launchpad,RobotMap.portButt[0]);
	JoystickButton lowgoal = new JoystickButton(launchpad,RobotMap.rotateLeftButt[0]);
	JoystickButton AutoAimandShoot= new JoystickButton(launchpad,RobotMap.rotateRightButt[0]);
	 /////////////////////instantiate buttons Launchpad2
	JoystickButton Automanualswitch1=new JoystickButton(launchpad2,1);
	JoystickButton Automanualswitch2=new JoystickButton(launchpad2,2);
	JoystickButton RPMmanualswitch1=new JoystickButton(launchpad2,3);
	JoystickButton RPMmanualswitch2=new JoystickButton (launchpad2,4);
	
	
	
	
	
	JoystickButton feederbuttonBackup=new JoystickButton(joy1,7);
	JoystickButton lowgoalbuttonBackup=new JoystickButton(joy1,6);
	JoystickButton aimbuttonBackup=new JoystickButton(joy1,11);
	JoystickButton manualshootBackup=new JoystickButton(joy1,10);
	JoystickButton setshooterwheelBackup=new JoystickButton(joy2,7);
	JoystickButton rotateShooterLeftBackup = new JoystickButton(joy1,8);
	JoystickButton rotateShooterRightBackup = new JoystickButton(joy1,9);
	JoystickButton aimAndShootButton = new JoystickButton(joy1,3);
	//JoystickButton chevaldefrise=new JoystickButton(launchpad,RobotMap.chevaldeFesse[0]);
//	JoystickButton drawbridge=new JoystickButton(launchpad,RobotMap.drawButt[0]);
	//JoystickButton sallyport=new JoystickButton(launchpad,RobotMap.sallyButt[0]);
	JoystickButton rightTrigger = new JoystickButton(joy1,1);
	JoystickButton leftTrigger = new JoystickButton(joy2,1);
		
	
	 climb.toggleWhenPressed(new Climb());
	 lowgoalbutton.toggleWhenPressed(new LowGoal());
	 lowgoalbuttonBackup.toggleWhenPressed(new LowGoal());
	 feederbutton.toggleWhenPressed(new Feeding());
	 feederbuttonBackup.toggleWhenPressed(new Feeding());
	 setcam.whenPressed(new LiftCenterWheel());
	 setcam.whenReleased(new CenterWheelDrop());
	 setshooterwheelBackup.whenPressed(new RunShooterWheel());
	 setshooterwheelBackup.whenReleased(new StopShooterWheel());
	 centerwheellift.toggleWhenPressed(new LiftCenterWheel());
	 leftTrigger.whenPressed(new LiftCenterWheel());
	 

	 aimbutton.toggleWhenPressed(new SimpleAim(true));
	 aimbuttonBackup.toggleWhenPressed(new SimpleAim(true));
	 aimAndShootButton.toggleWhenPressed(new aimAndShoot());
	 manualshoot.toggleWhenPressed(new ShootHighGoal());
	 manualshootBackup.toggleWhenPressed(new ShootHighGoal());
	 
	 AutoAimandShoot.toggleWhenPressed(new aimAndShoot());
	 manualshoot.whenPressed(new Fire());
	 rightTrigger.whenPressed(new Fire());
	 
	 
	 
	 }
	    public static double getJoystick1() {
	        return joy1.getY();
	    }
	    public static double getJoystick2(){
	    	return joy2.getY();
	    }
	    public static double getHoodDial(){
	    	double raw = launchpad.getRawAxis(RobotMap.inDaHood);
	    	return (raw);
	    }
	    public static double getRotationDial(){
	    	double raw=launchpad2.getRawAxis(RobotMap.inDaHood);
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

