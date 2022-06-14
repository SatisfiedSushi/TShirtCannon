package frc.robot.LED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public interface tshirtAddressableLEDPatternInterface {
    
    public int milliseconds_per_update(); //default time between updates, only matters in patterns that are animated

    public void setLEDs(AddressableLEDBuffer buffer);
    public boolean isAnimated();
    
}
