package org.usfirst.frc.team293.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShootHighGoal extends CommandGroup {

    public ShootHighGoal() {
    	addParallel(new RunShooterWheel());
    	addSequential(new Aim());
    	addSequential(new Fire());
    	addSequential(new LookDown());
    }

  
}
