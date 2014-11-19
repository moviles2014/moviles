package parcados.activities;

import java.lang.reflect.Method;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyApplication extends Application{

    private static Context context;

    public void onCreate(){
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
	
    
    
	/**
	 * Recursively sets a {@link Typeface} to all
	 * {@link TextView}s in a {@link ViewGroup}.
	 */
	public static final void setAppFont(ViewGroup mContainer, Typeface mFont, boolean reflect)
	{
	    if (mContainer == null || mFont == null) return;

	    final int mCount = mContainer.getChildCount();

	    // Loop through all of the children.
	    for (int i = 0; i < mCount; ++i)
	    {
	        final View mChild = mContainer.getChildAt(i);
	        if (mChild instanceof TextView)
	        {
	            // Set the font if it is a TextView.
	            ((TextView) mChild).setTypeface(mFont);
	        }
	        else if (mChild instanceof ViewGroup)
	        {
	            // Recursively attempt another ViewGroup.
	            setAppFont((ViewGroup) mChild, mFont , true);
	        }
	        else if (reflect)
	        {
	            try {
	                Method mSetTypeface = mChild.getClass().getMethod("setTypeface", Typeface.class);
	                mSetTypeface.invoke(mChild, mFont); 
	            } catch (Exception e) { /* Do something... */ }
	        }
	    }
	}
    
}