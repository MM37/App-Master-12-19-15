package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by rkhaj on 11/14/2015.
 */
public class EncoderTestRohan extends LinearOpMode {

    DcMotor lfMotor;
    DcMotor lbMotor;
    DcMotor rfMotor;
    DcMotor rbMotor;

    public void moveForwardEncoder(double power, double inches) {
        double ticks = inches * 1220 / (4 * Math.PI);
        byte direction=1;
        if (power<0) {
            direction = -1;
        }
        lfMotor.setTargetPosition(lfMotor.getCurrentPosition() + direction * (int) ticks);
        lbMotor.setTargetPosition(lbMotor.getCurrentPosition() + direction * (int) ticks);
        rfMotor.setTargetPosition(rfMotor.getCurrentPosition() + direction * (int) ticks);
        rbMotor.setTargetPosition(rbMotor.getCurrentPosition() + direction * (int) ticks);
        lfMotor.setPower(power);
        lbMotor.setPower(power);
        rfMotor.setPower(power);
        rbMotor.setPower(power);
    }

    @Override
    public void runOpMode() throws InterruptedException {

        lfMotor = hardwareMap.dcMotor.get("lfMotor");
        lbMotor = hardwareMap.dcMotor.get("lbMotor");
        rfMotor = hardwareMap.dcMotor.get("rfMotor");
        rbMotor = hardwareMap.dcMotor.get("rbMotor");

        lbMotor.setDirection(DcMotor.Direction.REVERSE);
        rfMotor.setDirection(DcMotor.Direction.REVERSE);

        lfMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        lbMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        rfMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        rbMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

        telemetry.addData("Reset Complete", "");

        waitForStart();
        waitOneFullHardwareCycle();


        telemetry.addData("starting position: ", rfMotor.getCurrentPosition());



        //moveForwardEncoder(0.75, 10);

        lfMotor.setTargetPosition(500);
        rfMotor.setTargetPosition(500);

        //telemetry.clearData();
        telemetry.addData("Target", rfMotor.getTargetPosition());

        lfMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rfMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

        lfMotor.setPower(0.75);
        //lbMotor.setPower(0.75);
        rfMotor.setPower(0.75);
        //rbMotor.setPower(0.75);



        telemetry.addData("ending position: ", rfMotor.getCurrentPosition());
    }
}
