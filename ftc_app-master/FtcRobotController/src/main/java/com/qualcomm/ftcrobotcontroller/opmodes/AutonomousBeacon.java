package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by rkhaj on 1/22/2016.
 */
public class AutonomousBeacon extends LinearOpMode {
    DcMotor lfMotor;
    DcMotor lbMotor;
    DcMotor rfMotor;
    DcMotor rbMotor;
    GyroSensor gyro;

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

        waitForStart();

        move(0.5, 20);
        turn(0.5, 45);
        move(0.5, 80);
        turn(0.5, 45);
    }
}
