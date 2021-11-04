package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Robot {
   public DcMotor leftFront;
   public DcMotor rightFront;
   public DcMotor leftBack;
   public DcMotor rightBack;
   public CRServo intakeServo;

   public void initialize(OpMode opMode) {
       leftFront = opMode.hardwareMap.get(DcMotor.class, "lf");
       rightFront = opMode.hardwareMap.get(DcMotor.class, "rf");
       leftBack = opMode.hardwareMap.get(DcMotor.class, "lb");
       rightBack = opMode.hardwareMap.get(DcMotor.class, "rb");
       intakeServo = opMode.hardwareMap.get(CRServo.class, "is");

       leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
       leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

       leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
   }
}


