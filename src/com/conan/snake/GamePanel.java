package com.conan.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
    int length;
    int[] snakeX = new int[600];
    int[] snakeY = new int[500];
    String fx;
    boolean isStart = false;
    Timer timer = new Timer(100, this);
    int foodX;
    int foodY;
    Random random = new Random();
    //死亡判断
    boolean isFail=false;
    int score ;

    public GamePanel() {
        init();
        //获取监听事件
        this.setFocusable(true);
        this.addKeyListener(this);
        timer.start();
    }

    public void init() {
        length = 3;
        snakeX[0] = 100;
        snakeY[0] = 100;  //头部坐标
        snakeX[1] = 75;
        snakeY[1] = 100;  //第一个身体坐标
        snakeX[2] = 50;
        snakeY[2] = 100;  //第二个身体坐标
        fx = "R";
        foodX = 25 + 25 * random.nextInt(34);
        foodY=75+25*random.nextInt(24);
        score=0;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        Data.header.paintIcon(this, g, 25, 11);
        g.fillRect(25, 75, 850, 600);

        if ("R".equals(fx)) {
            Data.right.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if ("L".equals(fx)) {
            Data.left.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if ("U".equals(fx)) {
            Data.up.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if ("D".equals(fx)) {
            Data.down.paintIcon(this, g, snakeX[0], snakeY[0]);
        }

        for (int i = 1; i < length; i++) {
            Data.body.paintIcon(this, g, snakeX[i], snakeY[i]);
        }

        //画食物
        Data.food.paintIcon(this,g,foodX,foodY);
        //画积分
        g.setColor(Color.WHITE);
        g.setFont(new Font("微软雅黑", Font.BOLD, 18));
        g.drawString("长度:"+length,750,35);
        g.drawString("分数:"+score,750,50);

        if (isStart == false) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("按下空格开始游戏", 300, 300);
        }
        if (isFail == true) {
            g.setColor(Color.RED);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("游戏失败，按下空格重新开始", 200, 300);
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //  键盘按下
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {  //按下的是空格键
            if (isFail == true) {
                isFail = false;
                init();
            } else {
                isStart = !isStart;
            }
            repaint();
        }
        if (keyCode == KeyEvent.VK_LEFT&& !"R".equals(fx)) {
            fx = "L";
        } else if (keyCode == KeyEvent.VK_RIGHT && !"L".equals(fx)) {
            fx = "R";
        } else if (keyCode == KeyEvent.VK_UP && ! "D".equals(fx)) {
            fx = "U";
        } else if (keyCode == KeyEvent.VK_DOWN && !"U".equals(fx)) {
            fx = "D";
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    //执行定时操作
    @Override
    public void actionPerformed(ActionEvent e) {
        //游戏开始，并且没有结束
        if (isStart && !isFail) {
            for (int i = length - 1; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }

            if (fx.equals("R")) {
                snakeX[0] = snakeX[0] + 25;
                if (snakeX[0] > 850) {
                    snakeX[0] = 25;
                }
            } else if (fx.equals("L")) {
                snakeX[0] = snakeX[0] - 25;
                if (snakeX[0] < 25) {
                    snakeX[0] = 850;
                }
            }
            else if (fx.equals("U")) {
                snakeY[0] = snakeY[0] - 25;
                if (snakeY[0] < 75) {
                    snakeY[0] = 650;
                }
            }
            else if (fx.equals("D")) {
                snakeY[0] = snakeY[0] + 25;
                if (snakeY[0] > 650) {
                    snakeY[0] = 75;
                }
            }
            if (snakeX[0] == foodX && snakeY[0] == foodY) {
                //长度加1
                length++;
                snakeX[length-1]=foodX-1;
                snakeY[length-1]=foodY-1;
                score=score+10;
                //重新生成食物
                foodX = 25 + 25 * random.nextInt(34);
                foodY=75+25*random.nextInt(24);
            }

            for (int i = 1; i < length; i++) {
                if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                    isFail=true;
                }
            }
            repaint();
        }
 //       timer.start();
    }
}
