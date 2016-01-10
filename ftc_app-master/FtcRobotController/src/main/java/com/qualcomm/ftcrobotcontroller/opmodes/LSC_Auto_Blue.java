package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by sahaj on 1/9/2015.
 */
public class LSC_Auto_Blue extends LinearOpMode {

    DcMotor lfMotor;
    DcMotor lbMotor;
    DcMotor rfMotor;
    DcMotor rbMotor;
    Servo climberFlap;

    public static final double CLIMBER_UP_POSITION = 1.0;
    public static final double CLIMBER_DOWN_POSITION = 0;

    public void turn(double power, int degrees) throws InterruptedException {
        double ticks = degrees * (1750 / 90);
        lfMotor.setPower(power);
        lbMotor.setPower(power);
        rfMotor.setPower(power);
        rbMotor.setPower(power);

        lfMotor.setTargetPosition(lfMotor.getCurrentPosition() + (int) ticks);
        lbMotor.setTargetPosition(lbMotor.getCurrentPosition() + (int) ticks);
        rfMotor.setTargetPosition(rfMotor.getCurrentPosition() - (int) ticks);
        rbMotor.setTargetPosition(rbMotor.getCurrentPosition() - (int) ticks);
        //while (lfMotor.getCurrentPosition() != test + (int) ticks) {}
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
        climberFlap = hardwareMap.servo.get("flapLeft");

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

        telemetry.addData("lf", lfMotor.getCurrentPosition());
        telemetry.addData("lb", lbMotor.getCurrentPosition());
        telemetry.addData("rf", rfMotor.getCurrentPosition());
        telemetry.addData("rb", rbMotor.getCurrentPosition());

        climberFlap.setPosition(CLIMBER_UP_POSITION);

        waitForStart();

        sleep(10000);

        telemetry.clearData();

        //First move to zone area
        move(0.5, 87.50);
        sleep(7000);
        //turn away from zone area
        turn(0.30, 500);        /*need to edit*/
        sleep(4000);

        //moves into zone
        move(0.25, -3);        /*need to edit*/
        sleep(2500);

        climberFlap.setPosition(CLIMBER_UP_POSITION);
        sleep(2000);
        climberFlap.setPosition(CLIMBER_DOWN_POSITION);
        sleep(2000);
        climberFlap.setPosition(CLIMBER_UP_POSITION);
        sleep(1000);
        climberFlap.setPosition(CLIMBER_DOWN_POSITION);
        sleep(1000);
        climberFlap.setPosition(CLIMBER_UP_POSITION);
        sleep(1000);
        climberFlap.setPosition(CLIMBER_DOWN_POSITION);
        sleep(1000);
        climberFlap.setPosition(CLIMBER_UP_POSITION);
        sleep(1000);

    }
}
