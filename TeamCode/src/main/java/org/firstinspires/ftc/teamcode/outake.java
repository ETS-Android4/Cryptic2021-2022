package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class outake implements Subsystem{
    Servo outakeServo;
    Servo outakeServo2;
    Servo outakeServo3;
    Servo outakeServo4;
    @Override
    public void init(LinearOpMode opMode) {
        outakeServo = opMode.hardwareMap.get(Servo.class, "outakeS");
        outakeServo2 = opMode.hardwareMap.get(Servo.class, "outakeS2");
        outakeServo3 = opMode.hardwareMap.get(Servo.class, "outakeS3");
        outakeServo4 = opMode.hardwareMap.get(Servo.class, "outakeS3");
    }

}
