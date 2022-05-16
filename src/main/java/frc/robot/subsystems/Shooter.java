// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {

  DoubleSolenoid pitchSolenoid = null;
  public Shooter() {
    //pitchSolenoid = new DoubleSolenoid(RobotMap.SHOOTER_PITCH_SOLENOID_DEPLOY, RobotMap.SHOOTER_PITCH_SOLENOID_RETRACT);
    // supposed to be new DoubleSolenoid(module, moduleType, forwardChannel, reverseChannel)
    // idk what the parameters are tho
    pcmCompressor.enableDigital();
    System.out.println("compressor initialized");
  }
  private final Compressor pcmCompressor = new Compressor(0, PneumaticsModuleType.CTREPCM);

  public void CompressorOn() {
    pcmCompressor.enableDigital();
  }
  public void CompressorOff() {
    pcmCompressor.disable();
  }

  boolean enabled = pcmCompressor.enabled();
  boolean pressureSwitch = pcmCompressor.getPressureSwitchValue();
  double current = pcmCompressor.getCurrent();

}