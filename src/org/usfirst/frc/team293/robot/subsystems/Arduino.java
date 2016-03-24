package org.usfirst.frc.team293.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Arduino extends Subsystem {						//so this sends stuff to the Arduino through i2c
	/******************************CODES*******************************/
	public static final byte
	AutoRed=1,	//red pulsing if on red alliance
	AutoBlue=2,	//blue pulsing if on blue alliance
	TeleopNoBallShooteroff=3,  //yellow
	TeleopNoBallShooteron=4,//pulsing yellow
	ShooterOnCanSeeSwag=5,//green pulsing
	ShooterOnAiming=6,//green flashing
	aftershooting=7,  //Fast Rainbow maybe for a second or two for swag?
	//SHOOTER ON MANUAL AIM NEED TO MAKE!  =8
	
	///Drivetrain Codes   Probabaly only a few strips
	DrivetrainUp=21,	//red pulsing
	DrivetrainDown=22,  //purple pulsing
	
	//all LEDS
	PARTY = 15; //before a match, or when the robot isn't enabled
	
	/*****************************END CODES****************************/
	
	I2C arduino;
	byte[] toSend = new byte[1];
	
	private int arduinoAddress = 8;
	
	public Arduino(){											//initializes the i2c port
		arduino = new I2C(I2C.Port.kOnboard, arduinoAddress);
	}
    
    public void initDefaultCommand() {
        //setDefaultCommand(new MySpecialCommand());
    }
    
    
    public void writeByte(byte toWrite){						//writes 1 byte to the i2c port
    	toSend[0] = toWrite;
    	arduino.transaction(toSend, 1, null, 0);
    }
}

