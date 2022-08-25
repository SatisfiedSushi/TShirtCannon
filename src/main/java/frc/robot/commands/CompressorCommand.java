package frc.robot.commands;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Controller;
import frc.robot.subsystems.Compressor;
import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.XboxController;

public class CompressorCommand extends CommandBase{
   
    boolean leftDebounce = false;
    boolean RightDebounce = false;

    XboxController controller;
    Compressor compressor;
    AnalogPotentiometer analog_pressure_sensor;
    boolean compressorState = false;
    int currentCompressorLimit = Constants.defaultCompressorTargetLimit;

    private double getRightTriggerAxis() {
        return 	controller.getRightTriggerAxis();
    }

    private double getLeftTriggerAxis() {
        return 	controller.getLeftTriggerAxis();
    }

    public CompressorCommand(XboxController passedController) {
        controller = passedController;
    }
    
    public double airPressureReading() {
        return analog_pressure_sensor.get();

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (airPressureReading() >= 50 && compressorState == true) { //195 psi is the hardlimit
            compressor.CompressorOff();
            compressorState = false;
        } else if (airPressureReading() >= currentCompressorLimit && compressorState == true) {
            compressor.CompressorOff();
            compressorState = false;
        } else if (compressorState == false) {
            compressor.CompressorOn();
            compressorState = true;
        }

        if (getRightTriggerAxis() >= 0.01 && RightDebounce == false && currentCompressorLimit <= 49) {
            RightDebounce = true;
            currentCompressorLimit = currentCompressorLimit + 1;
        } else if (getRightTriggerAxis() < 0.01 && RightDebounce == true) {
            RightDebounce = false;
        }

        if (getLeftTriggerAxis() >= 0.01 && leftDebounce == false && currentCompressorLimit >= 1) {
            leftDebounce = true;
            currentCompressorLimit = currentCompressorLimit - 1;
        } else if (getLeftTriggerAxis() < 0.01 && leftDebounce == true) {
            leftDebounce = false;
        }
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
