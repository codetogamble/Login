package com.bulsy.wbtempest;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import com.bulsy.wbtempest.BuildConfig;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.InputStream;

/**
 * Created by ugliest on 3/10/15.
 */
public class EntryScreen extends Screen {
    MainActivity_wbta act;
    Paint p = new Paint();
    Rect scaledDst = new Rect(); // generic rect for scaling
    Rect playBtnBounds = null;
    Rect exitBtnBounds = null;

    public EntryScreen(MainActivity_wbta act) {
        int x = ZMagic.Z_MAX_CACHE; // force load of ZMagic class while showing entry screen
        this.act = act;
    }

    @Override
    public void update(View v) {
      // nothing to update
    }

    @Override
    public void draw(Canvas c, View v) {
        int width = v.getWidth();
        int height = v.getHeight();
        String playmsg = act.getResources().getString(R.string.play);
        String exitmsg = act.getResources().getString(R.string.exitapp);

        // draw the screen
        c.drawRGB(0, 0, 0);

        p.setTypeface(act.getGameFont());
        p.setColor(Color.GREEN);  // dark greenish
        p.setTextSize(act.TS_BIG * 1.5f);
        String txt = "WBT";
        c.drawText(txt, (v.getWidth()-p.measureText(txt))/2, height/3, p);

        // screen chooser


        p.setTextSize(act.TS_BIG);
        if (playBtnBounds == null) {
            // initialize button locations
            p.getTextBounds(playmsg, 0, playmsg.length() - 1, scaledDst);
            // weirdly, and annoyingly, the bounds we create magically move and shrink.
            // so...uh....compensate by making the expected touch area bigger.
            playBtnBounds = new Rect(width/4 - scaledDst.width(),
                    height *3/4 - scaledDst.height(),
                    width/4 + scaledDst.width(),
                    height *3/4 + scaledDst.height());
            p.getTextBounds(exitmsg, 0, exitmsg.length() - 1, scaledDst);
            exitBtnBounds = new Rect(width*3/4 - scaledDst.width(),
                    height *3/4 - scaledDst.height(),
                    width*3/4 + scaledDst.width(),
                    height *3/4 + scaledDst.height());
        }
        c.drawText(playmsg, playBtnBounds.left, playBtnBounds.bottom, p);
        c.drawText(exitmsg, exitBtnBounds.left, exitBtnBounds.bottom, p);

        // version line
        p.setTextSize(act.TS_NORMAL);
        String msg = "v"+ BuildConfig.VERSION_NAME;
//        com.bulsy.wbtempest.BuildConfig.VERSION_NAME
        int xTextEnd = (int)(width*.99f);
        c.drawText(msg, xTextEnd-p.measureText(msg), height - 80, p);
        int w1 = scaledDst.width();
        msg = "BULSY GAMES 2015";
        c.drawText(msg, xTextEnd-p.measureText(msg), height - 40, p);
    }

    @Override
    public boolean onTouch(MotionEvent e) {
        if (playBtnBounds.contains((int)e.getX(), (int)e.getY()))
            act.startGame();
        if (exitBtnBounds.contains((int)e.getX(), (int)e.getY()))
            act.exit();

        // we don't care about followup events in this screen
        return false;
    }

}
