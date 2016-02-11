package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.OI;
import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LEDButtons extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void on(int i){
    	OI.launchpad.setOutput(i, true);
    }
    public void off(int i){
    	OI.launchpad.setOutput(i, false);
    }
}

