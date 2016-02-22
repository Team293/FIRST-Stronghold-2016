 package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.commands.pdpLogging;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Logging extends Subsystem {//for logging PDP current
    PowerDistributionPanel pdp=new PowerDistributionPanel(0);
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
    public void initDefaultCommand() {
    	
        // Set the default command for a subsystem here.
        setDefaultCommand(new pdpLogging());
    }
    public void log(){
    	SmartDashboard.putNumber("C_TotalPower", pdp.getTotalPower());//log stuff
    	SmartDashboard.putNumber("C_ShooterWheel", pdp.getCurrent(5));
    	SmartDashboard.putNumber("C_TotalAmps", pdp.getTotalCurrent());
    	
    }
    public void reset(){
    	pdp.clearStickyFaults();//clear that bizarre sticky fault thing
    }
}

