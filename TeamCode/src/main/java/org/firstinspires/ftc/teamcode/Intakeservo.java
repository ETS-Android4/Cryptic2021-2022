package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Intakeservo implements Subsystem{
    DcMotor intakeMotor;
    CRServo intakeServo;
    @Override
    public void init(LinearOpMode opMode) {
        intakeMotor = opMode.hardwareMap.get(DcMotor.class, "intakeM");
        intakeServo = opMode.hardwareMap.get(CRServo.class, "intakeS");
    }

}
