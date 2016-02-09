package org.usfirst.frc.team293.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShootHighGoal extends CommandGroup {

    public ShootHighGoal() {
    	addSequential(new Aim());
    	addSequential(new Fire());
    }

  
}
