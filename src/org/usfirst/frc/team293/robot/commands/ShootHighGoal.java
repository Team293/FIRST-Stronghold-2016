package org.usfirst.frc.team293.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShootHighGoal extends CommandGroup {

    public ShootHighGoal() {
    	//addParallel(new RunShooterWheel());
    	addSequential(new Fire());
    	addSequential(new LookDown());
    }

  
}
