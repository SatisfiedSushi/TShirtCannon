// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Compressor;
<<<<<<< Updated upstream
import edu.wpi.first.wpilibj.AnalogPotentiometer;
=======
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;


import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
>>>>>>> Stashed changes
import frc.robot.Constants;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

//put auto shutting off compressor stuff here

public class Shooter extends SubsystemBase {
<<<<<<< Updated upstream
  //private Solenoid fireSolenoid;

  private final Compressor pcmCompressor = new Compressor(0, PneumaticsModuleType.CTREPCM);
  public AnalogPotentiometer analog_pressure_sensor;

  public TalonSRX fireSolenoid;

  private XboxController xbox;

  private WaitCommand wait = new WaitCommand(0.25);

  public int target_pressure = 30;

  public boolean ready = false;
 
  public Shooter() {

    super();
    xbox = RobotContainer.controller;

    analog_pressure_sensor = new AnalogPotentiometer(0, 250, -25);

    //fireSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    fireSolenoid = new TalonSRX(Constants.motor_solenoid);
    fireSolenoid.configPeakCurrentLimit(2); // so we don't kill the $900 solenoid :)
    fireSolenoid.enableCurrentLimit(true); // config stuff like this varies between motor controllers like Spark Max and Talons, double check documentation ig
    
    pcmCompressor.enableDigital();
    System.out.println("compressor initialized");
=======
  DoubleSolenoid pitchSolenoid;
  TalonSRX fireSolenoid; // we're powering the solenoid with a motor controller since it needs more power than the PCM can give
  double scale = 250;
  double offset = -27;
  AnalogPotentiometer pressureTransducer = new AnalogPotentiometer(0, scale, offset);
  private Compressor pcmCompressor = new Compressor(9, PneumaticsModuleType.CTREPCM);

  public Shooter() {
    pitchSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 2); // unused
    fireSolenoid = new TalonSRX(5);
    fireSolenoid.configPeakCurrentLimit(2); // so we don't kill the $900 solenoid :)
    fireSolenoid.enableCurrentLimit(true); // config stuff like this varies between motor controllers like Spark Max and Talons, double check documentation ig
    pcmCompressor.enableDigital(); // you may also need to switch the compressor to a motor controller if they put a more powerful one
    // compressor ran by motor controller will need you to manually shut it off in code when the pressure reaches a certain psi, so mechanical needs to install a sensor
    // since we can't rely on the PCM to automatically stop the compressor at 120psi
    // (theres a reason running these things off motor controllers isnt FRC legal lol)
>>>>>>> Stashed changes
  }
  

  public void closeSolenoid() {
    fireSolenoid.set(TalonSRXControlMode.PercentOutput, 0.0); // 1.0 and 0.0 opens and closes the solenoid respectively
  }

  public boolean CompressorStatus() {
    return pcmCompressor.enabled();
  }

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

<<<<<<< Updated upstream
=======
  public double analogPressureInputToPSI() {

    double psi = pressureTransducer.get();
    return psi;
  }

  


  // idk what these are for but they probably arent needed
  boolean enabled = pcmCompressor.enabled();
  boolean pressureSwitch = pcmCompressor.getPressureSwitchValue();
  double current = pcmCompressor.getCurrent();
  PneumaticHub hub = new PneumaticHub();
  double pressure = hub.getPressure(0);

>>>>>>> Stashed changes
}