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

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//put auto shutting off compressor stuff here

public class Shooter extends SubsystemBase {
  TalonSRX fireSolenoid; // we're powering the solenoid with a motor controller since it needs more power than the PCM can give
  

  private final Compressor pcmCompressor = new Compressor(Constants.CompressorID, PneumaticsModuleType.CTREPCM);
  public AnalogPotentiometer analog_pressure_sensor;

  private XboxController xbox;

  private WaitCommand wait = new WaitCommand(0.250);

  public int target_pressure = 30;

  public boolean ready = false;
 
  public Shooter() {

    super();

    fireSolenoid = new TalonSRX(5);
    fireSolenoid.configPeakCurrentLimit(2); // so we don't kill the $900 solenoid :)
    fireSolenoid.enableCurrentLimit(true); // config stuff like this varies between motor controllers like Spark Max and Talons, double check documentation ig
    
    CompressorOff(); //so compressor doesn't automatically start

    xbox = RobotContainer.controller;

    analog_pressure_sensor = new AnalogPotentiometer(0, 250, -25);

    //fireSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    //pcmCompressor.enableDigital();
    System.out.println("compressor initialized");
  }
  
  @Override
  public void periodic() {
    super.periodic();

    if(xbox.getAButton()) {
      CompressorOn();
    }
    if(xbox.getBButton()) {
      CompressorOff();
    }

    if(xbox.getXButton()) {
      fire();
    }
    if(xbox.getYButton()) {
      closeSolenoid();
    }

    if (xbox.getLeftBumperPressed()) {
      target_pressure += 5;
    }
    if (xbox.getRightBumperPressed()) {
      target_pressure -= 5;
    }
    if(target_pressure <= airPressureReading()) {
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
    fireSolenoid.set(TalonSRXControlMode.PercentOutput, 1.0); // 1.0 and 0.0 opens and closes the solenoid respectively
    //wait.initialize();
    //fireSolenoid.set(TalonSRXControlMode.PercentOutput, 0.0); // 1.0 and 0.0 opens and closes the solenoid respectively
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

}