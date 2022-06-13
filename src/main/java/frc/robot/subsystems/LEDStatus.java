package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.util.Color;

import frc.robot.RobotContainer;

public class LEDStatus extends SubsystemBase {
    
    private XboxController xbox;

    private Shooter shooter;

    private LEDs m_leds = new LEDs();


    public LEDStatus () {
        super();
        xbox = RobotContainer.controller;
        shooter = RobotContainer.shooter;
       // m_leds.setSolidColor(Color.kRed);
       // m_leds.setPattern();

        
        
    }
    
    @Override
    public void periodic() {

        
        super.periodic();

        m_leds.setAnimatedPattern();
        m_leds.BlueAndMaroonChaseLEDBuffer();
       
        //controls for when the robot is moving
        
        /* if (xbox.getLeftY() != 0 || xbox.getRightX() != 0) {
            m_leds.BlinkingPattern(Color.kRed);
            //m_leds.setSolidColor(Color.kGreen);
            m_leds.setAnimatedPattern();
        } else  if (
                shooter.CompressorStatus()
            ) {  //when air is being loaded
            m_leds.AlternatingMaroonAndWhiteLEDBuffer();
            m_leds.setAnimatedPattern();
        } else if (shooter.ready && !shooter.overTargetPressure()) { //ready to fire!
            m_leds.BlueAndMaroonChaseLEDBuffer();
            m_leds.setPattern();
        } else if (shooter.overTargetPressure()) { //when there more air than you want
            m_leds.setSolidColor(Color.kYellow);
            m_leds.setPattern();
        } else if (!shooter.CompressorStatus() && !shooter.overTargetPressure() && !shooter.ready) {
             //when robot is not filled to target pressure and the compressor isn't on
            m_leds.setSolidColor(Color.kLightPink);
            m_leds.setPattern();
        }
        */
        

    }
}
