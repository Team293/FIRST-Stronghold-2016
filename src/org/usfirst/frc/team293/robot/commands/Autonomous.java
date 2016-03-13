package org.usfirst.frc.team293.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Autonomous extends CommandGroup {
    
    public  Autonomous() {//what is this going to do?  We have to work on IMU a lot.  Not sure how PID subsystem works
    	addSequential(new StartDriveStraight(10.0));		//Drive 10 feet straight				
    	addSequential(new aimAndShoot());					//Shoot
    }
}
