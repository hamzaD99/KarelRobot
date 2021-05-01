import stanford.karel.SuperKarel;

/**
 * Level 1: Put beepers only in the odd outside spots (e.g. 1x2, 2x1 are considered odd), then print the number how many you've put, and then
 * collect them all (do not duplicate beepers)
 * Level 2: Put beepers on all even spots, then print the number how many you've put, and finally collect them all
 * Level 3: Divide the map (using beepers) into 2 or 4 equal chambers (rectangles surrounded by walls or beepers),
 * depending on the map; see solution in 7x7; please note that you cannot put duplicate beepers.
 * Make sure to clean the map and print how many beepers you've put, then collect them all.
 *
 * 
 */
public class Homework extends SuperKarel {
    int x = 1, y = 1, map_x = 0, map_y = 0;

    public void run() {
        x = 1;
        y = 1;
        map_x = 0;
        map_y = 0;
        scanMap();
        backHome();
        pickBeepers();
        backHome();
        level1();
        backHome();
        pickBeepers();
        backHome();
        level2();
        backHome();
        pickBeepers();
        backHome();
        level3();
        backHome();
    }


    private boolean moveNorth() {
        boolean done = false;
        if (facingEast()) {
            turnLeft();
        } else if (facingWest()) {
            turnRight();
        } else if (facingSouth()) {
            turnAround();
        }
        if (frontIsClear()) {
            customMove();
            done = true;
        } else done = false;
        return done;
    }

    private boolean moveSouth() {
        boolean done = false;
        if (facingEast()) {
            turnRight();
        } else if (facingWest()) {
            turnLeft();
        } else if (facingNorth()) {
            turnAround();
        }
        if (frontIsClear()) {
            customMove();
            done = true;
        } else done = false;
        return done;
    }

    private boolean moveEast() {
        boolean done = false;
        if (facingNorth()) {
            turnRight();
        } else if (facingSouth()) {
            turnLeft();
        } else if (facingWest()) {
            turnAround();
        }
        if (frontIsClear()) {
            customMove();
            done = true;
        } else done = false;
        return done;
    }

    private boolean moveWest() {
        boolean done = false;
        if (facingNorth()) {
            turnLeft();
        } else if (facingSouth()) {
            turnRight();
        } else if (facingEast()) {
            turnAround();
        }
        if (frontIsClear()) {
            customMove();
            done = true;
        } else done = false;
        return done;
    }

    private void customMove() {
        if (facingNorth()) {
            move();
            y++;
        } else if (facingSouth()) {
            move();
            y--;
        } else if (facingWest()) {
            move();
            x--;
        } else if (facingEast()) {
            move();
            x++;
        }
    }

    private boolean goTo(int x_dest, int y_dest) {
        boolean done = false;
        if (x_dest <= map_x && y_dest <= map_y) {
            while (x < x_dest) {
                moveEast();
            }
            while (x > x_dest) {
                moveWest();
            }
            while (y < y_dest) {
                moveNorth();
            }
            while (y > y_dest) {
                moveSouth();
            }
            done = true;
        }
        return done;
    }

    private void scanMap() {
        while (frontIsClear()) {
            moveEast();
        }
        turnLeft();
        while (frontIsClear()) {
            moveNorth();
        }
        map_x = x;
        map_y = y;
    }

    private void pickBeepers() {
        for (int i = 1; i <= map_y; i++) {
            for (int j = 1; j <= map_x; j++) {
                if (beepersPresent())
                    pickBeeper();
                if (i % 2 != 0)
                    moveEast();
                else
                    moveWest();
            }
            moveNorth();
        }
    }

    private void backHome() {
        goTo(1, 1);
    }

    private int level1() {
        int c = 0;
        int inner = 0;
        if(map_y == 1 || map_x == 1) inner = 0;
        else inner = (map_x-2)*(map_y-2);
        int frame_len = (map_x*map_y)-inner;
        int frameCount = frame_len;
        while (frameCount>0){
            if(frontIsClear()){
                customMove();
                frameCount--;
                if ((x + y) % 2 != 0) {
                    putBeeper();
                    c++;
                }
            }
            else {
                turnLeft();
            }
        }
        System.out.print("# of beepers in level 1: ");
        System.out.println(c);
        return c;
    }

    private int level2() {
        int c = 0;
        for (int i = 1; i <= map_y; i++) {
            for (int j = 1; j <= map_x; j++) {
                if ((x + y) % 2 == 0) {
                    putBeeper();
                    c++;
                }
                if (i % 2 != 0)
                    moveEast();
                else
                    moveWest();
            }
            moveNorth();
        }
        System.out.print("# of beepers in level 2: ");
        System.out.println(c);
        return c;
    }

    private int level3() {
        int c = 0;
        int mid_x = 0, mid_y = 0;
        if(map_x == 1){
            if (map_y % 2 != 0){
                goTo(1,(map_y/2)+1);
                putBeeper();
                c++;
            }
            else {
                goTo(1,map_y/2);
                putBeeper();
                goTo(1,(map_y/2)+1);
                putBeeper();
                c+=2;
            }
        }
        else if(map_y == 1){
            if (map_x % 2 != 0){
                goTo((map_x/2)+1,1);
                putBeeper();
                c++;
            }
            else {
                goTo(map_x/2,1);
                putBeeper();
                goTo((map_x/2)+1,1);
                putBeeper();
                c+=2;
            }
        }
        else {
            if (map_x % 2 != 0) {
                mid_x = (map_x / 2) + 1;
            } else if (map_x % 2 == 0) {
                mid_x = (map_x / 2);
            }
            c += putBeepersInLineX(mid_x);
            if (map_x % 2 == 0) c += putBeepersInLineX(mid_x + 1);

            if (map_y % 2 != 0) {
                mid_y = (map_y / 2) + 1;
            } else if (map_y % 2 == 0) {
                mid_y = (map_y / 2);
            }
            c += putBeepersInLineY(mid_y);
            if (map_y % 2 == 0) c += putBeepersInLineY(mid_y + 1);
        }


        System.out.print("# of beepers in level 3: ");
        System.out.println(c);
        return c;
    }

    private int putBeepersInLineX(int mid_x) {
        int c = 0;
        goTo(mid_x, 1);
        for (int i = 0; i < y; i++) {
            if (noBeepersPresent()) {
                putBeeper();
                c++;
            }
            moveNorth();
        }
        return c;
    }

    private int putBeepersInLineY(int mid_y) {
        int c = 0;
        goTo(1, mid_y);
        for (int i = 0; i < x; i++) {
            if (noBeepersPresent()) {
                putBeeper();
                c++;
            }
            moveEast();
        }
        return c;
    }

}





