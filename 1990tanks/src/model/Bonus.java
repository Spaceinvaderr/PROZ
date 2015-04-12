package model;

import java.awt.Image;
import java.awt.Rectangle;

public class Bonus extends Model {

    private final Bullet bullet;
         /*   private final Player tank;*/

    public Bonus(Rectangle box, Image image, Model tank, Bullet bullet) {
	          /*      super(box, image);
	                this.tank = tank;*/
        this.bullet = bullet;
    }

    public Bullet getBullet() {
        return bullet;
    }
/*
	        public Player getTank() {
	                return tank;
	        }*/

}
