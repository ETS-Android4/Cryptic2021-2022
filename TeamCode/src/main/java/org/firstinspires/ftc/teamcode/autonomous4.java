package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.localization.Localizer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.drive.SampleTankDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous
public class autonomous4 extends LinearOpMode {
    public void runOpMode() {
        Robot robot = new Robot();
        robot.initialize(this);
        SampleTankDrive drive = new SampleTankDrive(hardwareMap);

        // We want to start the bot at x: -12, y: 66, heading: 90 degrees
        Pose2d startPose = new Pose2d(-12, 66, Math.toRadians(270));

        drive.setPoseEstimate(startPose);

        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(startPose)
                // Robot drives straight forward to the shipping hub
                .lineTo(new Vector2d(-12, 36))
                .waitSeconds(3)
                // Robot drops the freight on the top row
                .addDisplacementMarker(() -> {
                    robot.extensionServoLeft.setPosition(0.9);
                    robot.extensionServoRight.setPosition(0.9);
                    robot.outakeServo3.setPosition(0.722);
                    robot.outakeServo4.setPosition(0.722);
                })
                .waitSeconds(3)
                // Robot resets outake to original position
                .addDisplacementMarker(() -> {
                    robot.extensionServoLeft.setPosition(0);
                    robot.extensionServoRight.setPosition(0);
                    robot.outakeServo3.setPosition(0);
                    robot.outakeServo4.setPosition(0);
                })
                .waitSeconds(3)
                // Robot drives back to the warehouse
                .lineTo(new Vector2d(-12, 52))
                .splineTo(new Vector2d(10, 66), Math.toRadians(0))
                .lineTo(new Vector2d(55, 66))
                .build();
        waitForStart();

        if(isStopRequested()) return;

        drive.followTrajectorySequence(traj1);
    }
}
