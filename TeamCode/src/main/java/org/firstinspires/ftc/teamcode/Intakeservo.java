package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Intakeservo implements Subsystem{
    DcMotor intakeMotor;
    Servo intakeServo;
    @Override
    public void init(LinearOpMode opMode) {
        intakeMotor = opMode.hardwareMap.get(DcMotor.class, "intakeM");
        intakeServo = opMode.hardwareMap.get(Servo.class, "intakeS");
    }

}
