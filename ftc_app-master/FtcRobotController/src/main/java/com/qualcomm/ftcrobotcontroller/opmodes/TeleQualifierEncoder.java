package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

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
    Servo tiltServo;



    /*
    Declares servo position variables
    */

    public static final double LOCK_SERVOS_CLOSED_POSITION = 0.88;
    public static final double LOCK_SERVOS_OPEN_POSITION = 0.5;
    public static final double DEPOSITING_FLAP_SERVO_OPEN_POSITION = 0.0;
    public static final double DEPOSITING_FLAP_SERVO_CLOSED_POSITION = 1.0;
    public static final double TILT_LEFT = 0;
    public static final double TILT_RIGHT = 1.0;
    public static final double TILT_MIDDLE = 0.5;
    public static Boolean onRamp = false;             //should be set to true when on ramp;

    boolean servoLockWasPressed = false, flapLeftWasPressed = false, flapRightWasPressed = false;
    boolean isLockClosed = true, isLeftFlapClosed = true, isRightFlapClosed = true;
    boolean firstRun = true;

    double startTime;

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
        depositingFlapLeft = hardwareMap.servo.get("flapLeft");
        depositingFlapRight = hardwareMap.servo.get("flapRight");
        tiltServo = hardwareMap.servo.get("tilt");


        lockServos.setPosition(LOCK_SERVOS_CLOSED_POSITION);
        depositingFlapLeft.setPosition(DEPOSITING_FLAP_SERVO_CLOSED_POSITION);
        depositingFlapRight.setPosition(DEPOSITING_FLAP_SERVO_OPEN_POSITION);
        rackServo.setPosition(0.5);
        tiltServo.setPosition(TILT_MIDDLE);

        /*sets certain motors to run with encoders*/
        pulleyMotor1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    @Override
    public void loop() {

        if(firstRun) {
            startTime = System.currentTimeMillis();
            firstRun = false;
        }

        /*
        Creates variables for gamepad input
        */
        float leftDrivePwr;
        float rightDrivePwr;
        float bucketArmPwr;
        float pulleyPwr;


        if (gamepad1.y)
            onRamp = true;
        else if (gamepad1.a)
            onRamp = false;

        if (!onRamp) {
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


        if (abs(gamepad2.left_stick_y) > 0.08) {
            bucketArmPwr = -gamepad2.left_stick_y;
        } else {
            bucketArmPwr = 0;
        }

        if (abs(gamepad2.right_stick_y) > 0.08) {
            pulleyPwr = -gamepad2.right_stick_y;
        } else {
            pulleyPwr = 0;
        }

        /*
        Scales drivetrain gamepad values
        */
        if (gamepad1.left_bumper && gamepad1.right_bumper) {
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
        if (gamepad2.dpad_up)
            bucketArmMotor.setPower(bucketArmPwr);
        else
            bucketArmMotor.setPower(bucketArmPwr * 0.45);

        pulleyMotor1.setPower(pulleyPwr);

        /*
        Depositing Flap Servo assignment
        */
        if (gamepad1.x && !flapLeftWasPressed){
            if (isLeftFlapClosed) {
                depositingFlapLeft.setPosition(DEPOSITING_FLAP_SERVO_OPEN_POSITION);
            } else {
                depositingFlapLeft.setPosition(DEPOSITING_FLAP_SERVO_CLOSED_POSITION);
            }
            isLeftFlapClosed = !isLeftFlapClosed;
        }

        if (gamepad1.b && !flapRightWasPressed){
            if (isRightFlapClosed) {
                depositingFlapRight.setPosition(DEPOSITING_FLAP_SERVO_CLOSED_POSITION);
            } else {
                depositingFlapRight.setPosition(DEPOSITING_FLAP_SERVO_OPEN_POSITION);
            }
            isRightFlapClosed = !isRightFlapClosed;
        }




        /*
        Rack & Pinion Servo assignment
        */
        if (gamepad1.dpad_right)
            rackServo.setPosition(0);
        else if (gamepad1.dpad_left)
            rackServo.setPosition(1);
        else
            rackServo.setPosition(0.5);

        /*
        Tilt Servo Assignment
         */
        if (gamepad2.dpad_right)
            tiltServo.setPosition(TILT_RIGHT);
        else if (gamepad2.dpad_left)
            tiltServo.setPosition(TILT_LEFT);
        else
            tiltServo.setPosition(TILT_MIDDLE);

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
            }
            else {
                lockServos.setPosition(LOCK_SERVOS_CLOSED_POSITION);
            }
            isLockClosed = !isLockClosed;
        }


        /*
        *telemetry code will display output to user
        *updates robot part status
         */
        telemetry.clearData();
        telemetry.addData("*TIME LEFT: ", (int)((120.0 - Math.ceil((System.currentTimeMillis() - startTime) / 1000))/60) + ":" + (int)((120-Math.ceil((System.currentTimeMillis()-startTime)/1000)))%60);
        if(isLockClosed)
            telemetry.addData("Lock Servos: " , "NOT locked");
        else
            telemetry.addData("LockServos: ", "locked");

        if(!isLeftFlapClosed)
            telemetry.addData("LeftFlap: ", "closed");
        else
            telemetry.addData("LeftFlap: ", "open");

        if(!isRightFlapClosed)
            telemetry.addData("RightFlap: ", "closed");
        else
            telemetry.addData("RightFlap: ", "open");

        if(onRamp)
            telemetry.addData("Drive Mode: ", "onRamp");
        else
            telemetry.addData("Drive Mode: ", "ground");

        telemetry.addData("Bucket Power", bucketArmMotor.getPower());

        //telemetry.addData("Pulley Pos: ", pulleyMotor1.getCurrentPosition());






        /*state variables to hold condition of buttons
        *used to check if button was pressed last cycle
         */
        servoLockWasPressed = gamepad2.x;
        flapLeftWasPressed = gamepad1.x;
        flapRightWasPressed = gamepad1.b;


    }





    @Override
    public void stop() {

    }
}
