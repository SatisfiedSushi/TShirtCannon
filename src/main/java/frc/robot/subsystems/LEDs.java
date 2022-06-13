package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.Timer;
import java.util.TimerTask;

import frc.robot.Constants;

//This could totally be improved to be more in line with what Team 5013 did with making different classes for different led buffers, 
//I kind of just slapped this together in one class

public class LEDs {
    public AddressableLED led_strip;

    public AddressableLEDBuffer led_strip_buffer;

    private java.util.Timer timer = new java.util.Timer();
	private TimerTask task;

    private int milliseconds_per_update;

    public LEDs () {
        led_strip = new AddressableLED(Constants.LED_strip_pwm_port);
        led_strip_buffer = new AddressableLEDBuffer(Constants.LED_strip_length);
    }
    
    //with help from https://github.com/FRC-5013-Park-Hill-Robotics/5013-RapidReact/blob/b6f5d2a92cf5d6f639c43996f79c8823e12cce8f/src/main/java/frc/robot/trobot5013lib/led/TrobotAddressableLED.java#L53
    public void setAnimatedPattern() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        task = new TimerTask() {
            public void run() {
                led_strip.setData(led_strip_buffer);
            }
        };
        timer.scheduleAtFixedRate(
                task,
                20, // run first occurrence in 20ms
                milliseconds_per_update //updates every 20ms
            ); 
    }

    public void setPattern() {
        led_strip.setData(led_strip_buffer);
    }
    

    //for AlternatingMaroonAndWhiteLEDBuffer()
    private int MaroonAndWhiteBufferCounter = 0;

    public void AlternatingMaroonAndWhiteLEDBuffer() {
        milliseconds_per_update = 20;

        //below gives a pattern of four maroon leds, then four light gray ones, then four maroon, and so on
        Color[] color_pattern = {Color.kMaroon, Color.kMaroon, Color.kMaroon, Color.kMaroon, Color.kLightGray, Color.kLightGray, Color.kLightGray, Color.kLightGray}; 
        
        //pattern_counter is used to interate of color_pattern, once pattern counter reaches 7, or end of array, it resets to 0
        int pattern_counter = MaroonAndWhiteBufferCounter;
        for (int index = 0; index < led_strip_buffer.getLength(); index++) {
            
            led_strip_buffer.setLED(index, color_pattern[0]);

            if (pattern_counter >= 7) {
                pattern_counter = 0;
            } else {
                pattern_counter++;
            }
        
        } 
        //MaroonAndWhiteBufferCounter is used to give an animated look. 
        //Everytime this function is called, MaroonAndWhiteBufferCounter is used to tell on which part of the array pattern_counter should start
        if (MaroonAndWhiteBufferCounter >= 7) {
            MaroonAndWhiteBufferCounter = 0;
        } else {
            MaroonAndWhiteBufferCounter++;
        }
    }


    //for BlueAndMaroonChaseLEDBuffer
    private int m_offset;

    //"borrowed" from https://github.com/FRC-5013-Park-Hill-Robotics/5013-RapidReact/blob/main/src/main/java/frc/robot/trobot5013lib/led/ChasePattern.java
    public void BlueAndMaroonChaseLEDBuffer() {
        milliseconds_per_update = 20;
		Color[] color_pattern = {Color.kMaroon, Color.kLightSkyBlue};
        int m_SegmentWidth = 3; //this is what the team in the above link had
        int numberOfColors = color_pattern.length;
		int effectiveIndex;
		int colorIndex;
		int bufferLength = led_strip_buffer.getLength();
		for (int index = 0; index < bufferLength; index++){
			effectiveIndex = (index + m_offset) % bufferLength;
			colorIndex =( index /m_SegmentWidth )% numberOfColors;
			led_strip_buffer.setLED(effectiveIndex, color_pattern[colorIndex]);
		}

		m_offset =(m_offset+1) %bufferLength;
	}

    private boolean on = true;
    public double interval = 0.5;
    private double lastChange;
    //"borrowed" from https://github.com/FRC-5013-Park-Hill-Robotics/5013-RapidReact/blob/main/src/main/java/frc/robot/trobot5013lib/led/ChasePattern.java
    public void BlinkingPattern(Color color) {
        milliseconds_per_update = 500;
        double timestamp = Timer.getFPGATimestamp();

		if (timestamp- lastChange > interval){
			on = !on;
			lastChange = timestamp;
		}
		if (on){
			setSolidColor(color);
		} else {
			setSolidColor(Color.kBlack);
		}
    }

    public void setSolidColor(Color color) {
        for (int index = 0; index < led_strip_buffer.getLength(); index++){ 
            led_strip_buffer.setLED(index, color);
        }
    }

}
