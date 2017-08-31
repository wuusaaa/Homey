package app.customcomponents;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.PopupMenu;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.homey.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import app.activities.interfaces.IHasImage;
import app.activities.interfaces.IHasText;
import app.logic.Notification.MyFirebaseMessagingService;
import app.logic.appcomponents.Group;
import app.logic.appcomponents.User;
import app.logic.managers.ActivityChangeManager;
import app.logic.managers.DBManager;
import app.logic.managers.DragManager;
import app.logic.managers.Services;
import callback.GotoGroupPageCallBack;
import callback.UpdateCallBack;

public class ScrollHorizontalWithItems extends HorizontalScrollView {
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

    public ScrollHorizontalWithItems(Context context, AttributeSet attrs, int defStyle) {
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

        for (T item : items) {
            LinearLayout verticalLinearLayout = new LinearLayout(this.getContext());
            CircleImageButton itemIcon = new CircleImageButton(this.getContext(), item.GetImage(), R.drawable.icon_group_def);

            TextView textView = new TextView(this.getContext());

            verticalLinearLayout.setOrientation(LinearLayout.VERTICAL);

            textView.setLayoutParams(layoutParamsTextView);
            textView.setText(item.GetName());
            textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            textView.setTextColor(Color.BLACK);

            itemIcon.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.onSuccess(item);
                }
            });
            LinearLayout.LayoutParams imageButtonLayoutParams = new LinearLayout.LayoutParams(getDpSize(95), getDpSize(95));
            itemIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            itemIcon.setLayoutParams(imageButtonLayoutParams);
//            itemIcon.setBackgroundColor(getResources().getColor(R.color.gentle_gray, null));

            verticalLinearLayout.addView(itemIcon);
            verticalLinearLayout.addView(textView);
            linearLayout.addView(verticalLinearLayout);

            //drag and drop set:
            setGroupIconDrop(itemIcon, item);
            itemsMap.put(item.GetId(), itemIcon);
        }
    }

    private <T extends IHasText & IHasImage> void setGroupIconDrop(CircleImageButton itemIcon, T item) {
        DragManager dragManager = (DragManager) Services.GetService(DragManager.class);

        itemIcon.setOnDragListener(new OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                int action = dragEvent.getAction();
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        //none
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (item instanceof User) {
                            dragManager.setDroppedUser((User) item);
                            view.setBackgroundResource(R.color.light_gray);
                        } else {
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

    public int getDpSize(int size) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (size * scale + 0.5f);
    }

    private void setLayoutParamsMargin(LayoutParams layoutParams) {
        layoutParams.leftMargin = 15;
        layoutParams.rightMargin = 15;
        layoutParams.topMargin = 15;
        layoutParams.bottomMargin = 15;
    }

    public void SetOnLongClick(Group group) {
        for (Object object : itemsMap.entrySet()) {
            Map.Entry<String, CircleImageButton> entry = (Map.Entry<String, CircleImageButton>) object;
            CircleImageButton image = entry.getValue();

            image.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(ScrollHorizontalWithItems.this.getContext(), image);
                    popupMenu.getMenuInflater().inflate(R.menu.group_page_user_menu, popupMenu.getMenu());

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            String userId = entry.getKey();
                            switch (item.getItemId()) {
                                case R.id.promoteAdmin:
                                    // TODO: DBMANAGER. promote user (entry.getKey(), group.getId).
                                    ((DBManager) Services.GetService(DBManager.class)).MakeUserAdmin(group.GetId(), userId, new UpdateCallBack() {
                                        @Override
                                        public void onSuccess() {
                                            Toast.makeText(getContext(), "Promotion success", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(String errorMessage) {
                                            Toast.makeText(getContext(), "Promotion failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    break;
                                case R.id.removeFromGroup:
                                    ((DBManager) Services.GetService(DBManager.class)).LeaveGroup(group.GetId(), userId, new UpdateCallBack() {
                                        @Override
                                        public void onSuccess() {
                                            Toast.makeText(getContext(), "Member has removed", Toast.LENGTH_SHORT).show();
                                            MyFirebaseMessagingService.SendPushNotification(userId, "You have been removed from group: " + group.GetName());
                                            ((ActivityChangeManager) Services.GetService(ActivityChangeManager.class)).SetGroupActivity(getContext(), group);
                                        }

                                        @Override
                                        public void onFailure(String errorMessage) {

                                        }
                                    });
                                    break;
                                default:
                                    break;
                            }

                            return true;
                        }
                    });

                    popupMenu.show();

                    //TODO: set long click.
                    return true;
                }
            });
        }
    }
}
