package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
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

    public void turn (double degrees, double power) {
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

    @Override
    public void runOpMode() throws InterruptedException {
        lfMotor = hardwareMap.dcMotor.get("lfMotor");
        lbMotor = hardwareMap.dcMotor.get("lbMotor");
        rfMotor = hardwareMap.dcMotor.get("rfMotor");
        rbMotor = hardwareMap.dcMotor.get("rbMotor");
        gyro = hardwareMap.gyroSensor.get("gyro");
    }
}
