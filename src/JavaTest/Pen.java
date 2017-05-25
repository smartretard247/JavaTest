/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaTest;

enum Color
{
    RED, BLUE, BLACK
};

public class Pen {
    Color color = Color.BLACK;
    
    void draw() {
        //draw in BLACK
    }
};

class RedPen extends Pen {
    RedPen() {
        color = Color.RED;
    }
    
    @Override
    void draw() {
        //draw in RED
    }
};

class BluePen extends RedPen {
    BluePen() {
        color = Color.BLUE;
    }
    
    @Override
    void draw() {
        //draw in BLUE
    }
};

class MultiPen extends BluePen {
    @Override
    void draw() {
        //draw in BLUE
    }
    
    void switchColor() {
        if(color == Color.BLUE) {
            color = Color.RED;
        } else {
            color = Color.BLUE;
        }
    }
};