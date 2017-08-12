package app.customcomponents;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.PopupMenu;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.project.homey.R;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import app.activities.interfaces.IHasImage;
import app.activities.interfaces.IHasText;
import app.logic.appcomponents.Group;
import app.logic.appcomponents.Task;
import app.logic.appcomponents.User;
import app.logic.managers.DBManager;
import app.logic.managers.DragManager;
import app.logic.managers.Services;
import callback.GotoGroupPageCallBack;
import callback.UpdateCallBack;

public class ScrollHorizontalWithItems extends HorizontalScrollView
{
    private final LinearLayout linearLayout;
    private HashMap itemsMap = new HashMap();

    public ScrollHorizontalWithItems(Context context) {
        super(context);

        linearLayout = new LinearLayout(context);
        this.addView(linearLayout);
    }

    public ScrollHorizontalWithItems(Context context, AttributeSet attrs) {
        super(context, attrs);

        linearLayout = new LinearLayout(context);
        this.addView(linearLayout);
    }

    public ScrollHorizontalWithItems(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

        linearLayout = new LinearLayout(context);
        this.addView(linearLayout);
    }

    public <T extends IHasText & IHasImage> void SetScrollerItems(ArrayList<T> items, @LinearLayoutCompat.OrientationMode int orientation, GotoGroupPageCallBack callBack) {
        linearLayout.removeAllViews();
        itemsMap.clear();

        linearLayout.setOrientation(orientation);
        LayoutParams layoutParamsTextView = new LayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        LayoutParams layoutParamsImageButton = new LayoutParams(new LinearLayout.LayoutParams(300, 300));
        AtomicInteger i = new AtomicInteger(0);

        setLayoutParamsMargin(layoutParamsImageButton);
        setLayoutParamsMargin(layoutParamsTextView);

        for (T item : items)
        {
            LinearLayout verticalLinearLayout = new LinearLayout(this.getContext());
            CircleImageButton itemIcon = new CircleImageButton(this.getContext(), item.GetImage(), R.mipmap.ic_group_default);
            TextView textView = new TextView(this.getContext());

            verticalLinearLayout.setOrientation(LinearLayout.VERTICAL);

            textView.setLayoutParams(layoutParamsTextView);
            textView.setText(item.GetName());
            textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            textView.setTextColor(Color.BLACK);

            itemIcon.setOnClickListener(v -> callBack.onSuccess(item));
            LinearLayout.LayoutParams imageButtonLayoutParams = new LinearLayout.LayoutParams(getDpSize(100), getDpSize(100));
            itemIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            itemIcon.setLayoutParams(imageButtonLayoutParams);
            itemIcon.setBackgroundColor(getResources().getColor(R.color.gentle_gray, null));

            verticalLinearLayout.addView(itemIcon);
            verticalLinearLayout.addView(textView);
            linearLayout.addView(verticalLinearLayout);

            //drag and drop set:
            setGroupIconDrop(itemIcon, item);
            itemsMap.put(item.GetName(), itemIcon);
        }
    }

    private <T extends IHasText & IHasImage> void setGroupIconDrop(CircleImageButton itemIcon, T item)
    {
        DragManager dragManager = (DragManager) Services.GetService(DragManager.class);

        itemIcon.setOnDragListener(new OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent)
            {
                int action = dragEvent.getAction();
                switch (action)
                {
                    case DragEvent.ACTION_DRAG_STARTED:
                        //none
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (item instanceof User)
                        {
                            dragManager.setDroppedUser((User) item);
                            view.setBackgroundResource(R.color.light_gray);
                        }
                        else
                        {
                            dragManager.setDroppedUser(null);
                        }

                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        dragManager.setDroppedUser(null);
                        itemIcon.setBackgroundColor(Color.TRANSPARENT);
                        break;
                    case DragEvent.ACTION_DROP:
                        dragManager.AssignTaskToUser(getContext());
                        itemIcon.setBackgroundColor(Color.TRANSPARENT);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        //none
                    default:
                        break;
                }

                return true;
            }
        });
    }

    public int getDpSize(int size)
    {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (size * scale + 0.5f);
    }

    private void setLayoutParamsMargin(LayoutParams layoutParams)
    {
        layoutParams.leftMargin = 15;
        layoutParams.rightMargin = 15;
        layoutParams.topMargin = 15;
        layoutParams.bottomMargin = 15;
    }

    public void SetOnLongClick(Group group)
    {
        for (Object object : itemsMap.entrySet())
        {
            Map.Entry<String, CircleImageButton> entry = (Map.Entry<String, CircleImageButton>) object;
            CircleImageButton image = entry.getValue();

            image.setOnLongClickListener( view ->
            {
                PopupMenu popupMenu = new PopupMenu(getContext(), image);
                popupMenu.getMenuInflater().inflate(R.menu.group_page_user_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        switch (item.getItemId())
                        {
                            case R.id.promoteAdmin:
                                // TODO: DBMANAGER. promote user (entry.getKey(), group.getId).
                                ((DBManager) Services.GetService(DBManager.class)).MakeUserAdmin(group.GetId(), entry.getKey(), new UpdateCallBack() {
                                    @Override
                                    public void onSuccess()
                                    {
                                        Toast.makeText(getContext(), "Promotion success", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(String errorMessage)
                                    {
                                        Toast.makeText(getContext(), "Promotion failed", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                break;
                            case R.id.removeFromGroup:
                                Toast.makeText(getContext(), "Remove user id: " + entry.getKey() + "from group id: "+ group.GetId(), Toast.LENGTH_SHORT).show();
                                // TODO: DBMANAGER. remove user (entry.getKey(), group.getId).
                                break;
                            default:
                                Toast.makeText(getContext(), "default", Toast.LENGTH_SHORT).show();
                                break;
                        }

                        return true;
                    }
                });

                popupMenu.show();

                //TODO: set long click.
                return true;
            });
        }
    }
}
