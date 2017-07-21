package app.customcomponents;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.project.homey.R;

import app.logic.managers.EnvironmentManager;
import app.logic.managers.Services;

/**
 * TODO: DELETE THIS CLASS
 */
public class Header extends View {

    private TextView screenName;

    public Header(Context context) {
        super(context);
        screenName = (TextView) findViewById(R.id.textViewScreenName);
        screenName.setText("barak");
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.header_items);
//        screenName = (TextView) findViewById(R.id.textViewScreenName);
//        screenName.setText(((EnvironmentManager) (Services.GetService(EnvironmentManager.class))).GetScreenName());
//    }
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState);
//    }

    public void ChangeScreenName(){
        screenName.setText(((EnvironmentManager) (Services.GetService(EnvironmentManager.class))).GetScreenName());
    }
}
