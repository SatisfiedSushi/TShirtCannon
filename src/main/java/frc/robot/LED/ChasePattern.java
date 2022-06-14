package frc.robot.LED;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

//honestly have no idea if this is just the same as the alternating pattern 
//since I don't have the roborio with me to test it
public class ChasePattern implements tshirtAddressableLEDPatternInterface {
    
    public int milliseconds_per_update;

    private int m_offset;

    private Color[] colors;

    public ChasePattern(Color[] colors, int milliseconds_per_update) {
        this.colors = colors;
        this.milliseconds_per_update = milliseconds_per_update;
    }

    public void setLEDs(AddressableLEDBuffer buffer) {
        int m_SegmentWidth = 3; //this is what the team in the above link had
        int numberOfColors = colors.length;
		int effectiveIndex;
		int colorIndex;
		int bufferLength = buffer.getLength();
		for (int index = 0; index < bufferLength; index++){
			effectiveIndex = (index + m_offset) % bufferLength;
			colorIndex =( index /m_SegmentWidth )% numberOfColors;
			buffer.setLED(effectiveIndex, colors[colorIndex]);
		}

		m_offset =(m_offset+1) %bufferLength;
	}

    public boolean isAnimated() {
        return true;
    }
}
