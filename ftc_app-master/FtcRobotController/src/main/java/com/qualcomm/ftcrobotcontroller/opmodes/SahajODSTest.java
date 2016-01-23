package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

/**
 * Created by Sahaj on 1/12/2016.
 */
public class SahajODSTest extends LinearOpMode {
    OpticalDistanceSensor ods;

    @Override
    public void runOpMode() throws InterruptedException{

        ods = hardwareMap.opticalDistanceSensor.get("ods");

        waitForStart();
        waitOneFullHardwareCycle();

        while (opModeIsActive()){
            telemetry.addData("Raw Light: ", ods.getLightDetectedRaw());
            telemetry.addData("Light: ", ods.getLightDetected());
        }
    }
}
