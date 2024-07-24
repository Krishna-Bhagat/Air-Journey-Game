
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Background extends JPanel implements ActionListener, KeyListener {

    int width = 640;
    int height = 500;

    //Image variable
    Image grass1;
    Image aeroplane;
    Image building1;
    Image building2;
    Image building3;
    Image building4;
    Image building5;
    Image building6;
    Image building7;
    Image building8;
    Image building9;
    Image building0;
    Image cloud1;
    Image cloud2;

    //velocity variable
    int gravity = 1;
    int velocity_x = -4;
    int velocity_y = 0;

    //grass location
    int[] grassX = {0, width};
    int grassY = height / 6;

    //aeroplane location
    int aeroX = width / 9;
    int aeroY = height / 3;
    int aeroheight = 48;
    int aerowidth = 100;

    //speed Up level
    boolean speeduplevel1 = true;
    boolean speeduplevel2 = true;
    boolean speeduplevel3 = true;

    //Class for cloud 
    class Cloud {

        int x = width;
        int y = -24;

        boolean passed = false;
        int cwidth = 220;
        int cheight = 88;
        Image img;

        Cloud(Image img) {
            this.img = img;
        }
    }
    Cloud first_cloud;

    //Class for Building variable
    class Building {

        int x = width;
        int y = 200;

        boolean passed = false;
        int bwidth = 160;
        int bheight = 235;
        Image img;

        Building(Image img) {
            this.img = img;
        }
    }
    Building first_building;

    //Logic part && related variables
    Timer gameLoopTimer;
    Timer buildingTimer;
    Timer cloudTimer;

    int cloudTiming = 3200;
    int buildingTiming = 1850;

    int score = 0;
    boolean gameOver = false;
    Random rand = new Random();

    ArrayList<Building> buildings;
    ArrayList<Cloud> clouds;

    Image[] ImageArray = new Image[10];
    Image[] CloudArray = new Image[2];

    Background() {
        Color customBackground = new Color(232, 248, 245);

        setPreferredSize(new Dimension(width, height));
        setBackground(customBackground);
        //load image
        grass1 = new ImageIcon(getClass().getResource("./Grass_Ground_1.png")).getImage();
        aeroplane = new ImageIcon(getClass().getResource("./Aeroplane.png")).getImage();
        building1 = new ImageIcon(getClass().getResource("./Building_1.png")).getImage();
        building2 = new ImageIcon(getClass().getResource("./Building_2.png")).getImage();
        building3 = new ImageIcon(getClass().getResource("./Building_3.png")).getImage();
        building4 = new ImageIcon(getClass().getResource("./Building_4.png")).getImage();
        building5 = new ImageIcon(getClass().getResource("./Building_5.png")).getImage();
        building6 = new ImageIcon(getClass().getResource("./Building_6.png")).getImage();
        building7 = new ImageIcon(getClass().getResource("./Building_7.png")).getImage();
        building8 = new ImageIcon(getClass().getResource("./Building_8.png")).getImage();
        building9 = new ImageIcon(getClass().getResource("./Building_9.png")).getImage();
        building0 = new ImageIcon(getClass().getResource("./Building_0.png")).getImage();
        cloud1 = new ImageIcon(getClass().getResource("./Cloud-1.png")).getImage();
        cloud2 = new ImageIcon(getClass().getResource("./Cloud-2.png")).getImage();

        ImageArray[0] = building0;
        ImageArray[1] = building1;
        ImageArray[2] = building2;
        ImageArray[3] = building3;
        ImageArray[4] = building4;
        ImageArray[5] = building5;
        ImageArray[6] = building6;
        ImageArray[7] = building7;
        ImageArray[8] = building8;
        ImageArray[9] = building9;

        CloudArray[0] = cloud1;
        CloudArray[1] = cloud2;

        gameLoopTimer = new Timer(1000 / 60, this);
        gameLoopTimer.start();

        buildingTimer = new Timer(buildingTiming, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                placebuilding();
            }

        });
        buildingTimer.start();

        buildings = new ArrayList<>();

        first_building = new Building(building4);
        first_building.x = 530;
        buildings.add(first_building);

        cloudTimer = new Timer(cloudTiming, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                placeCloud();
            }

        });
        cloudTimer.start();

        clouds = new ArrayList<>();

        first_cloud = new Cloud(cloud1);
        first_cloud.x = 340;
        clouds.add(first_cloud);

        setFocusable(true);
        addKeyListener(this);
    }

    protected void placeCloud() {
        int random_cloud = rand.nextInt(2);
        Image cloud_name = CloudArray[random_cloud];
        Cloud cloud = new Cloud(cloud_name);
        int random_value = rand.nextInt(12);
        cloud.cheight -= random_value;
        cloud.cwidth -= random_value * 2;
        clouds.add(cloud);
    }

    protected void placebuilding() {
        int random_building = rand.nextInt(10);
        Image building_name = ImageArray[random_building];
        Building building = new Building(building_name);
        int value = random_building * 11;
        building.bheight -= value;
        building.y += value;
        buildings.add(building);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow(); // Request focus again after the component is added to the container
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        //setting background
        g.drawImage(grass1, grassX[0], grassY, width, height, null);
        g.drawImage(grass1, grassX[1], grassY, width, height, null);
        g.drawImage(aeroplane, aeroX, aeroY, aerowidth, aeroheight, null);

        //First Building drawing 
        g.drawImage(first_building.img, first_building.x, first_building.y, first_building.bwidth, first_building.bheight, null);

        //First cloud drawing
        g.drawImage(first_cloud.img, first_cloud.x, first_cloud.y, first_cloud.cwidth, first_cloud.cheight, null);

        //All Other building drawing
        for (Building building : buildings) {
            g.drawImage(building.img, building.x, building.y, building.bwidth, building.bheight, null);
        }

        //All Other cloud drawing
        for (Cloud cloud : clouds) {
            g.drawImage(cloud.img, cloud.x, cloud.y, cloud.cwidth, cloud.cheight, null);
        }

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Game Over: " + String.valueOf(score), 200, 250);
        } else {
            g.setColor(Color.green);
            g.setFont(new Font("Arial", Font.PLAIN, 28));
            g.drawString("Score: " + String.valueOf(score), 10, 23);
        }

    }

    private void move() {
        //changing aeroplane location 
        velocity_y += gravity;
        aeroY += velocity_y;
        aeroY = Math.max(aeroY, 0);

        //grass movement
        if (grassX[0] <= -width) {
            grassX[0] = grassX[1] + width;
        }
        if (grassX[1] <= -width) {
            grassX[1] = grassX[0] + width;
        }
        grassX[0] += velocity_x;
        grassX[1] += velocity_x;

        //Building Timing difference
        int randomvalue = (rand.nextInt(30) + 1) * 100;
        buildingTiming += randomvalue;

        // Cloud Timing Difference
        int randomValue2 = rand.nextInt(17) * 10;
        cloudTiming -= randomValue2;

        //changing building location
        for (Building building : buildings) {
            if (collision(building)) {
                System.out.println("Building; " + building.x + " " + (building.x + building.bwidth) + " " + building.y + " Aero: " + aeroX + " " + (aeroX + aerowidth) + " " + (aeroY + aeroheight));
                gameOver = true;
            }
            building.x += velocity_x;
            if (!building.passed && aeroX > building.x + building.bwidth) {
                building.passed = true;
                ++score;
            }

        }

        //changing cloud location
        for (Cloud cloud : clouds) {
            if (collision(cloud)) {
                System.out.println("cloud; " + cloud.x + " " + (cloud.x + cloud.cwidth) + " " + (cloud.y + cloud.cheight) + " Aero: " + aeroX + " " + (aeroX + aerowidth) + " " + aeroY);
                gameOver = true;
            }
            cloud.x += velocity_x;
            if (!cloud.passed && aeroX > cloud.x + cloud.cheight) {
                cloud.passed = true;
                ++score;
            }
        }

        if (speeduplevel1 && score > 20) {
            speeduplevel1 = false;
            velocity_x -= 2;
        }
        if (speeduplevel2 && score > 30) {
            speeduplevel2 = false;
            velocity_x -= 2;
        }
        if (speeduplevel3 && score > 36) {
            speeduplevel3 = false;
            velocity_x -= 2;
        }

        //Game Over incase of touching the ground
        if (aeroY > 360) {
            gameOver = true;
        }
    }

    private boolean collision(Cloud cloud) {
        if (aeroX + aerowidth > cloud.x + 15
                && aeroX < cloud.x + cloud.cwidth
                && aeroY + 15 < cloud.cheight + cloud.y) {
            return true;
        }
        return false;
    }

    private boolean collision(Building building) {
        if (aeroX + aerowidth - 9 > building.x
                && aeroX < building.x + building.bwidth
                && aeroY + aeroheight - 5 > building.y) {
            return true;
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        move();
        if (gameOver) {
            gameLoopTimer.stop();
            buildingTimer.stop();
            cloudTimer.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocity_y = -7;
        }
        if (gameOver && e.getKeyCode() == KeyEvent.VK_ENTER) {
            //resetting all the values
            score = 0;
            velocity_x = -4;
            aeroY = height / 3;
            speeduplevel1 = true;
            speeduplevel2 = true;
            speeduplevel3 = true;
            buildings.clear();
            clouds.clear();
            first_building = new Building(building4);
            first_building.x = 530;
            buildings.add(first_building);
            first_cloud = new Cloud(cloud1);
            first_cloud.x = 340;
            clouds.add(first_cloud);
            gameOver = false;
            //Starting the timer again
            gameLoopTimer.start();
            buildingTimer.start();
            cloudTimer.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
