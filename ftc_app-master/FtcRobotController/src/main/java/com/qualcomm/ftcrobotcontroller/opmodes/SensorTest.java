package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;


/**
 * Created by Sahaj on 1/13/2016.
 */
public class SensorTest extends OpMode {
    ColorSensor color;
    OpticalDistanceSensor ods;
    TouchSensor touch;
    GyroSensor gyro;
    double gyroZero;
    double degreesTurned = 0;
    long lastTime;

    public SensorTest(){}

    @Override
    public void init(){
        color = hardwareMap.colorSensor.get("color");
        ods = hardwareMap.opticalDistanceSensor.get("ods");
        touch = hardwareMap.touchSensor.get("touch");
        gyro = hardwareMap.gyroSensor.get("gyro");

        gyroZero = gyro.getRotation();
        lastTime = System.currentTimeMillis();
    }

    @Override
    public void loop() throws UnsupportedOperationException{
        if (gamepad1.x)
            color.enableLed(true);
        else if (gamepad1.b)
            color.enableLed(false);

        int[] colorVal = new int[5];
        colorVal[0] = color.red();
        colorVal[2] = color.blue();
        colorVal[1] = color.green();
        colorVal[3] = color.alpha();
        colorVal[4] = color.argb();
        String colorOutput = "(";

        for (int i = 0; i<5; i++){
            colorOutput += colorVal[i] + " ";
        }

        colorOutput += ")";
        telemetry.addData("color (R,G,B,Light,Hue): ", colorOutput);

        telemetry.addData("touch: ", touch.isPressed());

        telemetry.addData("ODS: ", ods.getLightDetectedRaw());

        telemetry.addData("GyroZero", gyroZero);

        telemetry.addData("Gyro Rotation", gyro.getRotation());

        /*converting gyro rotation to degrees*/

        if(Math.abs(gyro.getRotation() - gyroZero) >=5 )
        degreesTurned += ((gyro.getRotation()-gyroZero)*(System.currentTimeMillis()-lastTime)/1000);

        telemetry.addData("gyro heading", degreesTurned);

        if(gamepad1.a)
            degreesTurned = 0;

        lastTime = System.currentTimeMillis();

    }

    @Override
    public void stop(){
        color.enableLed(false);
    }
}
