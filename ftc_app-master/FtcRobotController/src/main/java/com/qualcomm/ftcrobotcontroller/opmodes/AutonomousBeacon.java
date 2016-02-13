package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by rkhaj on 1/22/2016.
 */
public class AutonomousBeacon extends LinearOpMode {
    DcMotor lfMotor;
    DcMotor lbMotor;
    DcMotor rfMotor;
    DcMotor rbMotor;
    GyroSensor gyro;
    OpticalDistanceSensor ods;
    TouchSensor touch;
    public final double halfValue = 0.5;
    public final double adjuster = 0.6;

    public void turn (double power, double degrees) {
        final double gyroZero = gyro.getRotation();
        double lastTime;
        double degreesTurned = 0;
        byte direction = 1;
        if (degrees < 0)
            direction = -1;

        lfMotor.setPower(power * direction);
        lbMotor.setPower(power * direction);
        rfMotor.setPower(-power * direction);
        rbMotor.setPower(-power * direction);

        while(Math.abs(degreesTurned) < degrees) {
            lastTime = System.currentTimeMillis();
            if(Math.abs(gyro.getRotation() - gyroZero) >=5 )
                degreesTurned += ((gyro.getRotation()-gyroZero)*(System.currentTimeMillis()-lastTime)/1000);
        }

        lfMotor.setPower(0);
        lbMotor.setPower(0);
        rfMotor.setPower(0);
        rbMotor.setPower(0);
    }

    public void move(double power, double inches) {
        double ticks = inches * 1220 / (4 * Math.PI);
        lfMotor.setPower(power);
        lbMotor.setPower(power);
        rfMotor.setPower(power);
        rbMotor.setPower(power);

        lfMotor.setTargetPosition(lfMotor.getCurrentPosition() + (int) ticks);
        lbMotor.setTargetPosition(lbMotor.getCurrentPosition() + (int) ticks);
        rfMotor.setTargetPosition(rfMotor.getCurrentPosition() + (int) ticks);
        rbMotor.setTargetPosition(rbMotor.getCurrentPosition() + (int) ticks);
    }

    @Override
    public void runOpMode() throws InterruptedException{
        lfMotor = hardwareMap.dcMotor.get("lfMotor");
        lbMotor = hardwareMap.dcMotor.get("lbMotor");
        rfMotor = hardwareMap.dcMotor.get("rfMotor");
        rbMotor = hardwareMap.dcMotor.get("rbMotor");
        gyro = hardwareMap.gyroSensor.get("gyro");
        ods = hardwareMap.opticalDistanceSensor.get("ods");
        touch = hardwareMap.touchSensor.get("touch");

        lfMotor.setDirection(DcMotor.Direction.REVERSE);
        lbMotor.setDirection(DcMotor.Direction.REVERSE);

        lfMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        lbMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rfMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rbMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        for(int i=0; i<90; i++){
            waitOneFullHardwareCycle();
            telemetry.addData("cycle", i);
        }

        lfMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        lbMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rfMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rbMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        boolean isRed = true;
        String alliance = "Red";
        int customWaitTime = 10;
        boolean ready = false;
        boolean dpadWasPressed = false;

        while(!ready) {
            telemetry.clearData();
            telemetry.addData("Select Alliance Color", "B for Red, X for Blue");
            telemetry.addData("Selected Alliance Color", alliance);
            telemetry.addData("", "");
            telemetry.addData("Select Start Wait Time", "D-Pad Up to Increse, D-Pad Down to Decrease");
            telemetry.addData("Selected Start Wait Time", customWaitTime);
            telemetry.addData("", "");
            telemetry.addData("Press A to End Selection", "");

            if (gamepad1.b) {
                isRed = true;
                alliance = "Red";
            } else if (gamepad1.x) {
                isRed = false;
                alliance = "Blue";
            }

            if (gamepad1.dpad_up && !dpadWasPressed)
                customWaitTime++;
            else if (gamepad1.dpad_down && !dpadWasPressed)
                customWaitTime--;

            if (gamepad1.a)
                ready = true;

            dpadWasPressed = gamepad1.dpad_up || gamepad1.dpad_down;
        }

        waitForStart();
        waitOneFullHardwareCycle();

        wait(customWaitTime*1000);

        move(0.5, 20);
        turn(0.5, 45);
        move(0.5, 80);
        turn(0.5, 45);

        lfMotor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        lbMotor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        rfMotor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        rbMotor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        while(ods.getLightDetected()<halfValue) {
            lfMotor.setPower(-50);
            lbMotor.setPower(-50);
            rfMotor.setPower(50);
            rbMotor.setPower(50);
        }

        while(!touch.isPressed()) {
            lfMotor.setPower(50 + (ods.getLightDetected()-halfValue)*adjuster);
            lbMotor.setPower(50 + (ods.getLightDetected()-halfValue)*adjuster);
            rfMotor.setPower(50 - (ods.getLightDetected()-halfValue)*adjuster);
            rbMotor.setPower(50 - (ods.getLightDetected()-halfValue) *adjuster);
        }

        lfMotor.setPower(0);
        lbMotor.setPower(0);
        rfMotor.setPower(0);
        rbMotor.setPower(0);
    }
}
