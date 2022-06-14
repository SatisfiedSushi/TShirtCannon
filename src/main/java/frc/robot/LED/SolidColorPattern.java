package frc.robot.LED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class SolidColorPattern implements tshirtAddressableLEDPatternInterface {

	private Color color;

	public SolidColorPattern(Color color){
		super();
		this.color = color;
	}

	@Override
	public void setLEDs(AddressableLEDBuffer buffer) {
		
		for (int index = 0; index < buffer.getLength(); index++){
			buffer.setLED(index, color);
		}
		
	}

    public boolean isAnimated() {
        return false;
    }
	
	public int milliseconds_per_update() {
        return 0; //this function should not be called because this isn't an animated pattern
    }
}

