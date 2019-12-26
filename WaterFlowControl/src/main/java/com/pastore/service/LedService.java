package com.pastore.service;

import org.springframework.stereotype.Service;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

@Service
public class LedService {

	private GpioPinDigitalOutput pin;
	GpioController gpio = null;

	public String lightOn() {
		String status = "";

		if (pin == null) {
			try {

				gpio = GpioFactory.getInstance();
				pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.LOW);
				pin.setShutdownOptions(true, PinState.HIGH, PinPullResistance.OFF);
				pin.toggle();
				if (pin.isLow()) {
					status = "on";
				} else {
					status = "off";
				}

			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}

		return status;
	}

	public String lightOff() {
		if (pin != null) {
			gpio.shutdown();
			gpio.unprovisionPin(pin);
			pin = null;
		}

		return "off";
	}
}
