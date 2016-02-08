package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Feeder extends Subsystem {
    private SpeedController outsidefeeder,insidefeeder;
    private DigitalInput opticallimit;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public Feeder(){
    outsidefeeder=new VictorSP(RobotMap.outsideFeeder);
    insidefeeder=new VictorSP(RobotMap.insideFeeder);
    
    		    	
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void outsidefeederset(double speed){
    	outsidefeeder.set(speed);
    }
    public void insidefeederset(double speed){
    	insidefeeder.set(speed);	
    }
    public boolean boulderoptical(){
    	return opticallimit.get();
    }
}

