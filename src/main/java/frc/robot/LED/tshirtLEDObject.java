package frc.robot.LED;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;


import java.util.Timer;
import java.util.TimerTask;

import frc.robot.Constants;

public class tshirtLEDObject {
    public AddressableLED led_strip;

    public AddressableLEDBuffer led_strip_buffer;

    public tshirtAddressableLEDPatternInterface current_pattern;

    private Timer timer = new Timer();
	private TimerTask task;


    public tshirtLEDObject() {
        led_strip = new AddressableLED(Constants.LED_strip_pwm_port);

        led_strip_buffer = new AddressableLEDBuffer(Constants.LED_strip_length); 
        //^^^ store the "settings" for each LED before it is added to LED Object

        led_strip.setLength(Constants.LED_strip_length);
        led_strip.setData(led_strip_buffer);
		led_strip.start(); //will run output continuously, so you can call setData() without calling start() again
    }
    
    public void setPattern(tshirtAddressableLEDPatternInterface pattern) {
        
        if(pattern != current_pattern) {
            current_pattern = pattern;
            if (task != null) {
                task.cancel();
                task = null;
            }
            if(pattern.isAnimated()) {
                
                //calls updateBufferAndLED() over and over for animated patterns
                task = new TimerTask() {
                    public void run() {
                        updateBufferAndLED();
                    }
                };
                timer.scheduleAtFixedRate(
                        task,
                        0, // time until first occurrence 
                        current_pattern.milliseconds_per_update()
                    ); 
            } else {
                updateBufferAndLED();
            }

           
        }

    }

    //animated patterns will update continuosly by calling this function over and over
    public void updateBufferAndLED () {
        current_pattern.setLEDs(led_strip_buffer); 
        //^^^ uses the common pattern interface to update buffer, 
        //either with different pattern or updating leds of an animated pattern
        
        led_strip.setData(led_strip_buffer);
    }

}

