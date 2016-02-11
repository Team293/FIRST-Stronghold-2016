package org.usfirst.frc.team293.robot;

import org.usfirst.frc.team293.robot.commands.Aim;
import org.usfirst.frc.team293.robot.commands.DropCenterWheel;
import org.usfirst.frc.team293.robot.commands.Feeding;
import org.usfirst.frc.team293.robot.commands.Fire;
//import org.usfirst.frc.team293.robot.commands.LEDOn;
import org.usfirst.frc.team293.robot.commands.LiftCenterWheel;
import org.usfirst.frc.team293.robot.commands.LowGoal;
import org.usfirst.frc.team293.robot.commands.RunShooterWheel;
import org.usfirst.frc.team293.robot.commands.ShootHighGoal;
import org.usfirst.frc.team293.robot.commands.StopShooterWheel;
import org.usfirst.frc.team293.spikelibrary.SpikeLEDButton;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	///////////instantiate joysticks
	 private Joystick joy1 = new Joystick(0);
	 private Joystick joy2=new Joystick(1);
	 
	 public static Joystick launchpad=new Joystick(2);

	 
	 public OI(){
		 /////////////////////instantiate buttons
	JoystickButton feederbutton=new JoystickButton(launchpad,1);
	JoystickButton lowgoalbutton=new JoystickButton(launchpad,2);
	JoystickButton highgoalshoot=new JoystickButton(launchpad,3);
	JoystickButton manualshoot=new JoystickButton(launchpad,4);
	JoystickButton setshooterwheel=new JoystickButton(launchpad,11);
	JoystickButton climb=new JoystickButton(launchpad,6);
	JoystickButton centerwheellift=new JoystickButton(launchpad,7);
	
	JoystickButton portcullis=new JoystickButton(launchpad,8);
	JoystickButton chevaldefrise=new JoystickButton(launchpad,9);
	JoystickButton drawbridge=new JoystickButton(launchpad,10);
	JoystickButton sallyport=new JoystickButton(launchpad,5);
	
	
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
	 
	 lowgoalbutton.whenPressed(new LowGoal());
	 //lowgoalbutton.whenPressed(new LEDOn());
	 feederbutton.whenPressed(new Feeding());
	 setshooterwheel.whenActive(new RunShooterWheel());
	 setshooterwheel.whenInactive(new StopShooterWheel());
	 
	 centerwheellift.whenActive(new LiftCenterWheel());
	 centerwheellift.whenInactive(new DropCenterWheel());
	 
	 highgoalshoot.whenPressed(new ShootHighGoal());
	 
	 manualshoot.whenPressed(new Fire());
	 }
	    public double getJoystick1() {
	        return joy1.getY();
	    }
	    public double getJoystick2(){
	    	return joy2.getY();
	    }
	    public void flash(int port){
	    	launchpad.setOutputs(port);
	    }
	    
}

