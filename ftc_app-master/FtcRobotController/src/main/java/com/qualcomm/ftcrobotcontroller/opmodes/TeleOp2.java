package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static java.lang.Math.abs;

/**
 * Created by rkhaj on 12/5/2015.
 * This is the teleop for the new robot (made beginning of Demeber.
 */
public class TeleOp2 extends OpMode {

    /*
    Declares motor variables and creates link to hardware destinations
    */
    DcMotor lfMotor = hardwareMap.dcMotor.get("lfMotor");
    DcMotor lbMotor = hardwareMap.dcMotor.get("lbMotor");
    DcMotor rfMotor = hardwareMap.dcMotor.get("rfMotor");
    DcMotor rbMotor = hardwareMap.dcMotor.get("rbMotor");
    DcMotor bucketArmMotor = hardwareMap.dcMotor.get("bucketArmMotor");
    DcMotor pulleyMotor1 = hardwareMap.dcMotor.get("pulleyMotor1");

    /*
    Declares servo variables and creates link to hardware destinations
    */
    Servo climberServo = hardwareMap.servo.get("climberServo");
    Servo lockServoLeft = hardwareMap.servo.get("lockServoLeft");
    Servo lockServoRight = hardwareMap.servo.get("lockServoRight");
    Servo rackServo = hardwareMap.servo.get("rackServo");
    Servo depositingFlapServoLeft = hardwareMap.servo.get("depositingFlapServoLeft");
    Servo depositingFlapServoRight = hardwareMap.servo.get("depositingFlapServoRight");

    /*
    Declares servo position variables
    */
    public static final int CLIMBER_SERVO_CLOSED_POSITION = 0;
    public static final int CLIMBER_SERVO_OPEN_POSITION = 0;
    public static final int LOCK_SERVO_LEFT_CLOSED_POSITION = 0;
    public static final int LOCK_SERVO_LEFT_OPEN_POSITION = 0;
    public static final int LOCK_SERVO_RIGHT_CLOSED_POSITION = 0;
    public static final int LOCK_SERVO_RIGHT_OPEN_POSITION = 0;
    public static final int DEPOSITING_FLAP_SERVO_LEFT_OPEN_POSITION = 0;
    public static final int DEPOSITING_FLAP_SERVO_LEFT_CLOSED_POSITION = 0;
    public static final int DEPOSITING_FLAP_SERVO_RIGHT_OPEN_POSITION = 0;
    public static final int DEPOSITING_FLAP_SERVO_RIGHT_CLOSED_POSITION = 0;

    public TeleOp2() {}

    @Override
    public void init() {

    }

    @Override
    public void loop() {

        /*
        Creates variables for gamepad input
        */
        float leftLeftVer;
        float leftRightVer;
        float rightLeftVer;
        float rightRightVer;

        /*
        Gets stick power
        */
        if (abs(gamepad1.left_stick_y)>0.08) {
            leftLeftVer = -gamepad1.left_stick_y;
        } else {
            leftLeftVer = 0;
        }

        if (abs(gamepad1.right_stick_y)>0.08) {
            leftRightVer = -gamepad1.right_stick_y;
        } else {
            leftRightVer = 0;
        }

        if (abs(gamepad2.left_stick_y)>0.08) {
            rightLeftVer = -gamepad1.right_stick_y;
        } else {
            rightLeftVer = 0;
        }

        if (abs(gamepad2.right_stick_y)>0.08) {
            rightRightVer = -gamepad1.right_stick_y;
        } else {
            rightRightVer = 0;
        }

        /*
        Scales drivetrain gamepad values
        */
        if (gamepad1.left_bumper && gamepad1.right_bumper){
            leftLeftVer *= 0.2;
            leftRightVer *= 0.2;
        } else if (gamepad1.left_bumper) {
            leftLeftVer *= 0.5;
            leftRightVer *= 0.5;
        }

        /*
        Updates motor power
        */
        lfMotor.setPower(leftLeftVer);
        lbMotor.setPower(leftLeftVer);
        rfMotor.setPower(leftRightVer);
        rbMotor.setPower(leftRightVer);
        bucketArmMotor.setPower(rightLeftVer);
        pulleyMotor1.setPower(rightRightVer);

        /*
        Depositing Flap Servo assignment
        */
        if (gamepad1.b)
            depositingFlapServoRight.setPosition(DEPOSITING_FLAP_SERVO_RIGHT_OPEN_POSITION);
        else
            depositingFlapServoRight.setPosition(DEPOSITING_FLAP_SERVO_RIGHT_CLOSED_POSITION);

        if (gamepad1.x)
            depositingFlapServoRight.setPosition(DEPOSITING_FLAP_SERVO_LEFT_OPEN_POSITION);
        else
            depositingFlapServoRight.setPosition(DEPOSITING_FLAP_SERVO_LEFT_CLOSED_POSITION);

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
        if (gamepad1.left_trigger > 0.3)
            climberServo.setPosition(CLIMBER_SERVO_CLOSED_POSITION);
        else if (gamepad1.right_trigger > 0.3)
            climberServo.setPosition(CLIMBER_SERVO_OPEN_POSITION);

        /*
        Lock Servo assignment
        */
        if (gamepad2.b) {
            lockServoLeft.setPosition(LOCK_SERVO_LEFT_CLOSED_POSITION);
            lockServoRight.setPosition(LOCK_SERVO_RIGHT_CLOSED_POSITION);
        } else if (gamepad2.x) {
            lockServoLeft.setPosition(LOCK_SERVO_LEFT_OPEN_POSITION);
            lockServoRight.setPosition(LOCK_SERVO_RIGHT_OPEN_POSITION);
        }
    }

    @Override
    public void stop() {

    }
}
