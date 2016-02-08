package org.usfirst.frc.team293.robot;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;

public class Serial {
	
	  public SerialPort serial;
	public Serial(Port port){
         serial = new SerialPort(115200,port);
         serial.setReadBufferSize(1);//to try data immediately sent to smartdashboard
         this.serial.reset();
	}

    public String getData() {
        	if(serial.getBytesReceived() > 0	){
        		return serial.readString();
        	}else{
        		return "null";
        	}
    }
    public boolean sendData(byte[] buffer) throws Exception {
      
            int count = buffer.length;
            this.serial.write(buffer, count);
            return true;     
    }
    public void reset(){
    	serial.reset();
    }


}