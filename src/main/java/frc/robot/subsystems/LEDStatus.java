package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.RobotContainer;

public class LEDStatus extends SubsystemBase {
    
    private XboxController xbox;

    private Shooter shooter;

    private LEDs m_leds;

    public LEDStatus () {
        super();
        xbox = RobotContainer.controller;
        shooter = RobotContainer.shooter;

        m_leds = new LEDs();

    }

    @Override
    public void periodic() {

        super.periodic();

        //controls for when the robot is moving
        if (xbox.getLeftY() != 0 || xbox.getRightX() != 0) {
            m_leds.BlinkingPattern(Color.kRed);
            m_leds.setPattern();
        } else if (shooter.CompressorStatus()) {  //when air is being loaded
            m_leds.AlternatingMaroonAndWhiteLEDBuffer();
            m_leds.setAnimatedPattern();
        } else if (shooter.ready && !shooter.overTargetPressure()) { //ready to fire!
            m_leds.BlueAndMaroonChaseLEDBuffer();
            m_leds.setAnimatedPattern();
        } else if (shooter.overTargetPressure()) {  //when there more air than you want
            m_leds.BlinkingPattern(Color.kYellow);
            m_leds.setPattern();
        } else if (!shooter.CompressorStatus() && !shooter.overTargetPressure() && !shooter.ready) {
            //when robot is not filled to target pressure and the compressor isn't on
            m_leds.BlinkingPattern(Color.kLightPink);
            m_leds.setPattern();
        }
    
        

    }
}
