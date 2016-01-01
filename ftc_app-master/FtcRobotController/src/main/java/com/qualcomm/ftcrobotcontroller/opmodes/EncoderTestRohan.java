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
        lfMotor.setPower(power);
        lbMotor.setPower(power);
        rfMotor.setPower(power);
        rbMotor.setPower(power);
        lfMotor.setTargetPosition(lfMotor.getCurrentPosition() + direction * (int) ticks);
        lbMotor.setTargetPosition(lbMotor.getCurrentPosition() + direction * (int) ticks);
        rfMotor.setTargetPosition(rfMotor.getCurrentPosition() + direction * (int) ticks);
        rbMotor.setTargetPosition(rbMotor.getCurrentPosition() + direction * (int) ticks);
    }

    @Override
    public void runOpMode() throws InterruptedException {

        lfMotor = hardwareMap.dcMotor.get("lfMotor");
        lbMotor = hardwareMap.dcMotor.get("lbMotor");
        rfMotor = hardwareMap.dcMotor.get("rfMotor");
        rbMotor = hardwareMap.dcMotor.get("rbMotor");

        lfMotor.setDirection(DcMotor.Direction.REVERSE);
        lbMotor.setDirection(DcMotor.Direction.REVERSE);

        lfMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        lbMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rfMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rbMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        for(int i = 0; i < 15; i++) {
            waitOneFullHardwareCycle();
            telemetry.addData("cycle", i);
        }

        waitForStart();

        lfMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        lbMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rfMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rbMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        telemetry.addData("lf", lfMotor.getCurrentPosition());
        telemetry.addData("lb", lbMotor.getCurrentPosition());
        telemetry.addData("rf", rfMotor.getCurrentPosition());
        telemetry.addData("rb", rbMotor.getCurrentPosition());

        rfMotor.setPower(0.5);
        rfMotor.setTargetPosition(1100);

        /*lfMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        lbMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rfMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rbMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        while(true) {
            telemetry.clearData();
            telemetry.addData("lf", lfMotor.getCurrentPosition());
            telemetry.addData("rf", rfMotor.getCurrentPosition());
        }*/
    }
}