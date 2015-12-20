package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import static java.lang.Math.abs;

/**
 * Created by rkhaj on 12/5/2015.
 * This is the teleop for the new robot (made beginning of December.)
 */
public class TeleOp2New extends OpMode {

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
    Servo climberServo;
    Servo lockServos;                       //Y-splitter controls 2 servos simultaneously
    Servo rackServo;
    Servo depositingFlapServos;




    /*
    Declares servo position variables
    */
    public static final double CLIMBER_SERVO_CLOSED_POSITION = 0;
    public static final double CLIMBER_SERVO_OPEN_POSITION = 0;
    public static final double LOCK_SERVOS_CLOSED_POSITION = 0.99;
    public static final double LOCK_SERVOS_OPEN_POSITION = 0.40;
    public static final double DEPOSITING_FLAP_SERVO_OPEN_POSITION = 0;
    public static final double DEPOSITING_FLAP_SERVO_CLOSED_POSITION = 0;
    public static Boolean onRamp = false;             //should be set to true when on ramp;

    public TeleOp2New() {}

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
        //climberServo = hardwareMap.servo.get("climber");
        lockServos = hardwareMap.servo.get("lockServos");
        rackServo = hardwareMap.servo.get("rack");
        //depositingFlapServos = hardwareMap.servo.get("depositFlaps");

        lockServos.setPosition(LOCK_SERVOS_CLOSED_POSITION);
        //depositingFlapServos.setPosition(DEPOSITING_FLAP_SERVO_CLOSED_POSITION);
        rackServo.setPosition(0.5);
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

        if(onRamp == false) {
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
            bucketArmPwr = -gamepad2.right_stick_y;
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
            leftDrivePwr *= 0.5;
            rightDrivePwr *= 0.5;
        }

        /*
        Updates motor power
        */
        lfMotor.setPower(leftDrivePwr);
        lbMotor.setPower(leftDrivePwr);
        rfMotor.setPower(rightDrivePwr);
        rbMotor.setPower(rightDrivePwr);
        bucketArmMotor.setPower(bucketArmPwr);
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
        if (gamepad2.b) {
            lockServos.setPosition(LOCK_SERVOS_CLOSED_POSITION);
        } else if (gamepad2.x) {
            lockServos.setPosition(LOCK_SERVOS_OPEN_POSITION);
        }

        /*
        *telemetry code will display output to user
        *updates robot part status
         */
        telemetry.clearData();
        if(lockServos.getPosition() > 0.1)
            telemetry.addData("Lock Servos: " , "locked");
        else
            telemetry.addData("LockServos: ", "open");

        if(onRamp == true)
            telemetry.addData("Drive Mode: ", "onRamp");
        else
            telemetry.addData("Drive Mode: ", "ground");
    }





    @Override
    public void stop() {

    }
}
