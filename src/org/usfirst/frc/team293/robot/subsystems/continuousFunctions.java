package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.commands.runContinuousFunctions;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class continuousFunctions extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private boolean 
	shooterOn = false,
	canSeeSwag = false,
	autoBlue = false,
	teleop = false,
	hasBall = false,
	aiming = false,
	afterShooting = false,
	
	drivetrainUp = false,
	
	PARTY = true,
	
	newData = false;
	
	private static long afterShootingTime = 0;
	private static final long timeToCelebrate = 3650;
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new runContinuousFunctions());
    }


	public boolean isNewData() {
		return newData;
	}


	public void setNewData(boolean newData) {
		this.newData = newData;
	}


	public boolean isShooterOn() {
		return shooterOn;
	}


	public void setShooterOn(boolean shooterOn) {
		setNewData(true);
		this.shooterOn = shooterOn;
	}


	public boolean isCanSeeSwag() {
		return canSeeSwag;
	}


	public void setCanSeeSwag(boolean canSeeSwag) {
		setNewData(true);
		this.canSeeSwag = canSeeSwag;
	}


	public boolean isAutoBlue() {
		return autoBlue;
	}


	public void setAutoBlue(boolean autoBlue) {
		setNewData(true);
		this.autoBlue = autoBlue;
	}


	public boolean isTeleop() {
		return teleop;
	}


	public void setTeleop(boolean teleop) {
		setNewData(true);
		this.teleop = teleop;
	}


	public boolean isHasBall() {
		return hasBall;
	}


	public void setHasBall(boolean hasBall) {
		setNewData(true);
		this.hasBall = hasBall;
	}


	public boolean isAiming() {
		return aiming;
	}


	public void setAiming(boolean aiming) {
		setNewData(true);
		this.aiming = aiming;
	}


	public boolean isAfterShooting() {
		if(this.afterShooting && System.currentTimeMillis() - afterShootingTime > timeToCelebrate){
			this.afterShooting = false;
		}
		return afterShooting;
	}


	public void setAfterShooting(boolean afterShooting) {
		setNewData(true);
		this.afterShooting = afterShooting;
		if(this.afterShooting){
			afterShootingTime = System.currentTimeMillis();
		}
	}


	public boolean isDrivetrainUp() {
		return drivetrainUp;
	}


	public void setDrivetrainUp(boolean drivetrainUp) {
		setNewData(true);
		this.drivetrainUp = drivetrainUp;
	}


	public boolean isPARTY() {
		return PARTY;
	}


	public void setPARTY(boolean pARTY) {
		setNewData(true);
		PARTY = pARTY;
	}
}

