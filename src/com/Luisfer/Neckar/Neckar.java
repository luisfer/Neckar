// Neckar
// 2012 by Luisfer Romero Calero

package com.Luisfer.Neckar;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;

public class Neckar extends Activity {
    
	// Different touch states (more than necessary for this project)
	int touchState;
	final int IDLE = 0;
	final int TOUCH = 1;
	final int PINCH = 2;
	final int SCROLL = 3;
	final int DOUBLETAP = 4;
	final int ENDOFPINCH =5;
	
	// Catching the coordinates of the fingers
	public static boolean transitionFinished = true;
	static float firstFingerpreviousX, firstFingerpreviousY;
	static float secondFingerpreviousX, secondFingerpreviousY;
	static float firstFingernewX, firstFingernewY;
	static float secondFingernewX, secondFingernewY;

	
	// Distance between the two fingers
	float distance1, distance2;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RelativeLayout container = new RelativeLayout(this);
        container.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		
		// Gesture starts
	     
		case MotionEvent.ACTION_DOWN:
			firstFingerpreviousX = event.getX();
			firstFingerpreviousY = event.getY();
			touchState = TOUCH;
			break;
			
		// Second finger is now on the show
			
		case MotionEvent.ACTION_POINTER_DOWN:
			touchState = PINCH;
			secondFingerpreviousX = event.getX(1);
			secondFingerpreviousY = event.getY(1);
			distance1 = makeDistance(firstFingerpreviousX,
				firstFingerpreviousY, secondFingerpreviousX,
				secondFingerpreviousY);
		break;
		
		// Fingers are moving
		
		case MotionEvent.ACTION_MOVE:
			System.out.println(touchState);
			
			if (touchState == PINCH) {
				firstFingernewX = event.getX(0);
				firstFingernewY = event.getX(0);
				secondFingernewX = event.getX(1);
				secondFingernewY = event.getY(1);
				distance2 = makeDistance(firstFingernewX, firstFingernewY,
						secondFingernewX, secondFingernewY);
				break;
		}
		break;
		
		// Second finger gets tired
		
		case MotionEvent.ACTION_POINTER_UP:
			touchState = ENDOFPINCH;
			// We calculate the distances between the fingers
			// to know if there is zoom in or out
			if (distance2 > distance1) {
				System.out.println("Zoom in detected");
			} else {
				System.out.println("Zoom out detected");
			}
			break;
		}
		return true;
	}
		
	// Pythagorean Theorem distance maker method
	
		public static float makeDistance(float x1, float y1, float x2, float y2) {
			float delta1 = (x2 - x1) * (x2 - x1);
			float delta2 = (y2 - y1) * (y2 - y1);
			float distance = (float) Math.sqrt(delta1 + delta2);
			return distance;
		}
}