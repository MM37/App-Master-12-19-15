package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Sahaj on 12/28/2015.
 */



public class EncoderTestSahaj extends LinearOpMode {
    DcMotor lfMotor, lbMotor, rfMotor, rbMotor;
    private static final int TARGET = 5000;

    @Override
    public void runOpMode() throws InterruptedException {
        //mapping the motors
        lfMotor = hardwareMap.dcMotor.get("lfMotor");
        lbMotor = hardwareMap.dcMotor.get("lbMotor");
        rfMotor = hardwareMap.dcMotor.get("rfMotor");
        rbMotor = hardwareMap.dcMotor.get("rbMotor");

        lfMotor.setDirection(DcMotor.Direction.REVERSE);
        lbMotor.setDirection(DcMotor.Direction.REVERSE);

        rfMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rfMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        //this is where robot movement starts
        waitOneFullHardwareCycle();
        waitForStart();

        //MAKE SURE TO INSERT 10 SECOND WAIT!!!!

        while (rfMotor.getCurrentPosition() < TARGET){
            rfMotor.setPower(.5);
            rbMotor.setPower(.5);
            lfMotor.setPower(.5);
            lbMotor.setPower(.5);
            telemetry.clearData();
            telemetry.addData("rfMotor Position", rfMotor.getCurrentPosition());
        }

        rfMotor.setPower(0);
        rbMotor.setPower(0);
        lfMotor.setPower(0);
        lbMotor.setPower(0);

        telemetry.addData("Success", "");


    }
}

