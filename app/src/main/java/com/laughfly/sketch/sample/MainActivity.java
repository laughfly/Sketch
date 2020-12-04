package com.laughfly.sketch.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.laughfly.sketch.FactoryIDs;
import com.laughfly.sketch.SketchView;
import com.laughfly.sketch.brush.BrushFactory;
import com.laughfly.sketch.eraser.EraserFactory;
import com.laughfly.sketch.laser.LaserFactory;
import com.laughfly.sketch.shape.ShapeFactory;

public class MainActivity extends AppCompatActivity {

    private SketchView mSketchView;

    private View mSelectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSketchView = findViewById(R.id.sketch_view);
        mSketchView.registerFactory(new BrushFactory(3, 0xFF000000));
        mSketchView.registerFactory(new EraserFactory(30));
        mSketchView.registerFactory(new ShapeFactory(4, 0xFF000000));
        mSketchView.registerFactory(new LaserFactory(20));
        clickBrushBtn(findViewById(R.id.brushBtn));
    }

    private void toggleButton(View view) {
        if(view != mSelectButton) {
            if(mSelectButton != null) {
                mSelectButton.setSelected(false);
            }
            view.setSelected(true);
            mSelectButton = view;
        }
    }

    public void clickBrushBtn(View view) {
        mSketchView.setCurrentFactory(FactoryIDs.BRUSH);
        toggleButton(view);
    }

    public void clickEraseBtn(View view) {
        mSketchView.setCurrentFactory(FactoryIDs.ERASER);
        toggleButton(view);
    }

    public void clickShapeBtn(View view) {
        mSketchView.setCurrentFactory(FactoryIDs.SHAPE);
        toggleButton(view);
    }

    public void clickLaserBtn(View view) {
        mSketchView.setCurrentFactory(FactoryIDs.LASER);
        toggleButton(view);
    }

    public void clickUndoBtn(View view) {
        if(mSketchView.canUndo()) {
            mSketchView.undo();
        }
    }

    public void clickRedoBtn(View view) {
        if(mSketchView.canRedo()) {
            mSketchView.redo();
        }
    }

    public void clickCleanBtn(View view) {
        mSketchView.clean();
    }


}
