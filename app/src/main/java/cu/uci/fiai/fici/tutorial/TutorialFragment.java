package cu.uci.fiai.fici.tutorial;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.joanzapata.iconify.widget.IconTextView;

import cu.uci.fiai.fici.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TutorialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TutorialFragment extends Fragment {

    private static final String ARG_POSITION = "P051T10N";

    private int mPosition;
    private ImageView ivDrawer = null;
    private Animation animSlideRight = null;
    private int mTextSize = 1;

    public TutorialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position Parameter 1.
     * @return A new instance of fragment TutorialFragment.
     */
    public static TutorialFragment newInstance(int position) {
        TutorialFragment fragment = new TutorialFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mPosition = getArguments().getInt(ARG_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mTextSize = getResources().getInteger(R.integer.text_paint);

        switch (mPosition) {
            case 0:
                return tutorial1(inflater, container);
            case 1:
                return tutorial2(inflater, container);
            case 2:
                return tutorial3(inflater, container);
            case 3:
                return tutorial4(inflater, container);
            case 4:
                return tutorial5(inflater, container);
            case 5:
                return tutorial6(inflater, container);
            case 6:
                return tutorial7(inflater, container);
            case 7:
                return tutorial8(inflater, container);
            default:
                return tutorial9(inflater, container);
        }
    }

    private View tutorial1(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_tutorial_1, container, false);
    }

    private View tutorial2(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_tutorial_2, container, false);

        final ImageView ivActivity = (ImageView) view.findViewById(R.id.ivBackground);
        ivDrawer = (ImageView) view.findViewById(R.id.ivDrawer);
        final ImageView ivMenuUCI = (ImageView) view.findViewById(R.id.ivTutoMenuUCI);
        final LinearLayout llTutoUCI = (LinearLayout) view.findViewById(R.id.llTutoUCI);

        // Obtener fondo de activity 217x400 480x800
        Bitmap bgActivity = BitmapFactory.decodeResource(getResources(),
                R.drawable.background);
        llTutoUCI.setVisibility(View.INVISIBLE);

        paintActivity(ivActivity, bgActivity); // Dibujar Activity
        paintDrawer(ivDrawer, bgActivity.getWidth(),
                bgActivity.getHeight()); // Dibujar Drawer
        paintTutoMenuUCI(ivMenuUCI, bgActivity.getWidth(),
                bgActivity.getHeight()); // Dibujar tutorial del menu UCI

        animSlideRight = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_right_drawer);
        final Animation animSlideLeft = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_left_drawer);
        final Animation animFadeInMenuUCI = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_in);
        final Animation animFadeOutMenuUCI = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_out);
        final Animation animFadeInTutoUCI = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_in);
        final Animation animFadeOutTutoUCI = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_out);
        final Animation animWaiting = AnimationUtils.loadAnimation(getContext(),
                R.anim.waiting_6s);

        animSlideRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                ivMenuUCI.startAnimation(animFadeInMenuUCI);
                ivMenuUCI.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeInMenuUCI.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                ivMenuUCI.startAnimation(animFadeOutMenuUCI);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeOutMenuUCI.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                llTutoUCI.startAnimation(animFadeInTutoUCI);
                llTutoUCI.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ivDrawer.startAnimation(animSlideLeft);
                ivMenuUCI.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animSlideLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoUCI.startAnimation(animWaiting);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animWaiting.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoUCI.startAnimation(animFadeOutTutoUCI);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeOutTutoUCI.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoUCI.setVisibility(View.INVISIBLE);
                ivDrawer.startAnimation(animSlideRight);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        ivDrawer.startAnimation(animSlideRight);

        return view;
    }

    private void paintActivity(ImageView imageView, Bitmap bgActivity) {
        // Dimensiones de la imagen
        final int width = bgActivity.getWidth();
        final int height = bgActivity.getHeight();

        // Definir el mapa de bits
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // Definir el lienzo
        Canvas canvas = new Canvas(bitmap);
        // estilos
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#FFFFFF"));
        paint.setAntiAlias(true);
        canvas.drawPaint(paint);

        // pintar fondo de la activity
        canvas.drawBitmap(bgActivity, 0, 0, paint);

        // pintar action bar
        paint.setColor(Color.parseColor("#1A237E"));
        canvas.drawRect(0, 0, width, 40, paint);

        // pintar menu opciones
        paint.setColor(Color.parseColor("#FFFFFF"));
        canvas.drawCircle(width - 10, 15, 2, paint);
        canvas.drawCircle(width - 10, 20, 2, paint);
        canvas.drawCircle(width - 10, 25, 2, paint);

        // pintar drawer toggle
        canvas.drawRect(10, 12, 28, 15, paint);
        canvas.drawRect(10, 17, 28, 20, paint);
        canvas.drawRect(10, 22, 28, 25, paint);

        //agrega el bitmap al ImageView Activity
        imageView.setImageBitmap(bitmap);
    }

    private void paintDrawer(ImageView imageView, int width, int height) {
        // Definir el mapa de bits
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // Definir el lienzo
        Canvas canvas = new Canvas(bitmap);
        // Estilos
        Paint paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        paint.setAntiAlias(true);
        canvas.drawPaint(paint);

        // Dibujar fondo del drawer
        paint.setColor(Color.parseColor("#C5CAE9"));
        canvas.drawRect(0, 0, (width / 2.f) + 60, height, paint);

        // Dibujar header del drawer
        paint.setColor(Color.parseColor("#05235E"));
        canvas.drawRect(0, 0, (width / 2.f) + 60, height / 3.f, paint);

        // Dibujar menu del drawer
        // iconos
        canvas.drawCircle((width / 20.f) + 5, (height / 3.f) + 20, 5, paint);
        canvas.drawCircle((width / 20.f) + 5, (height / 3.f) + 40, 5, paint);
        canvas.drawCircle((width / 20.f) + 5, (height / 3.f) + 60, 5, paint);
        canvas.drawCircle((width / 20.f) + 5, (height / 3.f) + 80, 5, paint);
        canvas.drawCircle((width / 20.f) + 5, (height / 3.f) + 100, 5, paint);
        canvas.drawCircle((width / 20.f) + 5, (height / 3.f) + 120, 5, paint);
        canvas.drawCircle((width / 20.f) + 5, (height / 3.f) + 140, 5, paint);
        canvas.drawCircle((width / 20.f) + 5, (height / 3.f) + 160, 5, paint);
        canvas.drawCircle((width / 20.f) + 5, (height / 3.f) + 180, 5, paint);

        // texto
        int textSpace = getResources().getInteger(R.integer.text_space_1);
        paint.setTextSize(mTextSize * 1.f);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(getString(R.string.string_uci_sig), (width / 20.f) + textSpace,
                (height / 3.f) + 24, paint);
        canvas.drawText(getString(R.string.string_fici_sig), (width / 20.f) + textSpace,
                (height / 3.f) + 45, paint);
        canvas.drawText(getString(R.string.string_cons_direcc), (width / 10.f) + 5,
                (height / 3.f) + 64, paint);
        canvas.drawText(getString(R.string.string_pgs), (width / 10.f) + 5,
                (height / 3.f) + 85, paint);
        canvas.drawText(getString(R.string.string_events), (width / 10.f) + 5,
                (height / 3.f) + 103, paint);
        canvas.drawText(getString(R.string.string_map), (width / 10.f) + 5,
                (height / 3.f) + 125, paint);
        canvas.drawText(getString(R.string.string_form_inv), (width / 10.f) + 5,
                (height / 3.f) + 144, paint);

        paint.setColor(Color.parseColor("#1A237E"));
        canvas.drawRect((width / 10.f) + 5, (height / 3.f) + 155, (width / 10.f) + 90,
                (height / 3.f) + 165, paint);
        canvas.drawRect((width / 10.f) + 5, (height / 3.f) + 175, (width / 10.f) + 70,
                (height / 3.f) + 185, paint);

        //agrega el bitmap al ImageView Drawer
        imageView.setImageBitmap(bitmap);
    }

    private void paintTutoMenuUCI(ImageView imageView, int width, int height) {
        // Definir el mapa de bits
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // Definir el lienzo
        Canvas canvas = new Canvas(bitmap);
        // Estilos
        Paint paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        paint.setAntiAlias(true);
        canvas.drawPaint(paint);

        // Dibujar fondo
        paint.setColor(Color.parseColor("#F0FFFFFF"));
        canvas.drawRect(0, height / 3.f, width, (height / 3.f) + 10, paint);
        canvas.drawRect(0, (height / 3.f) + 28, width, height, paint);
        canvas.drawRect((width / 2.f) + 60, (height / 3.f) + 10, width,
                (height / 3.f) + 28, paint);
        paint.setColor(Color.parseColor("#90000000"));
        canvas.drawRect((width / 2.f) + 60, 0, width, height, paint);

        //agregar el bitmap al ImageView
        imageView.setImageBitmap(bitmap);
        imageView.setVisibility(View.INVISIBLE);
    }

    private View tutorial3(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_tutorial_3, container, false);

        final ImageView ivActivity = (ImageView) view.findViewById(R.id.ivBackground);
        ivDrawer = (ImageView) view.findViewById(R.id.ivDrawer);
        final ImageView ivMenuFICI = (ImageView) view.findViewById(R.id.ivTutoMenuFICI);
        final LinearLayout llTutoFICI = (LinearLayout) view.findViewById(R.id.llTutoFICI);

        // Obtener fondo de activity 217x400
        Bitmap bgActivity = BitmapFactory.decodeResource(getResources(),
                R.drawable.background);
        llTutoFICI.setVisibility(View.INVISIBLE);

        paintActivity(ivActivity, bgActivity); // Dibujar Activity
        paintDrawer(ivDrawer, bgActivity.getWidth(),
                bgActivity.getHeight()); // Dibujar Drawer
        paintTutoMenuFICI(ivMenuFICI, bgActivity.getWidth(),
                bgActivity.getHeight()); // Dibujar tutorial del menu FICI

        animSlideRight = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_right_drawer);
        final Animation animSlideLeft = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_left_drawer);
        final Animation animFadeInMenuFICI = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_in);
        final Animation animFadeOutMenuFICI = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_out);
        final Animation animFadeInTutoFICI = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_in);
        final Animation animFadeOutTutoFICI = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_out);
        final Animation animWaiting = AnimationUtils.loadAnimation(getContext(),
                R.anim.waiting_6s);

        animSlideRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                ivMenuFICI.startAnimation(animFadeInMenuFICI);
                ivMenuFICI.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeInMenuFICI.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                ivMenuFICI.startAnimation(animFadeOutMenuFICI);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeOutMenuFICI.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                llTutoFICI.startAnimation(animFadeInTutoFICI);
                llTutoFICI.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ivDrawer.startAnimation(animSlideLeft);
                ivMenuFICI.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animSlideLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoFICI.startAnimation(animWaiting);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animWaiting.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoFICI.startAnimation(animFadeOutTutoFICI);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeOutTutoFICI.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoFICI.setVisibility(View.INVISIBLE);
                ivDrawer.startAnimation(animSlideRight);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        ivDrawer.startAnimation(animSlideRight);

        return view;
    }

    private void paintTutoMenuFICI(ImageView imageView, int width, int height) {
        // Definir el mapa de bits
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // Definir el lienzo
        Canvas canvas = new Canvas(bitmap);
        // Estilos
        Paint paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        paint.setAntiAlias(true);
        canvas.drawPaint(paint);

        // Dibujar fondo
        paint.setColor(Color.parseColor("#F0FFFFFF"));
        canvas.drawRect(0, height / 3.f, width, (height / 3.f) + 28, paint);
        canvas.drawRect(0, (height / 3.f) + 50, width, height, paint);
        canvas.drawRect((width / 2.f) + 60, (height / 3.f) + 28,
                width, (height / 3.f) + 50, paint);
        paint.setColor(Color.parseColor("#90000000"));
        canvas.drawRect((width / 2.f) + 60, 0, width, height, paint);

        //agregar el bitmap al ImageView
        imageView.setImageBitmap(bitmap);
        imageView.setVisibility(View.INVISIBLE);
    }

    private View tutorial4(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_tutorial_4, container, false);

        final ImageView ivActivity = (ImageView) view.findViewById(R.id.ivBackground);
        ivDrawer = (ImageView) view.findViewById(R.id.ivDrawer);
        final ImageView ivMenuCD = (ImageView) view.findViewById(R.id.ivTutoMenuCD);
        final LinearLayout llTutoCD = (LinearLayout) view.findViewById(R.id.llTutoCD);

        // Obtener fondo de activity 217x400
        Bitmap bgActivity = BitmapFactory.decodeResource(getResources(),
                R.drawable.background);
        llTutoCD.setVisibility(View.INVISIBLE);

        paintActivity(ivActivity, bgActivity); // Dibujar Activity
        paintDrawer(ivDrawer, bgActivity.getWidth(),
                bgActivity.getHeight()); // Dibujar Drawer
        paintTutoMenuCD(ivMenuCD, bgActivity.getWidth(),
                bgActivity.getHeight()); // Dibujar tutorial del menu CD

        animSlideRight = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_right_drawer);
        final Animation animSlideLeft = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_left_drawer);
        final Animation animFadeInMenuCD = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_in);
        final Animation animFadeOutMenuCD = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_out);
        final Animation animFadeInTutoCD = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_in);
        final Animation animFadeOutTutoCD = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_out);
        final Animation animWaiting = AnimationUtils.loadAnimation(getContext(),
                R.anim.waiting_6s);

        animSlideRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                ivMenuCD.startAnimation(animFadeInMenuCD);
                ivMenuCD.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeInMenuCD.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                ivMenuCD.startAnimation(animFadeOutMenuCD);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeOutMenuCD.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                llTutoCD.startAnimation(animFadeInTutoCD);
                llTutoCD.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ivDrawer.startAnimation(animSlideLeft);
                ivMenuCD.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animSlideLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoCD.startAnimation(animWaiting);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animWaiting.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoCD.startAnimation(animFadeOutTutoCD);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeOutTutoCD.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoCD.setVisibility(View.INVISIBLE);
                ivDrawer.startAnimation(animSlideRight);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        ivDrawer.startAnimation(animSlideRight);

        return view;
    }

    private void paintTutoMenuCD(ImageView imageView, int width, int height) {
        // Definir el mapa de bits
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // Definir el lienzo
        Canvas canvas = new Canvas(bitmap);
        // Estilos
        Paint paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        paint.setAntiAlias(true);
        canvas.drawPaint(paint);

        // Dibujar fondo
        paint.setColor(Color.parseColor("#F0FFFFFF"));
        canvas.drawRect(0, height / 3.f, width, (height / 3.f) + 50, paint);
        canvas.drawRect(0, (height / 3.f) + 70, width, height, paint);
        canvas.drawRect((width / 2.f) + 60, (height / 3.f) + 50, width,
                (height / 3.f) + 70, paint);
        paint.setColor(Color.parseColor("#90000000"));
        canvas.drawRect((width / 2.f) + 60, 0, width, height, paint);

        //agregar el bitmap al ImageView
        imageView.setImageBitmap(bitmap);
        imageView.setVisibility(View.INVISIBLE);
    }

    private View tutorial5(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_tutorial_5, container, false);

        final ImageView ivActivity = (ImageView) view.findViewById(R.id.ivBackground);
        ivDrawer = (ImageView) view.findViewById(R.id.ivDrawer);
        final ImageView ivMenuPG = (ImageView) view.findViewById(R.id.ivTutoMenuPG);
        final LinearLayout llTutoPG = (LinearLayout) view.findViewById(R.id.llTutoPG);

        // Obtener fondo de activity 217x400
        Bitmap bgActivity = BitmapFactory.decodeResource(getResources(),
                R.drawable.background);
        llTutoPG.setVisibility(View.INVISIBLE);

        paintActivity(ivActivity, bgActivity); // Dibujar Activity
        paintDrawer(ivDrawer, bgActivity.getWidth(),
                bgActivity.getHeight()); // Dibujar Drawer
        paintTutoMenuPG(ivMenuPG, bgActivity.getWidth(),
                bgActivity.getHeight()); // Dibujar tutorial del menu PG

        animSlideRight = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_right_drawer);
        final Animation animSlideLeft = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_left_drawer);
        final Animation animFadeInMenuPG = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_in);
        final Animation animFadeOutMenuPG = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_out);
        final Animation animFadeInTutoPG = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_in);
        final Animation animFadeOutTutoPG = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_out);
        final Animation animWaiting = AnimationUtils.loadAnimation(getContext(),
                R.anim.waiting_6s);

        animSlideRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                ivMenuPG.startAnimation(animFadeInMenuPG);
                ivMenuPG.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeInMenuPG.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                ivMenuPG.startAnimation(animFadeOutMenuPG);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeOutMenuPG.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                llTutoPG.startAnimation(animFadeInTutoPG);
                llTutoPG.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ivDrawer.startAnimation(animSlideLeft);
                ivMenuPG.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animSlideLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoPG.startAnimation(animWaiting);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animWaiting.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoPG.startAnimation(animFadeOutTutoPG);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeOutTutoPG.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoPG.setVisibility(View.INVISIBLE);
                ivDrawer.startAnimation(animSlideRight);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        ivDrawer.startAnimation(animSlideRight);

        return view;
    }

    private void paintTutoMenuPG(ImageView imageView, int width, int height) {
        // Definir el mapa de bits
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // Definir el lienzo
        Canvas canvas = new Canvas(bitmap);
        // Estilos
        Paint paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        paint.setAntiAlias(true);
        canvas.drawPaint(paint);

        // Dibujar fondo
        paint.setColor(Color.parseColor("#F0FFFFFF"));
        canvas.drawRect(0, height / 3.f, width, (height / 3.f) + 70, paint);
        canvas.drawRect(0, (height / 3.f) + 90, width, height, paint);
        canvas.drawRect((width / 2.f) + 60, (height / 3.f) + 70, width,
                (height / 3.f) + 90, paint);
        paint.setColor(Color.parseColor("#90000000"));
        canvas.drawRect((width / 2.f) + 60, 0, width, height, paint);

        //agregar el bitmap al ImageView
        imageView.setImageBitmap(bitmap);
        imageView.setVisibility(View.INVISIBLE);
    }

    private View tutorial6(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_tutorial_6, container, false);

        final ImageView ivActivity = (ImageView) view.findViewById(R.id.ivBackground);
        ivDrawer = (ImageView) view.findViewById(R.id.ivDrawer);
        final ImageView ivMenuEvents = (ImageView) view.findViewById(R.id.ivTutoMenuEvents);
        final ImageView ivDetails = (ImageView) view.findViewById(R.id.ivDetailsEvents);
        final LinearLayout llTutoEvents = (LinearLayout) view.findViewById(R.id.llTutoEvents);
        final LinearLayout llTutoEvents2 = (LinearLayout) view.findViewById(R.id.llTutoEvents2);
        final IconTextView itvTutoLast = (IconTextView) view.findViewById(R.id.itvTutoEventExit);

        // Obtener fondo de activity
        Bitmap bgActivity = BitmapFactory.decodeResource(getResources(),
                R.drawable.background);
        llTutoEvents.setVisibility(View.INVISIBLE);
        llTutoEvents2.setVisibility(View.INVISIBLE);
        ivDetails.setVisibility(View.INVISIBLE);
        itvTutoLast.setVisibility(View.INVISIBLE);

        paintActivity(ivActivity, bgActivity); // Dibujar Activity
        paintDrawer(ivDrawer, bgActivity.getWidth(),
                bgActivity.getHeight()); // Dibujar Drawer
        paintTutoMenuEvents(ivMenuEvents, bgActivity.getWidth(), bgActivity.getHeight());
        paintActivityDetails(ivDetails, bgActivity, "Detalles de actividad"); // Dibujar Activity Events

        animSlideRight = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_right_drawer);
        final Animation animSlideLeft = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_left_drawer);
        final Animation animFadeInMenuEvent = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_in);
        final Animation animFadeOutMenuEvent = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_out);
        final Animation animFadeInTutoEvent = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_in);
        final Animation animFadeInTutoEvent2 = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_in);
        final Animation animFadeInTutoEvent3 = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_in);
        final Animation animFadeOutTutoEvent = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_out);
        final Animation animFadeOutTutoEvent2 = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_out);
        final Animation animFadeOutTutoEvent3 = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_out);
        final Animation animWaiting = AnimationUtils.loadAnimation(getContext(),
                R.anim.waiting_6s);
        final Animation animWaiting2 = AnimationUtils.loadAnimation(getContext(),
                R.anim.waiting_7s);
        final Animation animWaiting3 = AnimationUtils.loadAnimation(getContext(),
                R.anim.waiting_7s);
        final Animation animSlideDetDown = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_details_down);
        final Animation animSlideDetUp = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_details_up);

        animSlideRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                ivMenuEvents.startAnimation(animFadeInMenuEvent);
                ivMenuEvents.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeInMenuEvent.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                ivMenuEvents.startAnimation(animFadeOutMenuEvent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeOutMenuEvent.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                llTutoEvents.startAnimation(animFadeInTutoEvent);
                llTutoEvents.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ivDrawer.startAnimation(animSlideLeft);
                ivMenuEvents.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animSlideLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoEvents.startAnimation(animWaiting);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animWaiting.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoEvents.startAnimation(animFadeOutTutoEvent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeOutTutoEvent.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoEvents.setVisibility(View.INVISIBLE);
                ivDetails.startAnimation(animSlideDetDown);
                ivDetails.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animSlideDetDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoEvents2.startAnimation(animFadeInTutoEvent2);
                llTutoEvents2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeInTutoEvent2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoEvents2.startAnimation(animWaiting2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animWaiting2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoEvents2.startAnimation(animFadeOutTutoEvent2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        animFadeOutTutoEvent2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                itvTutoLast.startAnimation(animFadeInTutoEvent3);
                itvTutoLast.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoEvents2.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeInTutoEvent3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                itvTutoLast.startAnimation(animWaiting3);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animWaiting3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                itvTutoLast.startAnimation(animFadeOutTutoEvent3);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        animFadeOutTutoEvent3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                ivDetails.startAnimation(animSlideDetUp);
                itvTutoLast.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animSlideDetUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                ivDetails.setVisibility(View.INVISIBLE);
                ivDrawer.startAnimation(animSlideRight);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        ivDrawer.startAnimation(animSlideRight);

        return view;
    }

    private void paintTutoMenuEvents(ImageView imageView, int width, int height) {
        // Definir el mapa de bits
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // Definir el lienzo
        Canvas canvas = new Canvas(bitmap);
        // Estilos
        Paint paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        paint.setAntiAlias(true);
        canvas.drawPaint(paint);

        // Dibujar fondo
        paint.setColor(Color.parseColor("#F0FFFFFF"));
        canvas.drawRect(0, height / 3.f, width, (height / 3.f) + 90, paint);
        canvas.drawRect(0, (height / 3.f) + 110, width, height, paint);
        canvas.drawRect((width / 2.f) + 60, (height / 3.f) + 90, width,
                (height / 3.f) + 110, paint);
        paint.setColor(Color.parseColor("#90000000"));
        canvas.drawRect((width / 2.f) + 60, 0, width, height, paint);

        //agregar el bitmap al ImageView
        imageView.setImageBitmap(bitmap);
        imageView.setVisibility(View.INVISIBLE);
    }

    private void paintActivityDetails(ImageView imageView, Bitmap bgActivity,
                                      String title) {
        // Dimensiones de la imagen
        final int width = bgActivity.getWidth();
        final int height = bgActivity.getHeight();

        // Definir el mapa de bits
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // Definir el lienzo
        Canvas canvas = new Canvas(bitmap);
        // estilos
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#FFFFFF"));
        paint.setAntiAlias(true);
        canvas.drawPaint(paint);

        // pintar fondo de la activity
        canvas.drawBitmap(bgActivity, 0, 0, paint);

        // pintar action bar
        paint.setColor(Color.parseColor("#1A237E"));
        canvas.drawRect(0, 0, width, 40, paint);

        // pintar menu opciones
        paint.setColor(Color.parseColor("#FFFFFF"));
        paint.setStrokeWidth(2);
        canvas.drawLine(width - 40, 10, width - 20, 30, paint);
        canvas.drawLine(width - 20, 10, width - 40, 30, paint) ;

        // pintar titulo
        int textSpace = getResources().getInteger(R.integer.text_space_1);
        paint.setTextSize(mTextSize * 1.f);
        paint.setFakeBoldText(true);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(title, textSpace, 25, paint);

        //agrega el bitmap al ImageView Activity
        imageView.setImageBitmap(bitmap);
    }

    private View tutorial7(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_tutorial_7, container, false);

        final ImageView ivActivity = (ImageView) view.findViewById(R.id.ivBackground);
        ivDrawer = (ImageView) view.findViewById(R.id.ivDrawer);
        final ImageView ivMenuMap = (ImageView) view.findViewById(R.id.ivTutoMenuMap);
        final LinearLayout llTutoMap = (LinearLayout) view.findViewById(R.id.llTutoMap);

        // Obtener fondo de activity
        Bitmap bgActivity = BitmapFactory.decodeResource(getResources(),
                R.drawable.background);
        llTutoMap.setVisibility(View.INVISIBLE);

        paintActivity(ivActivity, bgActivity); // Dibujar Activity
        paintDrawer(ivDrawer, bgActivity.getWidth(),
                bgActivity.getHeight()); // Dibujar Drawer

        paintTutoMenuMap(ivMenuMap, bgActivity.getWidth(),
                bgActivity.getHeight()); // Dibujar tutorial del menu PG

        animSlideRight = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_right_drawer);
        final Animation animSlideLeft = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_left_drawer);
        final Animation animFadeInMenuMap = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_in);
        final Animation animFadeOutMenuMap = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_out);
        final Animation animFadeInTutoPG = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_in);
        final Animation animFadeOutTutoMap = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_out);
        final Animation animWaiting = AnimationUtils.loadAnimation(getContext(),
                R.anim.waiting_6s);

        animSlideRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                ivMenuMap.startAnimation(animFadeInMenuMap);
                ivMenuMap.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeInMenuMap.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                ivMenuMap.startAnimation(animFadeOutMenuMap);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeOutMenuMap.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                llTutoMap.startAnimation(animFadeInTutoPG);
                llTutoMap.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ivDrawer.startAnimation(animSlideLeft);
                ivMenuMap.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animSlideLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoMap.startAnimation(animWaiting);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animWaiting.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoMap.startAnimation(animFadeOutTutoMap);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeOutTutoMap.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoMap.setVisibility(View.INVISIBLE);
                ivDrawer.startAnimation(animSlideRight);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        ivDrawer.startAnimation(animSlideRight);

        return view;
    }

    private void paintTutoMenuMap(ImageView imageView, int width, int height) {
        // Definir el mapa de bits
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // Definir el lienzo
        Canvas canvas = new Canvas(bitmap);
        // Estilos
        Paint paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        paint.setAntiAlias(true);
        canvas.drawPaint(paint);

        // Dibujar fondo
        paint.setColor(Color.parseColor("#F0FFFFFF"));
        canvas.drawRect(0, height / 3.f, (width / 2.f) + 61,
                (height / 3.f) + 110, paint);
        canvas.drawRect(0, (height / 3.f) + 130, (width / 2.f) + 61,
                height, paint);
        paint.setColor(Color.parseColor("#90000000"));
        canvas.drawRect((width / 2.f) + 60, 0, width, height, paint);

        //agregar el bitmap al ImageView
        imageView.setImageBitmap(bitmap);
        imageView.setVisibility(View.INVISIBLE);
    }

    private View tutorial8(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_tutorial_8, container, false);

        final ImageView ivActivity = (ImageView) view.findViewById(R.id.ivBackground);
        ivDrawer = (ImageView) view.findViewById(R.id.ivDrawer);
        final ImageView ivMenuGTCE = (ImageView) view.findViewById(R.id.ivTutoMenuGTCE);
        final LinearLayout llTutoGTCE = (LinearLayout) view.findViewById(R.id.llTutoGTCE);

        // Obtener fondo de activity 217x400
        Bitmap bgActivity = BitmapFactory.decodeResource(getResources(),
                R.drawable.background);
        llTutoGTCE.setVisibility(View.INVISIBLE);

        paintActivity(ivActivity, bgActivity); // Dibujar Activity
        paintDrawer(ivDrawer, bgActivity.getWidth(),
                bgActivity.getHeight()); // Dibujar Drawer
        paintTutoMenuGTCE(ivMenuGTCE, bgActivity.getWidth(),
                bgActivity.getHeight()); // Dibujar tutorial del menu PG

        animSlideRight = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_right_drawer);
        final Animation animSlideLeft = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_left_drawer);
        final Animation animFadeInMenuGTCE = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_in);
        final Animation animFadeOutMenuGTCE = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_out);
        final Animation animFadeInTutoGTCE = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_in);
        final Animation animFadeOutTutoGTCE = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_out);
        final Animation animWaiting = AnimationUtils.loadAnimation(getContext(),
                R.anim.waiting_9s);

        animSlideRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                ivMenuGTCE.startAnimation(animFadeInMenuGTCE);
                ivMenuGTCE.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeInMenuGTCE.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                ivMenuGTCE.startAnimation(animFadeOutMenuGTCE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeOutMenuGTCE.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                llTutoGTCE.startAnimation(animFadeInTutoGTCE);
                llTutoGTCE.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ivDrawer.startAnimation(animSlideLeft);
                ivMenuGTCE.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animSlideLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoGTCE.startAnimation(animWaiting);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animWaiting.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoGTCE.startAnimation(animFadeOutTutoGTCE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animFadeOutTutoGTCE.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                llTutoGTCE.setVisibility(View.INVISIBLE);
                ivDrawer.startAnimation(animSlideRight);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        ivDrawer.startAnimation(animSlideRight);

        return view;
    }

    private void paintTutoMenuGTCE(ImageView imageView, int width, int height) {
        // Definir el mapa de bits
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // Definir el lienzo
        Canvas canvas = new Canvas(bitmap);
        // Estilos
        Paint paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        paint.setAntiAlias(true);
        canvas.drawPaint(paint);

        // Dibujar fondo
        paint.setColor(Color.parseColor("#F0FFFFFF"));
        canvas.drawRect(0, height / 3.f, (width / 2.f) + 61,
                (height / 3.f) + 130, paint);
        canvas.drawRect(0, (height / 3.f) + 150, (width / 2.f) + 61,
                height, paint);
        paint.setColor(Color.parseColor("#90000000"));
        canvas.drawRect((width / 2.f) + 60, 0, width, height, paint);

        //agregar el bitmap al ImageView
        imageView.setImageBitmap(bitmap);
        imageView.setVisibility(View.INVISIBLE);
    }

    private View tutorial9(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_tutorial_9, container, false);
    }

}

/*
//path
paint.setColor(Color.parseColor("#ff00ff"));
paint.setStrokeWidth(2);
        Path path = new Path();
        path.moveTo(200, 30);
        path.lineTo(250, 120);
        path.lineTo(150, 120);
        canvas.drawPath(path, paint);
*/