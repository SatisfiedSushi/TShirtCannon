package frc.robot.LED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class AlternatingPattern implements tshirtAddressableLEDPatternInterface {

    public int milliseconds_per_update; //set when pattern is created
    
    private int MaroonAndWhiteBufferCounter = 0;

    private Color[] color_pattern;

    public AlternatingPattern(Color[] color_pattern, int milliseconds_per_update) {
        this.color_pattern = color_pattern;
        this.milliseconds_per_update = milliseconds_per_update;
    }

    public void setLEDs(AddressableLEDBuffer buffer) {
        
        //pattern_counter is used to interate of color_pattern, once pattern counter reaches 7, or end of array, it resets to 0
        int pattern_counter = MaroonAndWhiteBufferCounter;
        for (int index = 0; index < buffer.getLength(); index++) {
            
            buffer.setLED(index, color_pattern[pattern_counter]);

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

    public boolean isAnimated() {
        return true;
    }

    public int milliseconds_per_update() {
        return milliseconds_per_update;
    }

}
