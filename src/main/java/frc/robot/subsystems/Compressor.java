package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;

public class Compressor extends SubsystemBase {
    TalonSRX compressor;

    public Compressor() {
        compressor = new TalonSRX(5);
        compressor.configPeakCurrentLimit(23); // so we don't kill the $900 solenoid :)
        compressor.enableVoltageCompensation(true);
        compressor.enableCurrentLimit(true);
    }

    public void CompressorOn() {
        compressor.set(TalonSRXControlMode.PercentOutput, 1.0);
    }

    public void CompressorOff() {
        compressor.set(TalonSRXControlMode.PercentOutput, 0);
    }
}
