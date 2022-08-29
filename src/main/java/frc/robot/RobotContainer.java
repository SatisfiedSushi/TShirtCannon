// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.*;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public static XboxController controller;
  private DriveTrain driveTrain;
  public static Shooter shooter;
  public LEDStatus leds;
  private ParallelCommandGroup teleopCommand;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    controller = new XboxController(0);

    driveTrain = new DriveTrain();

    shooter = new Shooter();

    leds = new LEDStatus();
    
    teleopCommand = new ParallelCommandGroup(
      new DriveCommand(driveTrain, ()-> -controller.getLeftY(), ()-> controller.getRightX(), ()-> controller.getRawButton(1))
      //new ShootCommand(shooter, controller.getXButton(), controller.getYButton(), controller.getAButton(), controller.getBButton())
    );
  }
  
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous mode
   */
  
  /**
   * Use this to pass the teleop command to the main {@link Robot} class.
   * 
   * @return the command to run in teleoperated mode
   */
  public Command getTeleopCommand() {
    return teleopCommand;
  }
}