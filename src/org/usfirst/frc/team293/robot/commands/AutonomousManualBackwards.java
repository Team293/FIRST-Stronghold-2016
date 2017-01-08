package org.usfirst.frc.team293.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousManualBackwards extends CommandGroup {
    
    public  void AutonomousManaulBackwards() {//what is this going to do?  We have to work on IMU a lot.  Not sure how PID subsystem works
    	addParallel(new AutonomousLEDs());
    	addSequential(new ManualDriveStraight(4,.9));		//Drive 10 feet straight	
    	
    //	addSequential(new tankdrive());					//Shoot
    }
}
