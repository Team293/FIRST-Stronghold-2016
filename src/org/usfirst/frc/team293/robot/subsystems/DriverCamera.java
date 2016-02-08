package org.usfirst.frc.team293.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriverCamera extends Subsystem {
    Servo drivercamera;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    void Lookdown(){
    	drivercamera.set(0);
    }
    void Lookup(){
    	drivercamera.set(.7);
    }
    
    public void initDefaultCommand() {
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

