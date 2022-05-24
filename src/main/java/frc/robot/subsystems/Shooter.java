// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Constants;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {
  DoubleSolenoid pitchSolenoid;
  Solenoid fireSolenoid;
  
  public Shooter() {
    //pitchSolenoid = new DoubleSolenoid(RobotMap.SHOOTER_PITCH_SOLENOID_DEPLOY, RobotMap.SHOOTER_PITCH_SOLENOID_RETRACT);
    // supposed to be new DoubleSolenoid(module, moduleType, forwardChannel, reverseChannel)
    // idk what the parameters are tho
    pitchSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 2);
    fireSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
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

  /**
   * Calculates the required velocity to launch a projectile a given distance.
   * @param dx Horizontal distance in m
   * @param dy Vertical distance in m
   * @param theta Angle of cannon in RADIANS!!
   * @return Velocity in m/s^2
   */
  public double calculateVelocity(double dx, double dy, double theta) {
    return dx / (Math.cos(theta) * Math.sqrt(-(dy - dx * Math.tan(theta)) / 4.9));
  }

  /**
   * Calculates the required pressure to launch projectile at a given velocity.
   * @param v Desired velocity of projectile in m/s^2
   * @return Pressure needed to achieve this velocity in atm
   */
  public double calculatePressure(double v) {
    return (Math.pow(v,2)*Constants.m / 2 + Constants.A * Constants.L * Constants.Patm) / (Constants.V0*Math.log(1 + Constants.A * Constants.L / Constants.V0));
  }
  //both of these equations do ignore friction so they might not be 100% accurate
  
  /**
   * @param atm Pressure in atmospheres
   * @return Pressure in psi (pound-force per square inch)
   */
  public double pressureAtmToPsi(double atm) {
    return atm * 14.6959;
  }

  boolean enabled = pcmCompressor.enabled();
  boolean pressureSwitch = pcmCompressor.getPressureSwitchValue();
  double current = pcmCompressor.getCurrent();

}