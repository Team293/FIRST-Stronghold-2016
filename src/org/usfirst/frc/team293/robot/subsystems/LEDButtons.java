package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.OI;
import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class LEDButtons extends Subsystem {
	public int port;
	public boolean status;
	public long time;
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public LEDButtons(int i){
    	super();
    	this.port=i;
    	this.status=false;
    	this.time= System.currentTimeMillis();
    }
    
    public void on(){
    	OI.launchpad.setOutput(this.port, true);
    	this.status=true;
    }
    public void off(){
    	OI.launchpad.setOutput(this.port, false);
    	this.status=false;
    }
    public void flash(){
    	if((System.currentTimeMillis()-this.time)>750){
    		this.status= !this.status;
    		OI.launchpad.setOutput(this.port, this.status);
    		this.time=System.currentTimeMillis();
    	}
    }
    
}

