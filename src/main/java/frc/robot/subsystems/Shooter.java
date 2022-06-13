// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//put auto shutting off compressor stuff here

public class Shooter extends SubsystemBase {
  //private Solenoid fireSolenoid;

  private final Compressor pcmCompressor = new Compressor(0, PneumaticsModuleType.CTREPCM);
  public AnalogPotentiometer analog_pressure_sensor;

  private XboxController xbox;

  private WaitCommand wait = new WaitCommand(0.25);

  public int target_pressure = 30;

  public boolean ready = false;
 
  public Shooter() {

    super();
    xbox = RobotContainer.controller;

    analog_pressure_sensor = new AnalogPotentiometer(0, 250, -25);

    //fireSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    pcmCompressor.enableDigital();
    System.out.println("compressor initialized");
  }
  
  @Override
  public void periodic() {
    super.periodic();

    if (xbox.getXButton()) {
      fire();
    }
    if (xbox.getYButton()) {
      closeSolenoid();
    }
    if (xbox.getBButton()) {
      CompressorOff();
    }
    if (xbox.getAButton()) {
      CompressorOn();
    }
    if (xbox.getLeftBumperPressed()) {
      target_pressure += 5;
    }
    if (xbox.getRightBumperPressed()) {
      target_pressure -= 5;
    }
    if(target_pressure >= airPressureReading()) {
      CompressorOff();
      if(!overTargetPressure()) {
        ready = true; //ready to fire!
      }
    }

    SmartDashboard.putNumber("Target Pressure", target_pressure);
    SmartDashboard.putNumber("Air Pressure Reading", airPressureReading());
  }

  public double airPressureReading() {
    return analog_pressure_sensor.get();
  }

  public boolean overTargetPressure() {
    if (airPressureReading() >= target_pressure + 5) { 
      //the analog air pressure sensor can very around 1-2 psi of accuracy 
      //when the compressor is on, and the compressor might be automatically shutoff 1-2 psi above the target pressure
      //that's fine, and the actual shouldn't be 5 psi above the target pressure anyway, 
      //but if it does happen to be, for whatever other reason, we can give a little warning
      return true;
    } else {
      return false;
    }
  }

  public void fire() {
    //fireSolenoid.set(true);
    wait.initialize();
    //fireSolenoid.set(false);
  }

  public void closeSolenoid() {
    //fireSolenoid.set(false);
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

}