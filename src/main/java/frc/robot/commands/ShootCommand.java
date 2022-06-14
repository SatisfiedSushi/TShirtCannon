package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;


public class ShootCommand extends CommandBase {
    
    private boolean solenoidOpen, solenoidClose, compressorOn, compressorOff;
    private Shooter shooter_object;

    public ShootCommand (Shooter shooter, boolean solenoidOpen, boolean solenoidClose, boolean compressorOn, boolean compressorOff) {
        this.solenoidOpen = solenoidOpen;
        this.solenoidClose = solenoidClose;
        this.compressorOn = compressorOn;
        this.compressorOff = compressorOff;
        shooter_object = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if(solenoidOpen) {
            shooter_object.fire();
        } else if (solenoidClose) {
            shooter_object.closeSolenoid();
        }
        if(compressorOn) {
            shooter_object.CompressorOn();
        } else if(compressorOff) {
            shooter_object.CompressorOff();
        }

    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }

}
