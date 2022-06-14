// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.LED;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

import java.util.Random;

/** Add your docs here. */
public class ChaosPattern implements tshirtAddressableLEDPatternInterface {

	private int milliseconds_per_update;

	Color[] colors;

	public ChaosPattern(Color[] colors, int milliseconds_per_update) {
		this.colors = colors;
		this.milliseconds_per_update = milliseconds_per_update;

	}

	@Override
	public void setLEDs(AddressableLEDBuffer buffer) {
		for (int index = 0; index < buffer.getLength(); index++) {
			buffer.setLED(index,randomColor());
		}

	}
	private Color randomColor(){
		Random random = new Random();
    	return colors[random.nextInt(colors.length - 0) + 0];
	}

	public boolean isAnimated() {
		return true;
	}
	@Override
	public int milliseconds_per_update() {
		// TODO Auto-generated method stub
		return milliseconds_per_update;
	}
}