package org.usfirst.frc.team293.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Arduino extends Subsystem {//How the heck does this work?  Where is the Arduino Code?  Is this going to be worthless to us?
	
	I2C arduino;
	byte[] toSend = new byte[1];
	
	int arduinoAddress = 8;
	
	public Arduino(){
		arduino = new I2C(I2C.Port.kOnboard, arduinoAddress);
	}
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void writeByte(byte toWrite){
    	toSend[0] = toWrite;
    	arduino.transaction(toSend, 1, null, 0);
    }
}

