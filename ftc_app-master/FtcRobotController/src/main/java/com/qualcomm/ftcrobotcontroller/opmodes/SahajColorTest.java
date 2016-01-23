package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Sahaj on 1/10/2016.
 */
public class SahajColorTest extends LinearOpMode {

    //modern robotics color sensor
    ColorSensor mrColor;

    @Override
    public void runOpMode() throws InterruptedException {

        mrColor = hardwareMap.colorSensor.get("color");

        //enables led to check if sensor is receiving instructions
        mrColor.enableLed(true);

        waitOneFullHardwareCycle();

        waitForStart();

        //infinite loop
        while (opModeIsActive()) {
            if (gamepad1.x)
                mrColor.enableLed(false);
            else if(gamepad1.b)
                mrColor.enableLed(true);

            telemetry.addData("R: ", mrColor.red());
            telemetry.addData("B: ", mrColor.blue());
            telemetry.addData("G: ", mrColor.green());
            telemetry.addData("light: ", mrColor.alpha());
            telemetry.addData("hue", mrColor.argb());

            waitOneFullHardwareCycle();
        }

    }
}
