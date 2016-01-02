package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.util.Dictionary;

import static java.lang.Math.abs;

/**
 * Created by Sahaj on 12/31/2015.
 * This is the teleop for the LSC qualifier
 */
public class TeleQualifierEncoder extends OpMode {

    /*
    Declares motor variables
    */
    DcMotor lfMotor;
    DcMotor lbMotor;
    DcMotor rfMotor;
    DcMotor rbMotor;
    DcMotor bucketArmMotor;
    DcMotor pulleyMotor1;
    /*
    Declares servo variables
    */

    Servo lockServos;                       //Y-splitter controls 2 servos simultaneously
    Servo rackServo;
    Servo depositingFlapLeft;
    Servo depositingFlapRight;




    /*
    Declares servo position variables
    */

    public static final double LOCK_SERVOS_CLOSED_POSITION = 0.1;
    public static final double LOCK_SERVOS_OPEN_POSITION = 0.60;
    public static final double DEPOSITING_FLAP_SERVO_OPEN_POSITION = 0;
    public static final double DEPOSITING_FLAP_SERVO_CLOSED_POSITION = 0;
    public static Boolean onRamp = false;             //should be set to true when on ramp;

    int slideInitialPosition;
    boolean servoLockWasPressed = false, slideUpWasPressed = false, slideDownWasPressed = false;
    boolean isLockClosed = true;
    int slideStage = 0;

    /*change values for slide positions*/
    public static final int SLIDE0 = 0 , SLIDE1 = 500, SLIDE2 = 2000;

    public TeleQualifierEncoder() {
        /*empty*/
    }

    @Override
    public void init(){
        /*
        Links DC Motor objects to hardware locations
         */
        lfMotor = hardwareMap.dcMotor.get("lfMotor");
        lbMotor = hardwareMap.dcMotor.get("lbMotor");
        rfMotor = hardwareMap.dcMotor.get("rfMotor");
        rbMotor = hardwareMap.dcMotor.get("rbMotor");
        bucketArmMotor = hardwareMap.dcMotor.get("armMotor");
        pulleyMotor1 = hardwareMap.dcMotor.get("pulley1");

        lfMotor.setDirection(DcMotor.Direction.REVERSE);
        lbMotor.setDirection(DcMotor.Direction.REVERSE);

        /*
        Links servo objects to hardware locations
         */
        lockServos = hardwareMap.servo.get("lockServos");
        rackServo = hardwareMap.servo.get("rack");
        //depositingFlapLeft = hardwareMap.servo.get("depositLeft");
        //depositingFlapRight = hardwareMap.servo.get("depositRight");

        lockServos.setPosition(LOCK_SERVOS_CLOSED_POSITION);
        //depositingFlapServos.setPosition(DEPOSITING_FLAP_SERVO_CLOSED_POSITION);
        rackServo.setPosition(0.5);

        /*sets certain motors to run with encoders*/
        pulleyMotor1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        /*gets current encoder values to set defaults*/
        slideInitialPosition = pulleyMotor1.getCurrentPosition();

    }

    @Override
    public void loop() {

        /*
        Creates variables for gamepad input
        */
        float leftDrivePwr;
        float rightDrivePwr;
        float bucketArmPwr;
        float pulleyPwr;


        if(gamepad1.y)
            onRamp = true;
        else if(gamepad1.a)
            onRamp = false;

        if(!onRamp) {
            /*
            Gets stick power
            */
            if (abs(gamepad1.left_stick_y) > 0.08) {
                leftDrivePwr = -gamepad1.left_stick_y;
            } else {
                leftDrivePwr = 0;
            }

            if (abs(gamepad1.right_stick_y) > 0.08) {
                rightDrivePwr = -gamepad1.right_stick_y;
            } else {
                rightDrivePwr = 0;
            }
        } else {
            /*
            Gets stick power
            */
            if (abs(gamepad1.left_stick_y) > 0.08) {
                rightDrivePwr = gamepad1.left_stick_y;
            } else {
                rightDrivePwr = 0;
            }

            if (abs(gamepad1.right_stick_y) > 0.08) {
                leftDrivePwr = gamepad1.right_stick_y;
            } else {
                leftDrivePwr = 0;
            }
        }


        if (abs(gamepad2.left_stick_y)>0.08) {
            bucketArmPwr = (float)-0.75*gamepad2.left_stick_y;
        } else {
            bucketArmPwr = 0;
        }

        if (abs(gamepad2.right_stick_y)>0.08) {
            pulleyPwr = -gamepad2.right_stick_y;
        } else {
            pulleyPwr = 0;
        }

        /*
        Scales drivetrain gamepad values
        */
        if (gamepad1.left_bumper && gamepad1.right_bumper){
            leftDrivePwr *= 0.2;
            rightDrivePwr *= 0.2;
        } else if (gamepad1.left_bumper) {
            leftDrivePwr *= 0.4;
            rightDrivePwr *= 0.4;
        }

        /*
        Scales arm motor gamepad values
         */
        if (gamepad2.left_bumper)
            bucketArmPwr *= 0.4;

        /*
        Updates motor power
        */
        lfMotor.setPower(leftDrivePwr);
        lbMotor.setPower(leftDrivePwr);
        rfMotor.setPower(rightDrivePwr);
        rbMotor.setPower(rightDrivePwr);
        bucketArmMotor.setPower(bucketArmPwr);

        telemetry.clearData();
        telemetry.addData("pulleyMotor1", pulleyMotor1.getCurrentPosition());

        pulleyMotor1.setPower(pulleyPwr);

        /*
        Depositing Flap Servo assignment
        */
       /*<--[DELETE TO ACTIVATE CODE] if (gamepad1.b)
            depositingFlapServos.setPosition(DEPOSITING_FLAP_SERVO_OPEN_POSITION);
        else
            depositingFlapServos.setPosition(DEPOSITING_FLAP_SERVO_CLOSED_POSITION);

        /*
        Rack & Pinion Servo assignment
        */
        if (gamepad1.dpad_right)
            rackServo.setPosition(1);
        else if (gamepad1.dpad_left)
            rackServo.setPosition(0);
        else
            rackServo.setPosition(0.5);

        /*
        Climber Servo assignment
        */
        /*<--DELETE TO ACTIVATE CODE if (gamepad1.left_trigger > 0.3)
            climberServo.setPosition(CLIMBER_SERVO_CLOSED_POSITION);
        else if (gamepad1.right_trigger > 0.3)
            climberServo.setPosition(CLIMBER_SERVO_OPEN_POSITION);

        /*
        Lock Servo assignment
        */
        if (gamepad2.x && !servoLockWasPressed) {
            if (isLockClosed){
                lockServos.setPosition(LOCK_SERVOS_OPEN_POSITION);
                isLockClosed = !isLockClosed;
            }
            else {
                lockServos.setPosition(LOCK_SERVOS_CLOSED_POSITION);
                isLockClosed = !isLockClosed;
            }
        }


        /*
        *telemetry code will display output to user
        *updates robot part status
         */
        telemetry.clearData();
        if(lockServos.getPosition() > 0.3)
            telemetry.addData("Lock Servos: " , "locked");
        else
            telemetry.addData("LockServos: ", "NOT locked");

        if(onRamp)
            telemetry.addData("Drive Mode: ", "onRamp");
        else
            telemetry.addData("Drive Mode: ", "ground");


        /*state variables to hold condition of buttons
        *used to check if button was pressed last cycle
         */
        servoLockWasPressed = gamepad2.x;

    }





    @Override
    public void stop() {

    }
}
