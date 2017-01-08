 package org.usfirst.frc.team293.robot.subsystems;

import java.io.FileWriter;
import java.io.IOException;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.commands.pdpLogging;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Logging extends Subsystem {//for logging PDP current
    PowerDistributionPanel pdp=new PowerDistributionPanel(0);
    private static FileWriter writer;
    private static int shotnum = 0;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public Logging(){
    	super();
    	//generateCSV("shooterData.csv");
    }
	
    public void initDefaultCommand() {
    	
        // Set the default command for a subsystem here.
        setDefaultCommand(new pdpLogging());
    }
    public void log(){
    	SmartDashboard.putNumber("C_TotalPower", pdp.getTotalPower());//log stuff
    	SmartDashboard.putNumber("C_ShooterWheel", pdp.getCurrent(5));
    	SmartDashboard.putNumber("C_TotalAmps", pdp.getTotalCurrent());
    	SmartDashboard.putBoolean("Optical Sensor", !Robot.feeder.boulderoptical());
    	
    }
    public void reset(){
    	pdp.clearStickyFaults();						//clear that bizarre sticky fault thing
    }
    
  /*  public void generateCSV(String fileName){
    	try {
			writer = new FileWriter(fileName);
			writer.append("ShotNum,Distance,Range,Good\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void printShotData(double distance,int range,boolean good) throws IOException{
    	shotnum++;
    	writer.append(Integer.toString(shotnum));
    	writer.append(",");
    	writer.append(Double.toString(distance));
    	writer.append(",");
    	writer.append(Integer.toString(range));
    	writer.append(",");
    	writer.append(Boolean.toString(good));
    }*/
}

