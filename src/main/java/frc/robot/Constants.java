// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    // Motor Ids
    public static int motor_right1 = 0;
    public static int motor_right2 = 1;
    public static int motor_left1 = 3;
    public static int motor_left2 = 2;

    // Encoder stuffs
    public static double driveGearing = 1.0/60.0;

    // Math & physics
    public static double Patm = 1.0; // atmospheric pressure in atm
    public static double A = 1.0; // cross-sectional area of the barrel in meters^2
    public static double L = 1.0; // length of the barrel in meters
    public static double V0 = 5.0; // volume of air tank in liters
    public static double m = 0.1; // mass of projectile (t-shirt) in kg

    // LED strip
    public static int LED_strip_pwm_port = 0;
    public static int LED_strip_length = 300;
}
