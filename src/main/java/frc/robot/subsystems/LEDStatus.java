package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.util.Color;

import frc.robot.RobotContainer;
import frc.robot.LED.*;

public class LEDStatus extends SubsystemBase {
    
    private XboxController xbox;

    private Shooter shooter;

    private tshirtLEDObject leds = new tshirtLEDObject();
    
    //below gives a pattern of four maroon leds, then four light gray ones, then four maroon, and so on
    Color[] alternating_pattern = {Color.kMaroon, Color.kMaroon, Color.kMaroon, Color.kMaroon, Color.kLightGray, Color.kLightGray, Color.kLightGray, Color.kLightGray}; 
    
    Color[] chase_colors = {Color.kMaroon, Color.kBlue};

    Color[] chaos_colors = {Color.kMaroon, Color.kBlue, Color.kBlack};

    private tshirtAddressableLEDPatternInterface BlueAndMaroonChasePattern = new ChasePattern(chase_colors, 600);
    private tshirtAddressableLEDPatternInterface MaroonAndWhiteAlternatingPattern = new AlternatingPattern(alternating_pattern, 100);
    private tshirtAddressableLEDPatternInterface ChaosPattern = new ChaosPattern(chaos_colors, 50);
    private tshirtAddressableLEDPatternInterface CoralBlinkingPattern = new BlinkingPattern(Color.kCoral, 0.5);
    private tshirtAddressableLEDPatternInterface YellowBlinkingPattern = new BlinkingPattern(Color.kYellow, 1);
    private tshirtAddressableLEDPatternInterface RedBlinkingPattern = new BlinkingPattern(Color.kRed, 0.5);


    public LEDStatus () {
        super();
        xbox = RobotContainer.controller;
        shooter = RobotContainer.shooter;
        
    }
    
    @Override
    public void periodic() {

       
        //controls for when the robot is moving (doesn't seem to work as of now) 
        /* if (xbox.getLeftY() != 0 || xbox.getRightX() != 0) {
            m_leds.BlinkingPattern(Color.kRed);
            //m_leds.setSolidColor(Color.kGreen);
            m_leds.setAnimatedPattern();
        } else */  
        if (shooter.CompressorStatus()) {  //when air is being loaded
            leds.setPattern(MaroonAndWhiteAlternatingPattern);
        } else if (shooter.ready && !shooter.overTargetPressure()) { //ready to fire!
            //leds.setPattern(BlueAndMaroonChasePattern);
            leds.setPattern(ChaosPattern);
        } else if (shooter.overTargetPressure()) { //when there more air than you want
            leds.setPattern(YellowBlinkingPattern);
        } else if (!shooter.CompressorStatus() && !shooter.overTargetPressure() && !shooter.ready) {
            //when robot is not filled to target pressure or over target pressure and the compressor isn't on
            //^^^ (idle basically)
            leds.setPattern(CoralBlinkingPattern);
        }

    }
}
