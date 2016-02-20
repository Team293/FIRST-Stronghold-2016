package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.OI;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LEDButtons extends Subsystem {

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}		//Allows for the Launchpad LED buttons to light up
	/*public int port;
	public boolean status;
	public long time;
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public LEDButtons(int i){
    	super();
    	this.status=false;
    	this.port = i;
    	this.time= System.currentTimeMillis();
    }
    
    public void on(){
    	this.status=true;
    	OI.launchpad.setOutput(this.port, this.status);
    }
    public void off(){
    	this.status=false;
    	OI.launchpad.setOutput(this.port, this.status);
    }
    public void flash(){
    	if((System.currentTimeMillis()-this.time)>750){
    		this.status = !this.status;
    		OI.launchpad.setOutput(this.port, this.status);
    		this.time = System.currentTimeMillis();
    	}
    }*/
}

