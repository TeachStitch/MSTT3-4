import main.aima.core.environment.wumpusworld.WumpusAction;

import java.util.Random;

public class ActionDict {
    private static final String[] forward = {"Move forward."};
    private static final String[] turn_right = {"Just turn right."};
    private static final String[] turn_left = {"Just turn left."};
    private static final String[] grab = {"Just grab gold quickly!"};
    private static final String[] shoot = {"You must shoot!"};
    private static final String[] climb = {"Just climb back up."};

    private static String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    public static String getClimb() {
        return getRandom(climb);
    }

    public static String getForward() {
        return getRandom(forward);
    }

    public static String getGrab() {
        return getRandom(grab);
    }

    public static String getShoot() {
        return getRandom(shoot);
    }

    public static String getTurnLeft() {
        return getRandom(turn_left);
    }

    public static String getTurnRight() {
        return getRandom(turn_right);
    }

    public static String getSentence(WumpusAction action) {
        switch (action) {
            case FORWARD:
                return getForward();
            case TURN_LEFT:
                return getTurnLeft();
            case TURN_RIGHT:
                return getTurnRight();
            case GRAB:
                return getGrab();
            case SHOOT:
                return getShoot();
            case CLIMB:
                return getClimb();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static WumpusAction getAction(String act) {
        if (act.toLowerCase().contains("forward")) {
            return WumpusAction.FORWARD;
        } else if (act.toLowerCase().contains("climb")) {
            return WumpusAction.CLIMB;
        } else if (act.toLowerCase().contains("grab")) {
            return WumpusAction.GRAB;
        } else if (act.toLowerCase().contains("shoot")) {
            return WumpusAction.SHOOT;
        } else if (act.toLowerCase().contains("turn left")) {
            return WumpusAction.TURN_LEFT;
        } else if (act.toLowerCase().contains("turn right")) {
            return WumpusAction.TURN_RIGHT;
        } else {
            throw new IllegalArgumentException("Statement: \n" + act + "\n doesn't contain any action!");
        }
    }
}