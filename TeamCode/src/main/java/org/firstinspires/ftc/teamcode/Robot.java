package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {
   public DcMotor leftFront;
   public DcMotor rightFront;
   public DcMotor leftBack;
   public DcMotor rightBack;
   public DcMotor duckWheel;
   public Servo outakeServo;
   public Servo outakeServo2;
   public Servo outakeServo3;
   public Servo outakeServo4;

    DcMotor intakeMotor;
    Servo intakeServo;
   public void initialize(LinearOpMode opMode) {

       intakeMotor = opMode.hardwareMap.get(DcMotor.class, "intakeM");
       intakeServo = opMode.hardwareMap.get(Servo.class, "intakeS");

       outakeServo = opMode.hardwareMap.get(Servo.class, "outakeS");
       outakeServo2 = opMode.hardwareMap.get(Servo.class, "outakeS2");
       outakeServo3 = opMode.hardwareMap.get(Servo.class, "outakeS3");
       outakeServo4 = opMode.hardwareMap.get(Servo.class, "outakeS4");

       leftFront = opMode.hardwareMap.get(DcMotor.class, "lf");
       rightFront = opMode.hardwareMap.get(DcMotor.class, "rf");
       leftBack = opMode.hardwareMap.get(DcMotor.class, "lb");
       rightBack = opMode.hardwareMap.get(DcMotor.class, "rb");
       duckWheel = opMode.hardwareMap.get(DcMotor.class, "dw");

       //to make robot wheels spin the same way bc the motor inverts the other side's wheels
       //reversing the one side of the robot's wheels direction
       //bc the motor inverts the other side's wheels
       //therefore being able to travel forward
       rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
       rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

       //to make robot stop when youre not moving the sticks
       leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       duckWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


   }
}


