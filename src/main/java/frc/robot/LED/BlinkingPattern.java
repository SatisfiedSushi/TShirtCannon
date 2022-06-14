package frc.robot.LED;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.Timer;

public class BlinkingPattern implements tshirtAddressableLEDPatternInterface {
    
    public double interval; //interval between blinks in seconds
    
    public int milliseconds_per_update = 100; 
    //^^^ how many milliseconds between each update, geared towards scheduling update tasks in tshirtLEDObject.java

    private boolean on = true;
    private double lastChange;

    private tshirtAddressableLEDPatternInterface onPattern;
    private tshirtAddressableLEDPatternInterface offPattern;
    
    //uses two solid color patterns to give blink effect
    public BlinkingPattern(Color onColor, double interval_in_seconds){
		onPattern = new SolidColorPattern(onColor);
		offPattern = new SolidColorPattern(Color.kBlack);
		this.interval = interval_in_seconds;
	}

    @Override
    public void setLEDs(AddressableLEDBuffer buffer) {
        
        double timestamp = Timer.getFPGATimestamp();

		if (timestamp - lastChange > interval){
			on = !on;
			lastChange = timestamp;
		}
		if (on){
			onPattern.setLEDs(buffer); //sets buffer to the solid color pattern
		} else {
			offPattern.setLEDs(buffer); //sets buffer to the "off", or black pattern
		}
    }

    public boolean isAnimated() {
        return true;
    }

    public int milliseconds_per_update() {
        return milliseconds_per_update;
    }
}
