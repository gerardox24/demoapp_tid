package com.ramos.demosceneform;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArFragment arFragment;
    private ModelRenderable ironmanRenderable;

    ImageView ironman;

    View arrayView[];
    ViewRenderable name_avenger;

    int selected = 1; // Default IRONMAN


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.sceneform_ux_fragment);

        //View
        ironman = (ImageView) findViewById(R.id.ironman);

        setArrayView();
        setClickListener();

        setupModel();

        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                // When user tap on place, we will add model
                Anchor anchor = hitResult.createAnchor();
                AnchorNode anchorNode = new AnchorNode(anchor);
                anchorNode.setParent(arFragment.getArSceneView().getScene());

                createModel(anchorNode, selected);
            }
        });
    }

    private void setupModel() {
        ModelRenderable.builder()
                .setSource(this,R.raw.ironman)
                .build().thenAccept(renderable -> ironmanRenderable = renderable)
                .exceptionally(throwable -> {
                        // Toast.makeText(this, "Unable to load ironman model", Toast.LENGTH_SHORT).setGravity(Gravity.CENTER,0,0).show();
                        Toast toast = Toast.makeText(this,"Unable to load iroman model",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();

                        return null;
                    }
                );
    }

    private void createModel(AnchorNode anchorNode, int selected) {
        if(selected == 1) {
            TransformableNode ironman = new TransformableNode(arFragment.getTransformationSystem());
            ironman.setParent(anchorNode);
            ironman.setRenderable(ironmanRenderable);
            ironman.select();


        }
    }

    private void setClickListener(){
        for( int i = 0; i < arrayView.length; i++ ) {
            arrayView[i].setOnClickListener(this);
        }
    }

    private void setArrayView() {
        arrayView = new View[]{
                ironman
        };
    }

    @Override
    public void onClick(View v) {

    }
}
